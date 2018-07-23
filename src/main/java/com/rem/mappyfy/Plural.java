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
        for (Node node : getNodes()) {
            final Transaction<T> transaction = new Transaction<>(node);
            action.accept(transaction.mkInstance(c));
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArrayOf(Class<T> c) {
        T array[] = (T[]) Array.newInstance(c, getNodes().size());
        for (int i = 0; i < array.length; i++) {
            final Transaction<T> transaction = new Transaction<>(getNodes().get(i));
            array[i] = transaction.mkInstance(c);
        }
        return array;
    }

    public <T> List<T> toListOf(Class<T> c) {
        final List<T> list = new ArrayList<>();

        getNodes().forEach(node -> list.add(new Transaction<T>(node).mkInstance(c)));
        return list;
    }

    public <T> Set<T> toSetOf(Class<T> c) {
        final Set<T> set = new HashSet<>();

        getNodes().forEach(node -> set.add(new Transaction<T>(node).mkInstance(c)));
        return set;
    }

    public Plural bind(String from, String to) {
        getNodes().parallelStream().forEach(node -> {
            final Variable variable = node.getVariables().get(from);
            if (variable.getValue() != null) {
                node.getVariables().put(to, new Variable(variable.getType(), variable.getValue()));
                node.getVariables().remove(from);
            }
        });
        return this;
    }

    public Plural ignore(String f) {
        getNodes().parallelStream().forEach(node -> node.getVariables().remove(f));
        return this;
    }

    public Plural parseFieldWith(String fromField, TypeConverter typeConverter) {
        getNodes().parallelStream().forEach(node -> {
            final Variable variable = node.getVariables().get(fromField);
            if (variable.getValue() != null) {
                final Object newValue = typeConverter.convert(variable.getValue());
                if (newValue != null) {
                    variable.setValue(newValue);
                    node.getVariables().put(fromField, variable);
                } else {
                    node.getVariables().remove(fromField);
                }
            } else {
                node.getVariables().remove(fromField);
            }
        });
        return this;
    }
}
