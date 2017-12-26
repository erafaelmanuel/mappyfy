package io.ermdev.mapfierj;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class Transaction {

    private List<Class<?>> classes = new ArrayList<>();

    private HashMap<String, Object> fields = new HashMap<>();

    private List<Object> col = new ArrayList<>();

    private boolean absoluteNull;


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
                            fields.put(field.getName(), mapSet(collection, maps.value(), true, classes));
                        } else {
                            if (field.getType().equals(List.class)) {
                                fields.put(field.getName(), mapList(collection, maps.value(), true, classes));
                            } else if (field.getType().equals(Set.class)) {
                                fields.put(field.getName(), mapSet(collection, maps.value(), true, classes));
                            }
                        }
                    } else {
                        if(classes.size() > 0) {
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

    public Transaction(Object o, List<Class<?>> classes) throws Exception {
        if(o != null && !isRepeater(classes, o.getClass())) {
            classes.parallelStream().forEach(this.classes::add);
            if (o.getClass().getAnnotation(NoRepeat.class) != null) {
                this.classes.add(o.getClass());
            }
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(o);

                MapTo maps = field.getAnnotation(MapTo.class);
                if (maps != null && value != null) {
                    if (value instanceof Collection || maps.collection()) {
                        final Collection<?> collection = (Collection) value;
                        if (maps.type().equals(List.class))
                            fields.put(field.getName(), mapList(collection, maps.value(), true, this.classes));
                        else if (maps.type().equals(Set.class))
                            fields.put(field.getName(), mapSet(collection, maps.value(), true, this.classes));
                        else {
                            if (field.getType().equals(List.class))
                                fields.put(field.getName(), mapList(collection, maps.value(), true, this.classes));
                            else if (field.getType().equals(Set.class))
                                fields.put(field.getName(), mapSet(collection, maps.value(), true, this.classes));
                        }
                    } else {
                        if (classes.size() > 0) {
                            fields.put(field.getName(), new Transaction(value, this.classes).mapTo(maps.value()));
                        } else {
                            fields.put(field.getName(), new Transaction(value).mapTo(maps.value()));
                        }
                    }
                } else {
                    fields.put(field.getName(), value);
                }
            }
        } else {
            absoluteNull=true;
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
                        T instance = new Transaction(o, classes).mapTo(c);
                        if(instance != null)
                            list.add(instance);
                    } else {
                        T instance = new Transaction(o).mapTo(c);
                        if(instance != null)
                            list.add(instance);
                    }
                }
            } else {
                for (Object o : collection) {
                    if(classes != null && classes.size() > 0) {
                        T instance = new Transaction(o, classes).mapAllTo(c);
                        if(instance != null)
                            list.add(instance);
                    } else {
                        T instance = new Transaction(o).mapAllTo(c);
                        if(instance != null)
                            list.add(instance);
                    }
                }
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
            return list;
        }
    }

    private <T> Set<T> mapSet(Collection collection, Class<T> c, boolean hasMapTo, List<Class<?>> classes) {
        Set<T> set = new HashSet<>();
        try {
            if (collection == null)
                throw new RuntimeException("Collection must not null");
            if(hasMapTo) {
                for (Object o : collection) {
                    if(classes != null && classes.size() > 0) {
                        T instance = new Transaction(o, classes).mapTo(c);
                        if(instance != null)
                            set.add(instance);
                    } else {
                        T instance = new Transaction(o).mapTo(c);
                        if(instance != null)
                            set.add(instance);
                    }

                }
            } else {
                for (Object o : collection) {
                    if(classes != null && classes.size() > 0) {
                        T instance = new Transaction(o, classes).mapAllTo(c);
                        if(instance != null)
                            set.add(instance);
                    } else {
                        T instance = new Transaction(o).mapAllTo(c);
                        if(instance != null)
                            set.add(instance);
                    }
                }
            }
            return set;
        } catch (Exception e) {
            e.printStackTrace();
            return set;
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
                                value = mapSet((Collection) value, parameter, false, classes);
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

    public <T> T mapTo(Class<T> c) {
        if(absoluteNull) return null;
        try {
            T instance = c.newInstance();
            for (Field field : c.getDeclaredFields()) {
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
        return mapSet(col, c, false, null);
    }

    public Object mapToCollection(Class<?> c, Class<?> type) {
        if(type.equals(List.class) || type.equals(ArrayList.class)) {
            return mapList(col, c, false, null);
        } else {
            return mapSet(col, c, false, null);
        }
    }

    private boolean isRepeater(List<Class<?>> classes, Class<?> c) {
        return classes != null && classes.parallelStream().anyMatch(item->item.equals(c));
    }
}
