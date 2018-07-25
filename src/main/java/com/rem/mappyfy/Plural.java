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

        getBranches().forEach(node -> list.add(new Transaction<T>(node).mkInstance(c)));
        return list;
    }

    public <T> Set<T> toSetOf(Class<T> c) {
        final Set<T> set = new HashSet<>();

        getBranches().forEach(node -> set.add(new Transaction<T>(node).mkInstance(c)));
        return set;
    }

    public Plural bind(String from, String to) {
        getBranches().parallelStream().forEach(node -> {
            final Node variable = node.getNodes().get(from);
            if (variable.getValue() != null) {
                node.getNodes().put(to, new Node(variable.getType(), variable.getValue()));
                node.getNodes().remove(from);
            }
        });
        return this;
    }

    public Plural ignore(String f) {
        getBranches().parallelStream().forEach(node -> node.getNodes().remove(f));
        return this;
    }

    public Plural parseFieldWith(String fromField, TypeConverter typeConverter) {
        getBranches().parallelStream().forEach(node -> {
            final Node variable = node.getNodes().get(fromField);
            if (variable.getValue() != null) {
                final Object newValue = typeConverter.convert(variable.getValue());
                if (newValue != null) {
                    variable.setValue(newValue);
                    node.getNodes().put(fromField, variable);
                } else {
                    node.getNodes().remove(fromField);
                }
            } else {
                node.getNodes().remove(fromField);
            }
        });
        return this;
    }
}
