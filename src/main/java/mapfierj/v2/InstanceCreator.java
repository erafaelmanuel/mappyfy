package mapfierj.v2;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class InstanceCreator<T> {

    private T newInstance;

    InstanceCreator(Load load, Class<T> c) {
        try {
            newInstance = c.newInstance();
            final Field fields[] = newInstance.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                String name = field.getName();
                Object o = load.getFields().get(name);

                if (o != null) {
                    if (field.getType().equals(o.getClass())) {
                        field.set(newInstance, o);
                    } else if (o instanceof Collection) {
                        if (field.getGenericType() instanceof ParameterizedType) {

                            final ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                            final Class<?> parameter = (Class<?>) parameterizedType.getActualTypeArguments()[0];

                            if (field.getType().equals(List.class)) {
                                field.set(newInstance, mkList(o, field, parameter));
                            } else {
                                field.set(newInstance, mkSet(o, field, parameter));
                            }
                        }
                    } else {
                        field.set(newInstance, new InstanceCreator<>(new Load(o), field.getType()).newInstance());
                    }
                }
            }
        } catch (Exception e) {
            newInstance = null;
            e.printStackTrace();
        }
    }

    private <E> List<E> mkList(Object col, Field f, Class<E> c) {
        if (f.getGenericType() instanceof ParameterizedType) {
            List<E> list = new ArrayList<>();
            for (Object o : (Collection) col) {
                Load load = new Load(o);
                list.add(new InstanceCreator<E>(load, c).newInstance);
            }
            return list;
        } else {
            return null;
        }
    }

    private <E> Set<E> mkSet(Object col, Field f, Class<E> c) {
        if (f.getGenericType() instanceof ParameterizedType) {
            Set<E> set = new HashSet<>();
            for (Object o : (Collection) col) {
                Load load = new Load(o);
                set.add(new InstanceCreator<E>(load, c).newInstance);
            }
            return set;
        } else {
            return null;
        }
    }

    public T newInstance() {
        return newInstance;
    }
}