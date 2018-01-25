package io.ermdev.mapfierj;

import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Converter {

    private final String BASE_PACKAGE = "io.ermdev.mapfierj.typeconverter";
    private final Set<Class<? extends TypeConverterAdapter>> PRIMITIVE_CONVERTERS = new HashSet<>();
    private final Set<Class<? extends TypeConverterAdapter>> SCANNED_CONVERTERS = new HashSet<>();
    private final Set<TypeConverterAdapter> REGISTERED_CONVERTERS = new HashSet<>();

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

    @SuppressWarnings("unchecked")
    public <T> T convertTo(final Object o, Class<T> type) throws MappingException {
        final Set<Class<? extends TypeConverterAdapter>> converters = new HashSet<>();
        final HashMap<String, Class<? extends TypeConverterAdapter>> possibleTypes = new HashMap<>();
        final Objects objects = new Objects();

        if (o != null) {
            if (o.getClass().equals(type)) {
                return (T) o;
            }
            try {
                converters.addAll(PRIMITIVE_CONVERTERS);
                converters.addAll(SCANNED_CONVERTERS);
                boolean isExists = converters.parallelStream()
                        .filter(converter -> {
                            final Type types[] = (((ParameterizedType)
                                    converter.getGenericSuperclass()).getActualTypeArguments());
                            boolean isMatch = false;
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
                                if (generic.equals(o.getClass())) {
                                    isMatch = true;
                                    break;
                                }
                            }
                            return isMatch;
                        })
                        .anyMatch(converter -> {
                            try {
                                final boolean isRegistered = REGISTERED_CONVERTERS.parallelStream()
                                        .anyMatch(item -> item.getClass().equals(converter));
                                if (isRegistered) {
                                    final TypeConverterAdapter adapter = REGISTERED_CONVERTERS.parallelStream()
                                            .filter(item -> item.getClass().equals(converter))
                                            .findFirst()
                                            .get();
                                    objects.setObject(adapter.convert(o));
                                    if (!objects.getObject().getClass().equals(type))
                                        throw new TypeException("Type not match");
                                    return true;
                                } else {
                                    if (converter.getAnnotation(TypeConverter.class) != null) {
                                        final TypeConverterAdapter adapter;
                                        try {
                                            adapter = converter.newInstance();
                                        } catch (Exception e) {
                                            throw new MappingException("Failed to found the constructor " +
                                                    "with no arguments");
                                        }
                                        objects.setObject(adapter.convert(o));
                                        if (!objects.getObject().getClass().equals(type))
                                            throw new TypeException("Type not match");
                                        return true;
                                    }
                                }
                                throw new TypeException("No valid TypeConverter found");
                            } catch (Exception e) {
                                e.printStackTrace();
                                if(objects.getObject() != null) {
                                    objects.setObject(null);
                                }
                                return false;
                            }
                        });
                if (!isExists) {
                    converters.parallelStream().filter(converter -> {
                        boolean isMatch = false;
                        Type types[] = (((ParameterizedType)
                                converter.getGenericSuperclass()).getActualTypeArguments());
                        for (Type generic : types) {
                            if (generic.equals(o.getClass())) {
                                isMatch = true;
                                break;
                            }
                        }
                        return isMatch;
                    })
                    .forEach((Class<? extends TypeConverterAdapter> converter) -> {
                        Type types[] = (((ParameterizedType)
                                converter.getGenericSuperclass()).getActualTypeArguments());
                        outer:
                        for (Type generic : types) {
                            for (Map.Entry entry : possibleTypes.entrySet()) {
                                if (entry.getKey().equals(generic.toString())) {
                                    if (converter.getAnnotation(TypeConverter.class) != null) {
                                        try {
                                            final TypeConverterAdapter adapter1;
                                            final TypeConverterAdapter adapter2;
                                            try {
                                                adapter1 = converter.newInstance();
                                                adapter2  = (TypeConverterAdapter)
                                                        ((Class<?>) entry.getValue()).newInstance();
                                            } catch (Exception e) {
                                                throw new MappingException("Failed to found the constructor " +
                                                        "with no arguments");
                                            }
                                            objects.setObject(adapter2.convert(adapter1.convert(o)));
                                            break outer;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            if(objects.getObject() != null) {
                                                objects.setObject(null);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                if(objects.getObject() != null) {
                    objects.setObject(null);
                }
            }
        }
        return (T) objects.getObject();
    }

    public Session openSession() {
        return new Session();
    }

    public class Session {
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
                typeConverter = adapterClass.newInstance();
            } catch (Exception e) {
                throw new MappingException("Failed to found the constructor with no arguments");
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

    protected class Objects {

        Object object;

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }
}
