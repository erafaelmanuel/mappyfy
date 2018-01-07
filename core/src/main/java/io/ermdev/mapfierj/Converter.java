package io.ermdev.mapfierj;

import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Converter {

    private final Set<Class<? extends TypeConverterAdapter>> converters = new HashSet<>();
    private final Set<Class<? extends TypeConverterAdapter>> convertersScanned = new HashSet<>();
    private Object newInstance;

    public Converter() {
        final String packages = "io.ermdev.mapfierj.typeconverter";
        final Reflections reflections = new Reflections(packages);
        converters.addAll(reflections.getSubTypesOf(TypeConverterAdapter.class));
    }

    public void scanPackages(String packages) {
        final String dir = "io.ermdev.mapfierj.typeconverter";
        final Reflections reflections = new Reflections(packages);
        convertersScanned.addAll(reflections.getSubTypesOf(TypeConverterAdapter.class));
    }

    public Object apply(final Object o, Class<?> type) {
        if(o != null) {
            final Set<Class<? extends TypeConverterAdapter>> converters = new HashSet<>();
            final HashMap<String, Class<? extends TypeConverterAdapter>> possibleTypes = new HashMap<>();

            converters.addAll(this.converters);
            converters.addAll(convertersScanned);
            try {
                boolean isExists=converters.parallelStream()
                        .filter(converter -> {
                            boolean isMatch=false;
                            Type types[] = (((ParameterizedType)
                                    converter.getGenericSuperclass()).getActualTypeArguments());
                            if(types.length == 2) {
                                for (int i = 0; i < 2; i++) {
                                    if (types[i].equals(type)) {
                                        isMatch = true;
                                        switch (i) {
                                            case 0:
                                                possibleTypes.put(types[i+1].toString(), converter);
                                                break;
                                            case 1:
                                                possibleTypes.put(types[i-1].toString(), converter);
                                                break;
                                        }
                                        break;
                                    }
                                }
                            }
                            return isMatch;
                        })
                        .filter(converter -> {
                            boolean isMatch=false;
                            Type types[] = (((ParameterizedType)
                                    converter.getGenericSuperclass()).getActualTypeArguments());
                            for(Type generic : types) {
                                if(generic.equals(o.getClass())) {
                                    isMatch=true;
                                    break;
                                }
                            }
                            return isMatch;
                        })
                        .anyMatch(converter->{
                            try {
                                if (converter.getAnnotation(TypeConverter.class) != null) {
                                    TypeConverterAdapter adapter = converter
                                            .getDeclaredConstructor(Object.class).newInstance(o);
                                    newInstance = adapter.convert();
                                    if (!newInstance.getClass().equals(type))
                                        throw new TypeException("Type not match");
                                    return true;
                                }
                                throw new TypeException("No valid TypeConverter found");
                            } catch (Exception e) {
                                newInstance = null;
                                return false;
                            }
                        });
                if(!isExists) {
                    converters.parallelStream().filter(converter -> {
                        boolean isMatch=false;
                        Type types[] = (((ParameterizedType)
                                converter.getGenericSuperclass()).getActualTypeArguments());
                        for(Type generic : types) {
                            if(generic.equals(o.getClass())) {
                                isMatch=true;
                                break;
                            }
                        }
                        return isMatch;
                    })
                            .forEach(converter -> {
                                Type types[] = (((ParameterizedType)
                                        converter.getGenericSuperclass()).getActualTypeArguments());
                                outer:for(Type generic : types) {
                                    for(Map.Entry entry : possibleTypes.entrySet()) {
                                        if (entry.getKey().equals(generic.toString())) {
                                            try {
                                                if (converter.getAnnotation(TypeConverter.class) != null) {
                                                    TypeConverterAdapter adapter1 = converter
                                                            .getDeclaredConstructor(Object.class)
                                                            .newInstance(o);
                                                    TypeConverterAdapter adapter2 =
                                                            (TypeConverterAdapter) ((Class<?>) entry.getValue())
                                                                    .getDeclaredConstructor(Object.class)
                                                                    .newInstance(adapter1.convert());
                                                    newInstance = adapter2.convert();
                                                    break outer;
                                                }
                                                throw new TypeException("No valid TypeConverter found");
                                            } catch (Exception e) {
                                                newInstance = null;
                                            }
                                        }
                                    }
                                }
                            });
                }
            } catch (Exception e) {
                e.printStackTrace();
                newInstance = null;
            }
        }
        return newInstance;
    }
}
