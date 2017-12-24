package io.ermdev.mapfierj;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class Transaction {

    private final List<Class<?>> NO_REPEAT_CLASSES = new ArrayList<>();

    private HashMap<String, Object> fields = new HashMap<>();

    private List<Object> col = new ArrayList<>();

    public Transaction(Collection collection){
        if(collection != null)
            col.addAll(collection);
    }

    public Transaction(HashMap<String, Object> map) {
        if(map != null)
            fields.putAll(map);
    }

    public Transaction(Object o) throws Exception {
        if(o != null) {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(o);

                MapTo maps = field.getAnnotation(MapTo.class);
                if (maps != null && value != null) {
                    if (value instanceof Collection || maps.collection()) {
                        final Collection collection = (Collection) value;
                        if (maps.type().equals(List.class)) {
                            fields.put(field.getName(), mapList(collection, maps.value(), true));
                        } else if (maps.type().equals(Set.class)) {
                            fields.put(field.getName(), mapSet(collection, maps.value(), true));
                        } else {
                            if (field.getType().equals(List.class)) {
                                fields.put(field.getName(), mapList(collection, maps.value(), true));
                            } else if (field.getType().equals(Set.class)) {
                                fields.put(field.getName(), mapSet(collection, maps.value(), true));
                            }
                        }
                    } else {
                        fields.put(field.getName(), new Transaction(value).mapTo(maps.value()));
                    }
                } else {
                    fields.put(field.getName(), value);
                }
            }
        }
    }

    @Deprecated
    private Transaction(Object o, List<Class<?>> no_repeat_classes) throws Exception {
        if(o == null)
            return;
        NO_REPEAT_CLASSES.addAll(no_repeat_classes);

        for(Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(o);

            if(isReaper(field.getType())) continue;

            MapTo maps = field.getAnnotation(MapTo.class);
            if(maps != null && value != null) {
                if(value instanceof Collection || maps.collection()) {
                    final Collection<?> collection = (Collection) value;
                    if(maps.type().equals(List.class))
                        fields.put(field.getName(), mapList(collection, maps.value(), true));
                    else if(maps.type().equals(Set.class))
                        fields.put(field.getName(), mapSet(collection, maps.value(), true));
                    else {
                        if(field.getType().equals(List.class))
                            fields.put(field.getName(), mapList(collection, maps.value(), true));
                        else if(field.getType().equals(Set.class))
                            fields.put(field.getName(), mapSet(collection, maps.value(), true));
                    }
                } else {
                    fields.put(field.getName(), new Transaction(value, no_repeat_classes).mapTo(maps.value()));
                }
            } else {
                fields.put(field.getName(), value);
            }
        }

    }

    private <T> List<T> mapList(Collection collection, Class<T> c, boolean hasMapTo) {
        List<T> list = new ArrayList<>();
        try {
            if (collection == null)
                throw new RuntimeException("Collection must not null");
            if(hasMapTo) {
                for (Object o : collection) {
                    list.add(new Transaction(o).mapTo(c));
                }
            } else {
                for (Object o : collection) {
                    list.add(new Transaction(o).mapAllTo(c));
                }
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
            return list;
        }
    }

    @Deprecated
    private Object mapList(Collection<?> collection, Class<?> c, List<Class<?>> no_repeat_classes) throws Exception {
        Collection<Object> list = new ArrayList<>();
        for(Object o : collection) {
            list.add(new Transaction(o, no_repeat_classes).mapTo(c));
        }
        return list;
    }

    private <T> Set<T> mapSet(Collection collection, Class<T> c, boolean hasMapTo) {
        Set<T> set = new HashSet<>();
        try {
            if (collection == null)
                throw new RuntimeException("Collection must not null");
            if(hasMapTo) {
                for (Object o : collection) {
                    set.add(new Transaction(o).mapTo(c));
                }
            } else {
                for (Object o : collection) {
                    set.add(new Transaction(o).mapAllTo(c));
                }
            }
            return set;
        } catch (Exception e) {
            e.printStackTrace();
            return set;
        }
    }

    @Deprecated
    private Object mapSet(Collection collection, Class<?> c, List<Class<?>> no_repeat_classes) throws Exception {
        Collection<Object> set = new HashSet<>();
        for(Object o : collection) {
            set.add(new Transaction(o, no_repeat_classes).mapTo(c));
        }
        return set;
    }

    private Object mapCollection(Collection<?> collection, Class<?> parameter, Class<?> type, boolean hasMapTo) {
        if(type.equals(List.class)) {
            return mapList(collection, parameter, hasMapTo);
        } else {
            return mapSet(collection, parameter, hasMapTo);
        }
    }

    @Deprecated
    private Object mapCollection(Collection<?> collection, Class<?> parameter, Class<?> type,
                                 List<Class<?>> no_repeat_classes) throws Exception {
        if(collection == null)
            return null;
        if(type.equals(List.class)) {
            return mapList(collection, parameter, no_repeat_classes);
        } else {
            return mapSet(collection, parameter, no_repeat_classes);
        }
    }

    public HashMap<String, Object> getMap() {
        return fields;
    }

    public <T> T mapAllTo(Class<T> c) {
        try {
            T instance = c.newInstance();
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                if(field.getAnnotation(Excluded.class) == null) {
                    Object value = fields.get(field.getName());

                    if(value instanceof Collection) {
                        if(field.getType().equals(List.class)) {
                            if(field.getGenericType() instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                                Class<?> parameter = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                value = mapList((Collection) value, parameter, false);
                            }
                        } else {
                            if(field.getGenericType() instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                                Class<?> parameter = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                value = mapSet((Collection) value, parameter, false);
                            }
                        }
                        field.set(instance, value);
                    }else if (value != null) {
                        if(!field.getType().equals(value.getClass())) {
                            if(!TypeChecker.isPrimitive(field.getType())) {
                                value = new Transaction(value).mapTo(field.getType());
                            }
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

    public <T> List<T> mapToList(Class<T> c) {
        return mapList(col, c, false);
    }

    public <T> Set<T> mapToSet(Class<T> c) {
        return mapSet(col, c, false);
    }

    private boolean isReaper(Class<?> c) {
        return NO_REPEAT_CLASSES.parallelStream().anyMatch(item->item.equals(c));
    }
}
