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

    public Plural field(String from, String to) {
        getBranches().parallelStream().forEach(branch -> {
            final Node node = branch.getNodes().get(from);
            if (node.getValue() != null) {
                branch.getNodes().put(to, new Node(node.getType(), node.getValue()));
                branch.getNodes().remove(from);
            }
        });
        return this;
    }

    public Plural ignore(String f) {
        getBranches().parallelStream().forEach(branch -> branch.getNodes().remove(f));
        return this;
    }

    public Plural parseFieldWith(String fromField, TypeConverter typeConverter) {
        getBranches().parallelStream().forEach(branch -> {
            final Node node = branch.getNodes().get(fromField);
            if (node.getValue() != null) {
                final Object newValue = typeConverter.convert(node.getValue());

                if (newValue != null) {
                    node.setValue(newValue);
                    branch.getNodes().put(fromField, node);
                } else {
                    branch.getNodes().remove(fromField);
                }
            } else {
                branch.getNodes().remove(fromField);
            }
        });
        return this;
    }
}
