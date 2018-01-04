package io.ermdev.mapfierj.cj7;

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

    public ModelMapper(final String dir) {
        this();
        final Reflections reflections = new Reflections(dir);
        converters.addAll(reflections.getSubTypesOf(TypeConverterAdapter.class));
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
        final Set<Class<? extends TypeConverterAdapter>> filteredConverter1 = new HashSet<>();
        final Set<Class<? extends TypeConverterAdapter>> filteredConverter2 = new HashSet<>();
        final HashMap<String, Class<? extends TypeConverterAdapter>> possibleTypes = new HashMap<>();

        if (o != null) {
            try {
                map.remove(field);
                boolean isExists=false;
                for(Class<? extends TypeConverterAdapter> converter: converters) {
                    Type types[] = (((ParameterizedType)
                            converter.getGenericSuperclass()).getActualTypeArguments());
                    if (types.length == 2) {
                        for (int i = 0; i < 2; i++) {
                            if (types[i].equals(type)) {
                                filteredConverter1.add(converter);
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
                }
                for(Class<? extends TypeConverterAdapter> converter: filteredConverter1) {
                    Type types[] = (((ParameterizedType)
                            converter.getGenericSuperclass()).getActualTypeArguments());
                    for(Type generic : types) {
                        if(generic.equals(o.getClass())) {
                            filteredConverter2.add(converter);
                            break;
                        }
                    }
                }
                for(Class<? extends TypeConverterAdapter> converter: filteredConverter2) {
                    try {
                        if (converter.getAnnotation(TypeConverter.class) != null) {
                            TypeConverterAdapter adapter = converter.newInstance();
                            Object instance = adapter.convert(o);
                            if (!instance.getClass().equals(type))
                                throw new TypeException("Type not match");
                            map.put(field, instance);
                            isExists = true;
                        }
                    } catch (Exception e) {
                        map.remove(field);
                        isExists = false;
                    }
                }

                if(!isExists) {
                    filteredConverter1.clear();
                    filteredConverter2.clear();

                    for(Class<? extends TypeConverterAdapter> converter: converters) {
                        Type types[] = (((ParameterizedType)
                                converter.getGenericSuperclass()).getActualTypeArguments());
                        for(Type generic : types) {
                            if(generic.equals(o.getClass())) {
                                filteredConverter1.add(converter);
                                break;
                            }
                        }
                    }
                    for(Class<? extends TypeConverterAdapter> converter: filteredConverter1) {
                        Type types[] = (((ParameterizedType)
                                converter.getGenericSuperclass()).getActualTypeArguments());
                        outer:for(Type generic : types) {
                            for(Map.Entry entry : possibleTypes.entrySet()) {
                                if (entry.getKey().equals(generic.toString())) {
                                    try {
                                        if (converter.getAnnotation(TypeConverter.class) != null) {
                                            TypeConverterAdapter adapter1 = converter.newInstance();
                                            TypeConverterAdapter adapter2 =
                                                    (TypeConverterAdapter) ((Class<?>) entry.getValue()).newInstance();
                                            Object instance = adapter2.convert(adapter1.convert(o));
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
                    }
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
                Object instance = adapter.convert(o);
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
                TypeConverterAdapter adapter = c.newInstance();
                Object instance = adapter.convert(o);
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
    }
}
