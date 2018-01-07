package io.ermdev.mapfierj;

import javax.jws.WebParam;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class Transaction {

    private List<Class<?>> repeaterClasses = new ArrayList<>();

    private HashMap<String, Object> fieldsToMap = new HashMap<>();

    private List<Object> collectionToMap = new ArrayList<>();

    private List<String> excludedFields = new ArrayList<>();

    private boolean invalidToMap;

    public Transaction(Collection collection) {
        if (collection != null)
            collectionToMap.addAll(collection);
    }

    public Transaction(HashMap<String, Object> map) {
        if (map != null)
            fieldsToMap.putAll(map);
    }

    public Transaction(Object o) throws Exception {
        if (o != null) {
            if (o.getClass().getAnnotation(NoRepeat.class) != null) {
                repeaterClasses.add(o.getClass());
            }
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getAnnotation(Ignore.class) != null) {
                    continue;
                }
                Object value = field.get(o);
                final String fieldName = FieldHelper.fieldName(field);
                final MapTo mapTo = field.getAnnotation(MapTo.class);
                final ConvertTo convertTo = field.getAnnotation(ConvertTo.class);

                if(value != null) {
                    if(convertTo != null) {
                        try {
                            Converter converter = new Converter();
                            converter.scanPackages(convertTo.scanPackages());
                            if((value = converter.apply(value, convertTo.value())) != null) {
                                fieldsToMap.put(fieldName, value);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (mapTo != null) {
                        if (value instanceof Collection || mapTo.collection()) {
                            final Collection collection = (Collection) value;
                            if (mapTo.type().equals(List.class)) {
                                fieldsToMap.put(fieldName, mapList(collection, mapTo.value(),
                                        true, repeaterClasses));
                            } else if (mapTo.type().equals(Set.class)) {
                                fieldsToMap.put(fieldName, mapSet(collection, mapTo.value(),
                                        true, repeaterClasses));
                            } else {
                                if (field.getType().equals(List.class)) {
                                    fieldsToMap.put(fieldName, mapList(collection, mapTo.value(),
                                            true, repeaterClasses));
                                } else if (field.getType().equals(Set.class)) {
                                    fieldsToMap.put(fieldName, mapSet(collection, mapTo.value(),
                                            true, repeaterClasses));
                                }
                            }
                        } else {
                            if (repeaterClasses.size() > 0) {
                                Transaction transaction = new Transaction(value, repeaterClasses);
                                transaction.setExcludedField(getExcludedField());

                                fieldsToMap.put(fieldName, transaction.mapTo(mapTo.value()));
                            } else {
                                Transaction transaction = new Transaction(value);
                                transaction.setExcludedField(getExcludedField());

                                fieldsToMap.put(fieldName, transaction.mapTo(mapTo.value()));
                            }
                        }
                    } else {
                        fieldsToMap.put(fieldName, value);
                    }
                }
            }
        } else {
            invalidToMap = true;
        }
    }

    public Transaction(Object o, List<Class<?>> classes) throws Exception {
        if (o != null && !isRepeater(classes, o.getClass())) {
            classes.parallelStream().forEach(this.repeaterClasses::add);
            if (o.getClass().getAnnotation(NoRepeat.class) != null) {
                this.repeaterClasses.add(o.getClass());
            }
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getAnnotation(Ignore.class) != null) {
                    continue;
                }
                Object value = field.get(o);
                String fieldName = FieldHelper.fieldName(field);

                MapTo maps = field.getAnnotation(MapTo.class);
                if (maps != null && value != null) {
                    if (value instanceof Collection || maps.collection()) {
                        final Collection<?> collection = (Collection) value;
                        if (maps.type().equals(List.class))
                            fieldsToMap.put(fieldName, mapList(collection, maps.value(),
                                    true, this.repeaterClasses));
                        else if (maps.type().equals(Set.class))
                            fieldsToMap.put(fieldName, mapSet(collection, maps.value(),
                                    true, this.repeaterClasses));
                        else {
                            if (field.getType().equals(List.class))
                                fieldsToMap.put(fieldName, mapList(collection, maps.value(),
                                        true, this.repeaterClasses));
                            else if (field.getType().equals(Set.class))
                                fieldsToMap.put(fieldName, mapSet(collection, maps.value(),
                                        true, this.repeaterClasses));
                        }
                    } else {
                        if (classes.size() > 0) {
                            Transaction transaction = new Transaction(value, this.repeaterClasses);
                            transaction.setExcludedField(getExcludedField());

                            fieldsToMap.put(fieldName, transaction.mapTo(maps.value()));
                        } else {
                            Transaction transaction = new Transaction(value);
                            transaction.setExcludedField(getExcludedField());

                            fieldsToMap.put(fieldName, transaction.mapTo(maps.value()));
                        }
                    }
                } else {
                    fieldsToMap.put(fieldName, value);
                }
            }
        } else {
            invalidToMap = true;
        }
    }

    private <T> List<T> mapList(Collection collection, Class<T> c, boolean hasMapTo, List<Class<?>> classes) {
        List<T> list = new ArrayList<>();
        try {
            if (collection == null)
                throw new RuntimeException("Collection must not null");
            if (hasMapTo) {
                for (Object o : collection) {
                    if (classes != null && classes.size() > 0) {
                        Transaction transaction = new Transaction(o, classes);
                        transaction.setExcludedField(getExcludedField());

                        T instance = transaction.mapTo(c);
                        if (instance != null)
                            list.add(instance);
                    } else {
                        Transaction transaction = new Transaction(o);
                        transaction.setExcludedField(getExcludedField());

                        T instance = transaction.mapTo(c);
                        if (instance != null)
                            list.add(instance);
                    }
                }
            } else {
                for (Object o : collection) {
                    if (classes != null && classes.size() > 0) {
                        Transaction transaction = new Transaction(o, classes);
                        transaction.setExcludedField(getExcludedField());

                        T instance = transaction.mapAllTo(c);
                        if (instance != null)
                            list.add(instance);
                    } else {
                        Transaction transaction = new Transaction(o);
                        transaction.setExcludedField(getExcludedField());

                        T instance = transaction.mapAllTo(c);
                        if (instance != null)
                            list.add(instance);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    private <T> Set<T> mapSet(Collection collection, Class<T> c, boolean hasMapTo, List<Class<?>> classes) {
        Set<T> set = new HashSet<>();
        try {
            if (collection == null)
                throw new RuntimeException("Collection must not null");
            if (hasMapTo) {
                for (Object o : collection) {
                    if (classes != null && classes.size() > 0) {
                        Transaction transaction = new Transaction(o, classes);
                        transaction.setExcludedField(getExcludedField());

                        T instance = transaction.mapTo(c);
                        if (instance != null)
                            set.add(instance);
                    } else {
                        Transaction transaction = new Transaction(o);
                        transaction.setExcludedField(getExcludedField());

                        T instance = transaction.mapTo(c);
                        if (instance != null)
                            set.add(instance);
                    }
                }
            } else {
                for (Object o : collection) {
                    if (classes != null && classes.size() > 0) {
                        Transaction transaction = new Transaction(o, classes);
                        transaction.setExcludedField(getExcludedField());

                        T instance = transaction.mapAllTo(c);
                        if (instance != null)
                            set.add(instance);
                    } else {
                        Transaction transaction = new Transaction(o);
                        transaction.setExcludedField(getExcludedField());

                        T instance = transaction.mapAllTo(c);
                        if (instance != null)
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

    public <T> T mapAllTo(Class<T> c) {
        if (invalidToMap) return null;
        try {
            T instance = c.newInstance();
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                if (!isExcluded(field)) {
                    Object value = fieldsToMap.get(field.getName());

                    if (value instanceof Collection) {
                        if (field.getType().equals(List.class)) {
                            if (field.getGenericType() instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                                Class<?> parameter = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                value = mapList((Collection) value, parameter, false, repeaterClasses);
                            }
                        } else {
                            if (field.getGenericType() instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                                Class<?> parameter = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                value = mapSet((Collection) value, parameter, false, repeaterClasses);
                            }
                        }
                        field.set(instance, value);
                    } else if (value != null) {
                        if (!field.getType().equals(value.getClass())) {
                            if (!TypeChecker.isPrimitive(field.getType())) {
                                if (repeaterClasses != null && repeaterClasses.size() > 0) {
                                    Transaction transaction = new Transaction(value , repeaterClasses);
                                    transaction.setExcludedField(getExcludedField());

                                    value = transaction.mapTo(field.getType());
                                } else {
                                    Transaction transaction = new Transaction(value);
                                    transaction.setExcludedField(getExcludedField());

                                    value = transaction.mapTo(field.getType());
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

    public Object mapAllTo(Object o) {
        if (invalidToMap) return null;
        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!isExcluded(field)) {
                    Object value = fieldsToMap.get(field.getName());

                    if (value instanceof Collection) {
                        if (field.getType().equals(List.class)) {
                            if (field.getGenericType() instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                                Class<?> parameter = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                value = mapList((Collection) value, parameter, false, repeaterClasses);
                            }
                        } else {
                            if (field.getGenericType() instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                                Class<?> parameter = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                                value = mapSet((Collection) value, parameter, false, repeaterClasses);
                            }
                        }
                        field.set(o, value);
                    } else if (value != null) {
                        if (!field.getType().equals(value.getClass())) {
                            if (!TypeChecker.isPrimitive(field.getType())) {
                                if (repeaterClasses != null && repeaterClasses.size() > 0) {
                                    Transaction transaction = new Transaction(value , repeaterClasses);
                                    transaction.setExcludedField(getExcludedField());

                                    value = transaction.mapTo(field.getType());
                                } else {
                                    Transaction transaction = new Transaction(value);
                                    transaction.setExcludedField(getExcludedField());

                                    value = transaction.mapTo(field.getType());
                                }
                            }
                        }
                        field.set(o, value);
                    }
                }
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T mapTo(Class<T> c) {
        if (invalidToMap) return null;
        try {
            T instance = c.newInstance();
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                if (!isExcluded(field)) {
                    Object value = fieldsToMap.get(field.getName());
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

    public Object mapTo(Object o) {
        if (invalidToMap) return null;
        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!isExcluded(field)) {
                    Object value = fieldsToMap.get(field.getName());
                    if (value != null)
                        field.set(o, value);
                }
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return o;
        }
    }

    public <T> List<T> mapToList(Class<T> c) {
        return mapList(collectionToMap, c, false, null);
    }

    public <T> Set<T> mapToSet(Class<T> c) {
        return mapSet(collectionToMap, c, false, null);
    }

    public Object mapToCollection(Class<?> c, Class<?> type) {
        if (type.equals(List.class) || type.equals(ArrayList.class)) {
            return mapList(collectionToMap, c, false, null);
        } else {
            return mapSet(collectionToMap, c, false, null);
        }
    }

    private boolean isRepeater(List<Class<?>> classes, Class<?> c) {
        return classes != null && classes.parallelStream().anyMatch(item -> item.equals(c));
    }

    private boolean isExcluded(Field field) {
        return field.getAnnotation(Excluded.class) != null || excludedFields.parallelStream()
                .anyMatch(item -> item.trim().equals(field.getName()));
    }

    public HashMap<String, Object> getMap() {
        return fieldsToMap;
    }

    public List<String> getExcludedField() {
        return excludedFields;
    }

    public void setExcludedField(List<String> excludedField) {
        this.excludedFields = excludedField;
    }
}
