package io.ermdev.mapfierj;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Transaction {

    private final HashMap<String, Object> fields = new HashMap<>();

    public Transaction(Object obj) throws Exception {
        if(obj == null)
            return;
        for(Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            MapTo maps = field.getAnnotation(MapTo.class);
            if(maps != null) {
                fields.put(field.getName(), new Transaction(field.get(obj)).mapTo(maps.value()));
            } else {
                fields.put(field.getName(), field.get(obj));
            }
        }
    }

    public Transaction(HashMap<String, Object> map) {
        fields.clear();
        fields.putAll(map);
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
}
