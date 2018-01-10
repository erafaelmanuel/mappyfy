package io.ermdev.mapfierj;

import java.util.Collection;
import java.util.HashMap;

public class ModelMapper {

    private Transaction transaction;
    private HashMap<String, Object> map = new HashMap<>();
    private final Converter converter;

    public ModelMapper() {
        converter = new Converter();
    }

    public ModelMapper(final String... scanPackages) {
        this();
        converter.scanPackages(scanPackages);
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
        final Object instance = converter.convertTo( map.get(field), type);
        if(instance != null)
            map.put(field, instance);
        else
            map.remove(field);
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
