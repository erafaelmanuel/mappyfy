package io.ermdev.mapfierj;

import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class ModelMapper {

    private Transaction transaction;
    private HashMap<String, Object> map = new HashMap<>();
    private final Set<Class<? extends TypeConverterAdapter>> converters = new HashSet<>();

    public ModelMapper() {
        final String dir = "io.ermdev.mapfierj.typeconverter";
        final Reflections reflections = new Reflections(dir);
        converters.addAll(reflections.getSubTypesOf(TypeConverterAdapter.class));
    }

    public ModelMapper(final String scanPackages) {
        this();
        if(!scanPackages.trim().isEmpty()) {
            final Reflections reflections = new Reflections(scanPackages);
            converters.addAll(reflections.getSubTypesOf(TypeConverterAdapter.class));
        }
    }

    public ModelMapper set(Object o) {
        try {
            map = setAndGetTransaction(o).getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelMapper set(HashMap<String, Object> map) {
        try {
            this.map = setAndGetTransaction(map).getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelMapper set(Collection collection) {
        map = setAndGetTransaction(collection).getMap();
        return this;
    }

    public Transaction setAndGetTransaction(Object obj) {
        try {
            transaction = new Transaction(obj);
            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Transaction setAndGetTransaction(HashMap<String, Object> map) {
        try {
            transaction = new Transaction(map);
            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Transaction setAndGetTransaction(Collection collection) {
        return transaction = new Transaction(collection);
    }

    public ModelMapper field(String field, String field2) {
        final Object o = map.get(field);
        map.remove(field);
        if (o != null) {
            map.put(field2, o);
        }
        return this;
    }

    public ModelMapper exclude(String field) {
        map.remove(field);
        return this;
    }

    public ModelMapper excludeAll(String field) {
        transaction.getExcludedField().add(field);
        return this;
    }

    public ModelMapper convertFieldToType(String field, Class<?> type) {
        final Object o = map.get(field);
        final HashMap<String, Class<? extends TypeConverterAdapter>> possibleTypes = new HashMap<>();

        if (o != null) {
            try {
                map.remove(field);
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
                                    Object instance = adapter.convert();
                                    if (!instance.getClass().equals(type))
                                        throw new TypeException("Type not match");
                                    map.put(field, instance);
                                    return true;
                                }
                                throw new TypeException("No valid TypeConverter found");
                            } catch (Exception e) {
                                map.remove(field);
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
                                            Object instance = adapter2.convert();
                                            map.put(field, instance);
                                            break outer;
                                        }
                                        throw new TypeException("No valid TypeConverter found");
                                    } catch (Exception e) {
                                        map.remove(field);
                                    }
                                }
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.remove(field);
            }
        }
        return this;
    }

    public ModelMapper converter(String field, TypeConverterAdapter adapter) {
        final Object o = map.get(field);
        map.remove(field);
        if (o != null) {
            try {
                if(adapter.getObject() == null) {
                    adapter.setObject(o);
                }
                Object instance = adapter.convert();
                if (instance != null) {
                    map.put(field, instance);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public ModelMapper converter(String field, Class<? extends TypeConverterAdapter> c) {
        final Object o = map.get(field);
        map.remove(field);
        if (o != null) {
            try {
                TypeConverterAdapter adapter = c.getDeclaredConstructor(Object.class).newInstance(o);
                Object instance = adapter.convert();
                if (instance != null) {
                    map.put(field, instance);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        this.map = transaction.getMap();
    }
}
