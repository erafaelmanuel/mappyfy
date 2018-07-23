package com.rem.mappyfy;

import java.lang.reflect.Field;

public class Transaction<T> {

    private Node node;
    private T newInstance;

    public Transaction(Node node) {
        this.node = node;
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
                final Variable variable = node.getVariables().get(name);

                if (variable != null) {
                    final Object o = variable.getValue();
                    if (o != null) {
                        if (field.getType().toString().equals(variable.getType())) {
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