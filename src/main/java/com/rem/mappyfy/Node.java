package com.rem.mappyfy;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Node {

    private HashMap<String, Variable> variables = new HashMap<>();

    public Node(Object o) {
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
                        variables.put(name, new Variable(field.getType().toString(), field.get(o)));
                    } else {
                        variables.put(field.getName(), new Variable(field.getType().toString(), field.get(o)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }
}
