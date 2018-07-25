package com.rem.mappyfy;

import java.lang.reflect.Field;

public class Node {

    private String type;

    private Object value;

    public Node() {}

    public Node(String type, Object value) {
        this.value = value;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Node of(String f, Object o) {
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equalsIgnoreCase(f)) {
                    return new Node(field.getType().toString(), field.get(o));
                }
            }
        } catch (Exception e) {}
        return null;
    }
}
