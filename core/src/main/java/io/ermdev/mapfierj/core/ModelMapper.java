package io.ermdev.mapfierj.core;

import java.util.HashMap;

public class ModelMapper {

    private Transaction transaction;
    private HashMap<String, Object> map = new HashMap<>();

    public ModelMapper set(Object obj) {
        try {
            transaction=new Transaction(obj);
            map=transaction.getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Transaction setAndGetTransaction(Object obj) {
        try {
            transaction=new Transaction(obj);
            map=transaction.getMap();
            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelMapper field(String field, String field2) {
        final Object o = map.get(field);
        map.remove(field);
        if(o != null) {
            map.put(field2, o);
        }
        return this;
    }

    public ModelMapper excluded(String field) {
        map.remove(field);
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
