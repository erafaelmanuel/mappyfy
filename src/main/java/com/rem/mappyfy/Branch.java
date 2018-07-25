package com.rem.mappyfy;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Branch {

    private HashMap<String, Node> nodes = new HashMap<>();

    public Branch(Object o) {
        try {
            if (o != null) {
                Field fields[] = o.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getAnnotation(Ignore.class) != null) {
                        continue;
                    }
                    final com.rem.mappyfy.Field annotation;
                    if ((annotation = field.getAnnotation(com.rem.mappyfy.Field.class)) != null) {
                        String name = annotation.name();
                        nodes.put(name, new Node(field.getType().toString(), field.get(o)));
                    } else {
                        nodes.put(field.getName(), new Node(field.getType().toString(), field.get(o)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }
}
