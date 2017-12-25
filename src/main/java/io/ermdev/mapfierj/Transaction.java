package io.ermdev.mapfierj;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class Transaction {

    List<Class<?>> classes = new ArrayList<>();

    private HashMap<String, Object> fields = new HashMap<>();

    private List<Object> col = new ArrayList<>();

    private Boolean absoluteNull = false;

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
            List<Class<?>> classes = new ArrayList<>();
            if(o.getClass().getAnnotation(NoRepeat.class) != null) {
                classes.add(o.getClass());
            }
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(o);

                MapTo maps = field.getAnnotation(MapTo.class);
                if (maps != null && value != null) {
                    if (value instanceof Collection || maps.collection()) {
                        final Collection collection = (Collection) value;
                        if (maps.type().equals(List.class)) {
                            fields.put(field.getName(), mapList(collection, maps.value(), true, classes));
                        } else if (maps.type().equals(Set.class)) {
                            fields.put(field.getName(), mapSet(collection, maps.value(), true));
                        } else {
                            if (field.getType().equals(List.class)) {
                                fields.put(field.getName(), mapList(collection, maps.value(), true, classes));
                            } else if (field.getType().equals(Set.class)) {
                                fields.put(field.getName(), mapSet(collection, maps.value(), true));
                            }
                        }
                    } else {
                        if(classes != null && classes.size() > 0) {
                            fields.put(field.getName(), new Transaction(value, classes).mapTo(maps.value()));
                        } else {
                            fields.put(field.getName(), new Transaction(value).mapTo(maps.value()));
                        }
                    }
                } else {
                    fields.put(field.getName(), value);
                }
            }
        }
    }

    @Deprecated
    public Transaction(Object o, List<Class<?>> classes) throws Exception {
        System.out.println(o);
        System.out.println(classes);

        this.classes=classes;
        if(o == null) {
            absoluteNull=true;
            return;
        } if(isReaper(classes, o.getClass())) {
            absoluteNull=true;
            return;
        } if(classes != null && o.getClass().getAnnotation(NoRepeat.class) != null) {
            classes.add(o.getClass());
        }
        for(Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(o);

            MapTo maps = field.getAnnotation(MapTo.class);
            if(maps != null && value != null) {
                if(value instanceof Collection || maps.collection()) {
                    final Collection<?> collection = (Collection) value;
                    if(maps.type().equals(List.class))
                        fields.put(field.getName(), mapList(collection, maps.value(), true, classes));
                    else if(maps.type().equals(Set.class))
                        fields.put(field.getName(), mapSet(collection, maps.value(), true));
                    else {
                        if(field.getType().equals(List.class))
                            fields.put(field.getName(), mapList(collection, maps.value(), true, classes));
                        else if(field.getType().equals(Set.class))
                            fields.put(field.getName(), mapSet(collection, maps.value(), true));
                    }
                } else {
                    if(classes != null && classes.size() > 0) {
                        fields.put(field.getName(), new Transaction(value, classes).mapTo(maps.value()));
                    } else {
                        fields.put(field.getName(), new Transaction(value).mapTo(maps.value()));
                    }
                }
            } else {
                fields.put(field.getName(), value);
            }
        }

    }

    private <T> List<T> mapList(Collection collection, Class<T> c, boolean hasMapTo, List<Class<?>> classes) {
        List<T> list = new ArrayList<>();
        try {
            if (collection == null)
                throw new RuntimeException("Collection must not null");
            if(hasMapTo) {
                for (Object o : collection) {
                    if(classes != null && classes.size() > 0) {
                        list.add(new Transaction(o, classes).mapTo(c));
                    } else {
                        list.add(new Transaction(o).mapTo(c));
                    }
                }
            } else {
                for (Object o : collection) {
                    if(classes != null && classes.size() > 0) {
                        list.add(new Transaction(o, classes).mapAllTo(c));
                    } else {
                        list.add(new Transaction(o).mapAllTo(c));
                    }
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
                    if(classes != null && classes.size() > 0) {
                        set.add(new Transaction(o, classes).mapTo(c));
                    } else {
                        set.add(new Transaction(o).mapTo(c));
                    }

                }
            } else {
                for (Object o : collection) {
                    if(classes != null && classes.size() > 0) {
                        set.add(new Transaction(o, classes).mapAllTo(c));
                    } else {
                        set.add(new Transaction(o).mapAllTo(c));
                    }
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
            return mapList(collection, parameter, hasMapTo, null);
        } else {
            return mapSet(collection, parameter, hasMapTo);
        }
    }

    public HashMap<String, Object> getMap() {
        return fields;
    }

    public <T> T mapAllTo(Class<T> c) {
        if(absoluteNull) return null;
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
                                value = mapList((Collection) value, parameter, false, classes);
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
                                if(classes != null && classes.size() > 0) {
                                    value = new Transaction(value, classes).mapTo(field.getType());
                                } else {
                                    value = new Transaction(value).mapTo(field.getType());
                                }
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
        if(absoluteNull) return null;
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
        return mapList(col, c, false, null);
    }

    public <T> Set<T> mapToSet(Class<T> c) {
        return mapSet(col, c, false);
    }

    private boolean isReaper(List<Class<?>> list, Class<?> c) {
        return list != null && list.parallelStream().anyMatch(item->item.equals(c));
    }
}
