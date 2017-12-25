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

//    public Transaction(Object o) throws Exception {
//        if(o != null) {
//            for (Field field : o.getClass().getDeclaredFields()) {
//                field.setAccessible(true);
//                Object value = field.get(o);
//
//                MapTo maps = field.getAnnotation(MapTo.class);
//                if (maps != null && value != null) {
//                    if (value instanceof Collection || maps.collection()) {
//                        final Collection collection = (Collection) value;
//                        if (maps.type().equals(List.class)) {
//                            fields.put(field.getName(), mapList(collection, maps.value(), true));
//                        } else if (maps.type().equals(Set.class)) {
//                            fields.put(field.getName(), mapSet(collection, maps.value(), true));
//                        } else {
//                            if (field.getType().equals(List.class)) {
//                                fields.put(field.getName(), mapList(collection, maps.value(), true));
//                            } else if (field.getType().equals(Set.class)) {
//                                fields.put(field.getName(), mapSet(collection, maps.value(), true));
//                            }
//                        }
//                    } else {
//                        fields.put(field.getName(), new Transaction(value).mapTo(maps.value()));
//                    }
//                } else {
//                    fields.put(field.getName(), value);
//                }
//            }
//        }
//    }

    @Deprecated
    private Transaction(Object o) throws Exception {
        if(o == null)
            return;

        if(isReaper(o.getClass())) {
            return;
        } else {
            if(o.getClass().getAnnotation(NoRepeat.class) != null)
                getNO_REPEAT_CLASSES().add(o.getClass());
        }

        for(Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(o);

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
                    fields.put(field.getName(), new Transaction(value).mapTo(maps.value()));
                }
            } else {
                fields.put(field.getName(), value);
            }
        }

    }

    /**
     *
     * @param collection
     * @param c the class you will bind to your object or collection
     * @param hasMapTo return true if the generic class of the collection is annotated of @MapTo
     * @param <T>
     * @return
     */
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

    private Object mapCollection(Collection<?> collection, Class<?> parameter, Class<?> type, boolean hasMapTo) {
        if(type.equals(List.class)) {
            return mapList(collection, parameter, hasMapTo);
        } else {
            return mapSet(collection, parameter, hasMapTo);
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

    public List<Class<?>> getNO_REPEAT_CLASSES() {
        return NO_REPEAT_CLASSES;
    }
}
