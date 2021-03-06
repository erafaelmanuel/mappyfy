package com.rem.mappyfy;

public class Singular extends Optional {

    public Singular(Object arg) {
        super(arg);
    }

    public void to(final Object o) {
        try {
            if (getBranches().size() != 0 && o != null) {
                final var branch = getBranches().iterator().next();
                final var transaction = new Transaction(branch);
                transaction.bind(o);
            } else {
                throw new RuntimeException("Error!");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public <T> T toInstanceOf(Class<T> c) {
        try {
            if (getBranches().size() != 0) {
                final var branch = getBranches().iterator().next();
                final var transaction = new Transaction<T>(branch);
                return transaction.mkInstance(c);
            } else {
                throw new RuntimeException("Error!");
            }
        } catch (RuntimeException e) {
            return null;
        }
    }

    public Singular bind(String f1, String f2) {
        if (getBranches().size() != 0) {
            final var branch = getBranches().iterator().next();
            var node = branch.getNodes().get(f1);
            if (node.getValue() != null) {
                final var other = new Node(f2, node.getType(), node.getValue());
                node.getLink().add(other);
                branch.getNodes().put(f2, other);
            } else if ((node = branch.getNodes().get(f2)) != null) {
                final var other = new Node(f1, node.getType(), node.getValue());
                node.getLink().add(other);
                branch.getNodes().put(f1, other);
            }
        }
        return this;
    }

    public Singular ignore(String field) {
        if (getBranches().size() != 0) {
            final var node = getBranches().iterator().next().getNodes().get(field);
            node.getLink().forEach(n -> ignore(n.getName()));
            getBranches().iterator().next().getNodes().remove(field);
        }
        return this;
    }

    public Singular parse(String field, TypeConverter typeConverter) {
        if (getBranches().size() != 0) {
            final var branch = getBranches().iterator().next();
            final var node = branch.getNodes().get(field);
            if (node.getValue() != null) {
                final var newValue = typeConverter.convert(node.getValue());
                if (newValue != null) {
                    node.setValue(newValue);
                    node.getLink().forEach(n -> n.setValue(newValue));
                    branch.getNodes().put(field, node);
                } else {
                    branch.getNodes().remove(field);
                }
            } else {
                branch.getNodes().remove(field);
            }
        }
        return this;
    }
}
