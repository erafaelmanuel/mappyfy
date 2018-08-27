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
                    field.setAccessible(true);

                    final var node = new Node(field.getName(), field.getType().toString(), field.get(o));

                    nodes.put(node.getName(), node);
                }
            }
        } catch (Exception e) {
        }
    }

    public HashMap<String, Node> getNodes() {
        return nodes;
    }
}
