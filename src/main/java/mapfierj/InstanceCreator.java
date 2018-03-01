package mapfierj;

import java.lang.reflect.Array;
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
                Variable variable = load.getVariables().get(name);
                Object o = variable.getValue();
                if (o != null) {
                    System.out.println(field.getType().getTypeName() + " " + variable.getType());
                    if (field.getType().toString().equals(variable.getType())) {
                        field.set(newInstance, o);
                    } else if (o instanceof Object[]) {
                        field.set(newInstance, mkArray(o, field.getType()));
                    } else if (o instanceof Collection) {
                        if (field.getGenericType() instanceof ParameterizedType) {

                            final ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                            final Class<?> parameter = (Class<?>) parameterizedType.getActualTypeArguments()[0];

                            if (field.getType().equals(List.class)) {
                                field.set(newInstance, mkList(o, parameter));
                            } else {
                                field.set(newInstance, mkSet(o, parameter));
                            }
                        }
                    } else {
                        field.set(newInstance, new InstanceCreator<>(new Load(o), field.getType()).newInstance());
                    }
                } else {
                    System.out.println("its null");
                }
            }
        } catch (Exception e) {
            newInstance = null;
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <E> E[] mkArray(Object arg, Class<E> c) {
        try {
            Object o[] = (Object[]) arg;
            E array[] = (E[]) Array.newInstance(c, o.length);

            for (int ctr = 0; ctr < o.length; ctr++) {
                array[ctr] = new InstanceCreator<>(new Load(o[ctr]), c).newInstance();
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private <E> List<E> mkList(Object arg, Class<E> c) {
        List<E> list = new ArrayList<>();
        for (Object o : (Collection) arg) {
            Load load = new Load(o);
            list.add(new InstanceCreator<>(load, c).newInstance);
        }
        return list;
    }

    private <E> Set<E> mkSet(Object arg, Class<E> c) {
        Set<E> set = new HashSet<>();
        for (Object o : (Collection) arg) {
            Load load = new Load(o);
            set.add(new InstanceCreator<>(load, c).newInstance);
        }
        return set;
    }

    public T newInstance() {
        return newInstance;
    }

    public static <E> E create(Class<E> c) {
        try {
            return c.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}