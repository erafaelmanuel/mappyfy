package com.rem.mappyfy;

public class Singular extends Optional {

    public Singular(Object arg) {
        super(arg);
    }

    public void to(final Object o) {
        try {
            if (getBranches().size() != 0 && o != null) {
                final Branch branch = getBranches().iterator().next();
                final Transaction transaction = new Transaction(branch);

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
                final Branch branch = getBranches().iterator().next();
                final Transaction<T> transaction = new Transaction<>(branch);

                return transaction.mkInstance(c);
            } else {
                throw new RuntimeException("Error!");
            }
        } catch (RuntimeException e) {
            return null;
        }
    }

    public Singular field(String from, String to) {
        for (Branch branch : getBranches()) {
            final Node node = branch.getNodes().get(from);
            if (node.getValue() != null) {
                branch.getNodes().put(to, new Node(node.getType(), node.getValue()));
                branch.getNodes().remove(from);
                break;
            }
        }
        return this;
    }

    public Singular ignore(String fromField) {
        getBranches().iterator().next()
                .getNodes().remove(fromField);
        return this;
    }

    public Singular parseFieldWith(String field, TypeConverter typeConverter) {
        final Branch branch = getBranches().iterator().next();
        final Node node = branch.getNodes().get(field);

        if (node.getValue() != null) {
            final Object newValue = typeConverter.convert(node.getValue());
            if (newValue != null) {
                node.setValue(newValue);
                branch.getNodes().put(field, node);
            } else {
                branch.getNodes().remove(field);
            }
        } else {
            branch.getNodes().remove(field);
        }
        return this;
    }
}
