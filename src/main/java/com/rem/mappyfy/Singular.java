package com.rem.mappyfy;

public class Singular extends Optional {

    public Singular(Object arg) {
        super(arg);
    }

    public <T> T mapTo(Class<T> c) {
        try {
            if (getNodes().size() != 0) {
                final Node node = getNodes().iterator().next();
                return new Transaction<>(node, c).newInstance();
            } else {
                throw new RuntimeException("Error!");
            }
        } catch (RuntimeException e) {
            return null;
        }
    }

    public void mapTo(final Object o) {
        try {
            if (getNodes().size() != 0 && o != null) {
                final Node node = getNodes().iterator().next();
                final Transaction transaction = new Transaction();
                transaction.bind(node, o);
            } else {
                throw new RuntimeException("Error!");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Singular ignore(String f) {
        for (Node node : getNodes()) {
            node.getVariables().remove(f);
        }
        return this;
    }

    @Override
    public Singular field(String f1, String f2) {
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
