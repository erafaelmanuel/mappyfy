package com.rem.mappyfy;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Branch {

    private HashMap<String, Node> nodes = new HashMap<>();

    public Branch(Object o) {
        try {
            if (o != null) {
                final Field fields[] = o.getClass().getDeclaredFields();
                for (Field field : fields) {
                    final com.rem.mappyfy.Field annotation;

                    field.setAccessible(true);
                    if (field.getAnnotation(Ignore.class) != null) {
                        continue;
                    }
                    if ((annotation = field.getAnnotation(com.rem.mappyfy.Field.class)) != null) {
                        final Node node = new Node(annotation.name(), field.getType().toString(), field.get(o));

                        nodes.put(node.getName(), node);
                    } else {
                        final Node node = new Node(field.getName(), field.getType().toString(), field.get(o));

                        nodes.put(node.getName(), node);
                    }
                }
            }
        } catch (Exception e) {}
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }
}
