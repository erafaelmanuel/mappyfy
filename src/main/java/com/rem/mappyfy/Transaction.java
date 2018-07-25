package com.rem.mappyfy;

import java.lang.reflect.Field;

public class Transaction<T> {

    private Branch branch;
    private T newInstance;

    public Transaction(Branch branch) {
        this.branch = branch;
    }

    public T mkInstance(Class<T> t) {
        try {
            newInstance = t.newInstance();
            bind(newInstance);
        } catch (Exception e) {}
        return newInstance;
    }

    public void bind(Object newInstance) {
        final Field fields[] = newInstance.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                field.setAccessible(true);

                final String name = field.getName();
                final Node node = branch.getNodes().get(name);

                if (node != null) {
                    final Object o = node.getValue();
                    if (o != null) {
                        if (field.getType().toString().equals(node.getType())) {
                            field.set(newInstance, o);
                        } else {
                            try {
                                field.set(newInstance, o);
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}