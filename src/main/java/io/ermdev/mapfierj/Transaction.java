package io.ermdev.mapfierj;

import java.lang.reflect.Field;
import java.util.*;

public class Transaction {

    private final HashMap<String, Object> fields = new HashMap<>();

    public Transaction(Object obj) throws Exception {
        if(obj == null)
            return;
        for(Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);

            MapTo maps = field.getAnnotation(MapTo.class);
            if(maps != null) {
                if(value instanceof Collection || maps.collection()) {
                    if(field.getType().equals(List.class))
                        fields.put(field.getName(), transaction((List) value, maps));
                    else
                        fields.put(field.getName(), transaction((Set) value, maps));
                } else {
                    fields.put(field.getName(), new Transaction(value).mapTo(maps.value()));
                }
            } else {
                fields.put(field.getName(), value);
            }
        }
    }

    public Transaction(HashMap<String, Object> map) {
        fields.clear();
        fields.putAll(map);
    }

    private Object transaction(List collection, MapTo maps) throws Exception {
        if(collection == null)
            return null;
        Collection<Object> list = new ArrayList<>();
        for(Object o : collection) {
            list.add(new Transaction(o).mapTo(maps.value()));
        }
        return list;
    }

    private Object transaction(Set collection, MapTo maps) throws Exception {
        if(collection == null)
            return null;
        Collection<Object> set = new HashSet<>();
        for(Object o : collection) {
            set.add(new Transaction(o).mapTo(maps.value()));
        }
        return set;
    }

    public HashMap<String, Object> getMap() {
        return fields;
    }

    public <T> T mapTo(Class<T> _class) {
        try {
            T instance = _class.newInstance();
            for (Field field : _class.getDeclaredFields()) {
                field.setAccessible(true);
                if(field.getAnnotation(Excluded.class) == null) {
                    Object value = fields.get(field.getName());
                    if (value != null)
                        field.set(instance, value);
                }
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO
    public <T> T mapAllTo(Class<T> c) {
        try {
            T instance = c.newInstance();
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                if(field.getAnnotation(Excluded.class) == null) {
                    Object value = fields.get(field.getName());
                    if (value != null) {
                        if(!field.getType().equals(value.getClass())) {
                            value = new Transaction(value).mapTo(field.getType());
                        }
                        field.set(instance, value);
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
