package io.ermdev.mapfierj;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class Transaction {

    private final HashMap<String, Object> fields = new HashMap<>();

    private final List<Class<?>> NO_REPEAT_CLASSES = new ArrayList<>();

    public Transaction(HashMap<String, Object> map) {
        fields.clear();
        fields.putAll(map);
    }

    public Transaction(Object obj) throws Exception {
        if(obj == null)
            return;
        for(Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);

            MapTo maps = field.getAnnotation(MapTo.class);
            if(maps != null && value != null) {
                if(value instanceof Collection || maps.collection()) {
                    final Collection<?> collection = (Collection) value;
                    if(maps.type().equals(List.class))
                        fields.put(field.getName(), mapList(collection, maps.value()));
                    else if(maps.type().equals(Set.class))
                        fields.put(field.getName(), mapSet(collection, maps.value()));
                    else {
                        if(field.getType().equals(List.class))
                            fields.put(field.getName(), mapList(collection, maps.value()));
                        else if(field.getType().equals(Set.class))
                            fields.put(field.getName(), mapSet(collection, maps.value()));
                    }
                } else {
                    fields.put(field.getName(), new Transaction(value).mapTo(maps.value()));
                }
            } else {
                fields.put(field.getName(), value);
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
                        fields.put(field.getName(), mapList(collection, maps.value()));
                    else if(maps.type().equals(Set.class))
                        fields.put(field.getName(), mapSet(collection, maps.value()));
                    else {
                        if(field.getType().equals(List.class))
                            fields.put(field.getName(), mapList(collection, maps.value()));
                        else if(field.getType().equals(Set.class))
                            fields.put(field.getName(), mapSet(collection, maps.value()));
                    }
                } else {
                    fields.put(field.getName(), new Transaction(value, no_repeat_classes).mapTo(maps.value()));
                }
            } else {
                fields.put(field.getName(), value);
            }
        }

    }

    public <T> List<T> mapList(Collection<?> collection, Class<T> c) throws Exception {
        List<T> list = new ArrayList<>();
        if(collection == null)
            return null;
        for(Object o : collection) {
            list.add(new Transaction(o).mapTo(c));
        }
        return list;
    }

    @Deprecated
    private Object mapList(Collection<?> collection, Class<?> c, List<Class<?>> no_repeat_classes) throws Exception {
        Collection<Object> list = new ArrayList<>();
        for(Object o : collection) {
            list.add(new Transaction(o, no_repeat_classes).mapTo(c));
        }
        return list;
    }

    public <T> Set<T> mapSet(Collection collection, Class<T> c) throws Exception {
        if(collection == null)
            return null;
        Set<T> set = new HashSet<>();
        for(Object o : collection) {
            set.add(new Transaction(o).mapTo(c));
        }
        return set;
    }

    @Deprecated
    private Object mapSet(Collection collection, Class<?> c, List<Class<?>> no_repeat_classes) throws Exception {
        Collection<Object> set = new HashSet<>();
        for(Object o : collection) {
            set.add(new Transaction(o, no_repeat_classes).mapTo(c));
        }
        return set;
    }

    private Object mapCollection(Collection<?> collection, Class<?> parameter, Class<?> type) throws Exception {
        if(type.equals(List.class)) {
            return mapList(collection, parameter);
        } else {
            return mapSet(collection, parameter);
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
                                value = mapCollection((Collection) value, parameter, List.class);
                            }
                        } else {
                            if(field.getGenericType() instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                                Class<?> parameter = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                value = mapCollection((Collection) value, parameter, Set.class);
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

    private boolean isReaper(Class<?> c) {
        return NO_REPEAT_CLASSES.parallelStream().anyMatch(item->item.equals(c));
    }
}
