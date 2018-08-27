package com.rem.mappyfy;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class Node {

    private String name;

    private String type;

    private Object value;

    private Set<Node> link = new HashSet<>();

    public Node() {
    }

    public Node(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static Node of(String name, Object value) {
        final Node node = new Node();
        try {
            final Field[] fields = value.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equalsIgnoreCase(name)) {
                    node.setName(name);
                    node.setType(field.getType().toString());
                    node.setValue(field.get(value));
                }
            }
        } catch (Exception e) {
        }
        return node;
    }

    public Set<Node> getLink() {
        return link;
    }

}
