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
        } catch (Exception e) {
        }
        return newInstance;
    }

    public void bind(Object newInstance) {
        final Field fields[] = newInstance.getClass().getDeclaredFields();


        for (Field field : fields) {
            try {
                final Bind bind;
                Node node = null;

                field.setAccessible(true);
                if (field.getAnnotation(Ignore.class) != null) {
                    continue;
                }
                if ((bind = field.getAnnotation(Bind.class)) != null) {
                    for (String name : bind.fields()) {
                        node = branch.getNodes().get(name);
                        if (node != null)
                            break;
                    }
                }
                if (node == null) {
                    node = branch.getNodes().get(field.getName());
                }
                if (node != null) {
                    final Object o = node.getValue();

                    if (o != null) {
                        if (field.getType().toString().equals(node.getType())) {
                            field.set(newInstance, o);
                        } else {
                            field.set(newInstance, o);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}