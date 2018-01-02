package io.ermdev.mapfierj;

import org.reflections.Reflections;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ModelMapper {

    private Transaction transaction;
    private HashMap<String, Object> map = new HashMap<>();
    private final Set<Class<? extends TypeConverterAdapter>> converters = new HashSet<>();

    public ModelMapper() {
        final String dir = "io.ermdev.mapfierj.typeconverter";
        final Reflections reflections = new Reflections(dir);
        converters.addAll(reflections.getSubTypesOf(TypeConverterAdapter.class));
    }

    public ModelMapper(String dir) {
        this();
        final Reflections reflections = new Reflections(dir);
        converters.addAll(reflections.getSubTypesOf(TypeConverterAdapter.class));
    }

    public ModelMapper set(Object o) {
        try {
            map=setAndGetTransaction(o).getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelMapper set(HashMap<String, Object> map) {
        try {
            this.map=setAndGetTransaction(map).getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelMapper set(Collection collection) {
        map=setAndGetTransaction(collection).getMap();
        return this;
    }

    public Transaction setAndGetTransaction(Object obj) {
        try {
            transaction=new Transaction(obj);
            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Transaction setAndGetTransaction(HashMap<String, Object> map) {
        try {
            transaction=new Transaction(map);
            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Transaction setAndGetTransaction(Collection collection) {
        return transaction=new Transaction(collection);
    }

    public ModelMapper field(String field, String field2) {
        final Object o = map.get(field);
        map.remove(field);
        if(o != null) {
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
        if(o != null) {
            try {
                for(Class<? extends TypeConverterAdapter> converter : converters) {
                    try {
                        if(converter.getAnnotation(TypeConverter.class) != null) {
                            TypeConverterAdapter adapter = converter.newInstance();
                            Object instance = adapter.convert(o);
                            if (!instance.getClass().equals(type))
                                throw new TypeException("Type not match");
                            map.put(field, instance);
                            break;
                        }
                    } catch (Exception e) {}
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
        if(o != null) {
            try {
                Object instance = adapter.convert(o);
                if(instance!=null)
                    map.put(field, instance);
                else
                    map.remove(field);
            } catch (Exception e) {
                e.printStackTrace();
                map.remove(field);
            }
        }
        return this;
    }

    public ModelMapper converter(String field, Class<? extends TypeConverterAdapter> c) {
        final Object o = map.get(field);
        if(o != null) {
            try {
                TypeConverterAdapter adapter = c.newInstance();
                Object instance = adapter.convert(o);
                if(instance!=null)
                    map.put(field, instance);
                else
                    map.remove(field);
            } catch (Exception e) {
                e.printStackTrace();
                map.remove(field);
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
