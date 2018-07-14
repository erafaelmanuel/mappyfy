package com.rem.mappyfy;

import java.util.ArrayList;
import java.util.List;

public class Optional {

    private final List<Node> nodes = new ArrayList<>();

    public Optional(Object arg) {
        nodes.add(new Node(arg));
    }

    public Optional(Object[] args) {
        if (args != null) {
            for (Object o : args) {
                nodes.add(new Node(o));
            }
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
