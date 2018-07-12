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

    public Optional ignore(String f) {
        for (Node node : getNodes()) {
            node.getVariables().remove(f);
        }
        return this;
    }

    public Optional field(String f1, String f2) {
        for (Node node : getNodes()) {
            final Variable variable = node.getVariables().get(f1);
            if (variable.getValue() != null) {
                node.getVariables().put(f2, new Variable(variable.getType(), variable.getValue()));
                node.getVariables().remove(f1);
            }
        }
        return this;
    }
}
