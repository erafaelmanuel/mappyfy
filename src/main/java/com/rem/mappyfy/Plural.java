package com.rem.mappyfy;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

public class Plural extends Optional {

    public Plural(Collection arg) {
        super(arg);
    }

    public Plural(Object[] arg) {
        super(arg);
    }

    public <T> void map(Class<T> c, Consumer<? super T> action) {
        for (Branch branch : getBranches()) {
            final Transaction<T> transaction = new Transaction<>(branch);
            action.accept(transaction.mkInstance(c));
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArrayOf(Class<T> c) {
        T array[] = (T[]) Array.newInstance(c, getBranches().size());
        for (int i = 0; i < array.length; i++) {
            final Transaction<T> transaction = new Transaction<>(getBranches().get(i));
            array[i] = transaction.mkInstance(c);
        }
        return array;
    }

    public <T> List<T> toListOf(Class<T> c) {
        final List<T> list = new ArrayList<>();

        getBranches().forEach(branch -> list.add(new Transaction<T>(branch).mkInstance(c)));
        return list;
    }

    public <T> Set<T> toSetOf(Class<T> c) {
        final Set<T> set = new HashSet<>();

        getBranches().forEach(branch -> set.add(new Transaction<T>(branch).mkInstance(c)));
        return set;
    }

    public Plural bind(String f1, String f2) {
        getBranches().parallelStream().forEach(branch -> {
            Node node = branch.getNodes().get(f1);

            if (node.getValue() != null) {
                final Node other = new Node(f2, node.getType(), node.getValue());

                node.getLink().add(other);
                branch.getNodes().put(f2, other);

            } else if ((node = branch.getNodes().get(f2)) != null) {
                final Node other = new Node(f1, node.getType(), node.getValue());

                node.getLink().add(other);
                branch.getNodes().put(f1, other);
            }
        });
        return this;
    }

    public Plural ignore(String f) {
        getBranches().forEach(branch -> {
            final Node node = branch.getNodes().get(f);

            if (node != null) {
                node.getLink().forEach(n -> ignore(n.getName()));
                branch.getNodes().remove(f);
            }
        });
        return this;
    }

    public Plural parse(String field, TypeConverter typeConverter) {
        getBranches().parallelStream().forEach(branch -> {
            final Node node = branch.getNodes().get(field);

            if (node.getValue() != null) {
                final Object newValue = typeConverter.convert(node.getValue());
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
        });
        return this;
    }
}
