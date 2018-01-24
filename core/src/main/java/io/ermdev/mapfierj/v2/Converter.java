package io.ermdev.mapfierj.v2;

import io.ermdev.mapfierj.MappingException;
import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeException;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Converter {

    private final String BASE_PACKAGE = "io.ermdev.mapfierj.typeconverter.v2";
    private final Set<Class<? extends TypeConverterAdapter>> PRIMITIVE_CONVERTERS = new HashSet<>();
    private final Set<Class<? extends TypeConverterAdapter>> SCANNED_CONVERTERS = new HashSet<>();
    private final Set<TypeConverterAdapter> REGISTERED_CONVERTERS = new HashSet<>();
    private Object o;

    public Converter() {
        final Reflections reflections = new Reflections(BASE_PACKAGE);
        PRIMITIVE_CONVERTERS.addAll(reflections.getSubTypesOf(TypeConverterAdapter.class));
    }

    public void scanPackages(String... packages) {
        for (String item : packages) {
            if (item != null && !item.trim().isEmpty()) {
                final Reflections reflections = new Reflections(item);
                SCANNED_CONVERTERS.addAll(reflections.getSubTypesOf(TypeConverterAdapter.class));
            }
        }
    }

    public void register(TypeConverterAdapter adapter) {
        REGISTERED_CONVERTERS.add(adapter);
        SCANNED_CONVERTERS.add(adapter.getClass());
    }

    public Object convertTo(final Object obj, Class<?> type) throws MappingException {
        if (obj != null) {
            final Set<Class<? extends TypeConverterAdapter>> converters = new HashSet<>();
            final HashMap<String, Class<? extends TypeConverterAdapter>> possibleTypes = new HashMap<>();
            converters.addAll(PRIMITIVE_CONVERTERS);
            converters.addAll(SCANNED_CONVERTERS);
            try {
                if (obj.getClass().equals(type)) {
                    return obj;
                } else {
                    o = null;
                }
                boolean isExists = converters.parallelStream()
                        .filter(converter -> {
                            boolean isMatch = false;
                            Type types[] = (((ParameterizedType)
                                    converter.getGenericSuperclass()).getActualTypeArguments());
                            if (types.length == 2) {
                                for (int i = 0; i < 2; i++) {
                                    if (types[i].equals(type)) {
                                        isMatch = true;
                                        switch (i) {
                                            case 0:
                                                possibleTypes.put(types[i + 1].toString(), converter);
                                                break;
                                            case 1:
                                                possibleTypes.put(types[i - 1].toString(), converter);
                                                break;
                                        }
                                        break;
                                    }
                                }
                            }
                            return isMatch;
                        })
                        .filter(converter -> {
                            boolean isMatch = false;
                            Type types[] = (((ParameterizedType)
                                    converter.getGenericSuperclass()).getActualTypeArguments());
                            for (Type generic : types) {
                                if (generic.equals(obj.getClass())) {
                                    isMatch = true;
                                    break;
                                }
                            }
                            return isMatch;
                        })
                        .anyMatch(converter -> {
                            try {
                                final boolean isRegistered = REGISTERED_CONVERTERS
                                        .parallelStream().anyMatch(item-> item.getClass().equals(converter));
                                if(isRegistered) {
                                    TypeConverterAdapter adapter = REGISTERED_CONVERTERS.parallelStream()
                                            .filter(item-> item.getClass().equals(converter))
                                            .findFirst().get();

                                    if (!(o = adapter.convert(obj)).getClass().equals(type))
                                        throw new TypeException("Type not match");
                                    return true;
                                } else {
                                    if (converter.getAnnotation(TypeConverter.class) != null) {
                                        TypeConverterAdapter adapter = converter
                                                .getDeclaredConstructor(Object.class).newInstance(obj);

                                        if (!(o = adapter.convert(obj)).getClass().equals(type))
                                            throw new TypeException("Type not match");
                                        return true;
                                    }
                                }
                                throw new TypeException("No valid TypeConverter found");
                            } catch (Exception e) {
                                o = null;
                                return false;
                            }
                        });
                if (!isExists) {
                    converters.parallelStream().filter(converter -> {
                        boolean isMatch = false;
                        Type types[] = (((ParameterizedType)
                                converter.getGenericSuperclass()).getActualTypeArguments());
                        for (Type generic : types) {
                            if (generic.equals(obj.getClass())) {
                                isMatch = true;
                                break;
                            }
                        }
                        return isMatch;
                    })
                    .forEach(converter -> {
                        Type types[] = (((ParameterizedType)
                                converter.getGenericSuperclass()).getActualTypeArguments());
                        outer:
                        for (Type generic : types) {
                            for (Map.Entry entry : possibleTypes.entrySet()) {
                                if (entry.getKey().equals(generic.toString())) {
                                    try {
                                        if (converter.getAnnotation(TypeConverter.class) != null) {
                                            TypeConverterAdapter adapter1 = converter
                                                    .getDeclaredConstructor(Object.class)
                                                    .newInstance();
                                            TypeConverterAdapter adapter2 =
                                                    (TypeConverterAdapter) ((Class<?>) entry.getValue())
                                                            .getDeclaredConstructor(Object.class)
                                                            .newInstance();
                                            this.o = adapter2.convert(adapter1.convert(obj));
                                            break outer;
                                        }
                                        throw new TypeException("No valid TypeConverter found");
                                    } catch (Exception e) {
                                        this.o = null;
                                    }
                                }
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.o = null;
            }
        }
        return this.o;
    }

    public Session openSession() {
        return new Session();
    }

    class Session {
        private Object o;
        private TypeConverterAdapter typeConverter;

        public Session set(Object obj) {
            o = obj;
            return this;
        }

        public Session adapter(TypeConverterAdapter typeConverter) {
            this.typeConverter = typeConverter;
            return this;
        }

        public Session adapter(Class<? extends TypeConverterAdapter> adapterClass) throws MappingException {
            try {
                typeConverter = adapterClass.getDeclaredConstructor(Object.class).newInstance();
            } catch (Exception e) {
                throw new MappingException("Failed to found a constructor without an arguments");
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public <T> T convert() throws MappingException {
            if (o == null) {
                throw new MappingException("No object to convert");
            }
            if (typeConverter == null) {
                throw new MappingException("TypeConverter is null");
            }
            try {
                return (T) typeConverter.convert(o);
            } catch (TypeException e) {
                return null;
            }
        }
    }
}
