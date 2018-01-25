package io.ermdev.mapfierj;

import io.ermdev.mapfierj.legacy.Converter;

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
                final String fieldName = FieldUtil.fieldName(field);
                final MapTo mta = field.getAnnotation(MapTo.class);
                final ConvertTo cta = field.getAnnotation(ConvertTo.class);

                if (value != null) {
                    if (cta != null) {
                        try {
                            final Converter converter = new Converter();
                            if (cta.converter().equals(EmptyConverter.class)) {
                                converter.scanPackages(cta.scanPackages());
                                if ((value = converter.convertTo(value, cta.value())) != null) {
                                    fieldsToMap.put(fieldName, value);
                                }
                            } else {
                                Converter.Session session = converter.openSession();
                                if ((value = session.set(value).adapter(cta.converter()).convert()) != null) {
                                    fieldsToMap.put(fieldName, value);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (mta != null) {
                        if (value instanceof Collection) {
                            final Collection collection = (Collection) value;
                            switch (mta.type().getValue()) {
                                case 2 :
                                    fieldsToMap.put(fieldName, mapList(collection, mta.value(),
                                            true, this.repeaterClasses));
                                    break;
                                case 3 :
                                    fieldsToMap.put(fieldName, mapSet(collection, mta.value(),
                                            true, this.repeaterClasses));
                                    break;
                                default :
                                    if (field.getType().equals(List.class))
                                        fieldsToMap.put(fieldName, mapList(collection, mta.value(),
                                                true, this.repeaterClasses));
                                    else if (field.getType().equals(Set.class))
                                        fieldsToMap.put(fieldName, mapSet(collection, mta.value(),
                                                true, this.repeaterClasses));
                                    break;
                            }
                        } else {
                            if (repeaterClasses.size() > 0) {
                                Transaction transaction = new Transaction(value, repeaterClasses);
                                transaction.setExcludedField(getExcludedField());

                                fieldsToMap.put(fieldName, transaction.mapTo(mta.value()));
                            } else {
                                Transaction transaction = new Transaction(value);
                                transaction.setExcludedField(getExcludedField());

                                fieldsToMap.put(fieldName, transaction.mapTo(mta.value()));
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
                final String fieldName = FieldUtil.fieldName(field);
                final MapTo mta = field.getAnnotation(MapTo.class);
                final ConvertTo cta = field.getAnnotation(ConvertTo.class);

                if (value != null) {
                    if (cta != null) {
                        Converter converter = new Converter();
                        if (cta.converter().equals(EmptyConverter.class)) {
                            converter.scanPackages(cta.scanPackages());
                            if ((value = converter.convertTo(value, cta.value())) != null) {
                                fieldsToMap.put(fieldName, value);
                            }
                        } else {
                            Converter.Session session = converter.openSession();
                            if ((value = session.set(value).adapter(cta.converter()).convert()) != null) {
                                fieldsToMap.put(fieldName, value);
                            }
                        }
                    }
                    if (mta != null) {
                        if (value instanceof Collection) {
                            final Collection<?> collection = (Collection) value;
                            switch (mta.type().getValue()) {
                                case 2 :
                                    fieldsToMap.put(fieldName, mapList(collection, mta.value(),
                                            true, this.repeaterClasses));
                                    break;
                                case 3 :
                                    fieldsToMap.put(fieldName, mapSet(collection, mta.value(),
                                            true, this.repeaterClasses));
                                    break;
                                default :
                                    if (field.getType().equals(List.class))
                                        fieldsToMap.put(fieldName, mapList(collection, mta.value(),
                                                true, this.repeaterClasses));
                                    else if (field.getType().equals(Set.class))
                                        fieldsToMap.put(fieldName, mapSet(collection, mta.value(),
                                                true, this.repeaterClasses));
                                    break;
                            }
                        } else {
                            if (classes.size() > 0) {
                                Transaction transaction = new Transaction(value, this.repeaterClasses);
                                transaction.setExcludedField(getExcludedField());

                                fieldsToMap.put(fieldName, transaction.mapTo(mta.value()));
                            } else {
                                Transaction transaction = new Transaction(value);
                                transaction.setExcludedField(getExcludedField());

                                fieldsToMap.put(fieldName, transaction.mapTo(mta.value()));
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

    private <T> List<T> mapList(Collection collection, Class<T> c, boolean hasMapTo, List<Class<?>> repeaterClasses) {
        List<T> list = new ArrayList<>();
        try {
            if (collection == null)
                throw new RuntimeException("Collection must not null");
            if (hasMapTo) {
                for (Object o : collection) {
                    if (repeaterClasses != null && repeaterClasses.size() > 0) {
                        Transaction transaction = new Transaction(o, repeaterClasses);
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
                    if (repeaterClasses != null && repeaterClasses.size() > 0) {
                        Transaction transaction = new Transaction(o, repeaterClasses);
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

    private <T> Set<T> mapSet(Collection collection, Class<T> c, boolean hasMapTo, List<Class<?>> repeaterClasses) {
        Set<T> set = new HashSet<>();
        try {
            if (collection == null)
                throw new RuntimeException("Collection must not null");
            if (hasMapTo) {
                for (Object o : collection) {
                    if (repeaterClasses != null && repeaterClasses.size() > 0) {
                        Transaction transaction = new Transaction(o, repeaterClasses);
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
                    if (repeaterClasses != null && repeaterClasses.size() > 0) {
                        Transaction transaction = new Transaction(o, repeaterClasses);
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

    @Deprecated
    private <T> Collection<T> mapCollection(final Collection collection, Class<T> c, boolean hasMapTo,
                                            List<Class<?>> repeaterClasses, Class<? extends Collection> typeOfCollection) {
        final Collection<T> list;
        try {
            switch (typeOfCollection.toString()) {
                case TypeUtil.LIST:
                    list = new ArrayList<>();
                    break;
                case TypeUtil.ARRAY_LIST:
                    list = new ArrayList<>();
                    break;
                default:
                    list = new ArrayList<>();
            }
            if (collection == null)
                throw new RuntimeException("Collection must not null");

            for (Object o : collection) {
                final Transaction transaction;
                final T instance;

                if (repeaterClasses != null && repeaterClasses.size() > 0) {
                    transaction = new Transaction(o, repeaterClasses);
                    transaction.setExcludedField(getExcludedField());
                } else {
                    transaction = new Transaction(o);
                    transaction.setExcludedField(getExcludedField());
                }
                if (hasMapTo) {
                    instance = transaction.mapTo(c);
                } else {
                    instance = transaction.mapAllTo(c);
                }
                if (instance != null)
                    list.add(instance);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> T mapAllTo(Class<T> c) {
        if (invalidToMap) return null;
        try {
            T instance = c.newInstance();
            for (Field field : c.getDeclaredFields()) {
                field.setAccessible(true);
                if (isNotExcluded(field)) {
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
                            if (!TypeUtil.isPrimitive(field.getType())) {
                                if (repeaterClasses != null && repeaterClasses.size() > 0) {
                                    Transaction transaction = new Transaction(value, repeaterClasses);
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
                if (isNotExcluded(field)) {
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
                            if (!TypeUtil.isPrimitive(field.getType())) {
                                if (repeaterClasses != null && repeaterClasses.size() > 0) {
                                    Transaction transaction = new Transaction(value, repeaterClasses);
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
                if (isNotExcluded(field)) {
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
                if (isNotExcluded(field)) {
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

    @Deprecated
    public Object mapToCollection(Class<?> type, Class<?> c) {
        if (type.equals(List.class) || type.equals(ArrayList.class)) {
            return mapList(collectionToMap, c, false, null);
        } else {
            return mapSet(collectionToMap, c, false, null);
        }
    }

    private boolean isRepeater(List<Class<?>> classes, Class<?> c) {
        return classes != null && classes.parallelStream().anyMatch(item -> item.equals(c));
    }

    private boolean isNotExcluded(Field field) {
        return !(field.getAnnotation(Excluded.class) != null || excludedFields.parallelStream()
                .anyMatch(item -> item.trim().equals(field.getName())));
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
