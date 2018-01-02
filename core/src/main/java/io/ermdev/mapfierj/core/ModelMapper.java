package io.ermdev.mapfierj.core;

import io.ermdev.mapfierj.exception.TypeException;
import org.reflections.Reflections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class ModelMapper {

    private Transaction transaction;
    private HashMap<String, Object> map = new HashMap<>();

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

    public ModelMapper autoConvert(String field) {
        final Object o = map.get(field);
        if(o != null) {
            try {
                Reflections reflections = new Reflections("io.ermdev.mapfierj.typeconverter");
                Set<Class<? extends TypeConverterAdapter>> converters = reflections.getSubTypesOf(TypeConverterAdapter.class);
                for(Class<? extends TypeConverterAdapter> c : converters) {
                    try {
                        TypeConverterAdapter adapter = c.newInstance();
                        System.out.println(c);
                        Object instance = adapter.convert(o);
                        map.put(field, instance);
                        break;
                    } catch (TypeException e) {
                        //e.printStackTrace();
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
