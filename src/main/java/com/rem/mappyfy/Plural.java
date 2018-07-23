package com.rem.mappyfy;

import java.lang.reflect.Array;
import java.util.Collection;
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
    public <T> T[] toArray(Class<T> c) {
        T array[] = (T[]) Array.newInstance(c, getNodes().size());
        for (int i = 0; i < array.length; i++) {
            final Transaction<T> transaction = new Transaction<>(getNodes().get(i));
            array[i] = transaction.mkInstance(c);
        }
        return array;
    }

    public Plural bind(String from, String to) {
        for (Node node : getNodes()) {
            final Variable variable = node.getVariables().get(from);
            if (variable.getValue() != null) {
                node.getVariables().put(to, new Variable(variable.getType(), variable.getValue()));
                node.getVariables().remove(from);
            }
        }
        return this;
    }

    public Plural ignore(String f) {
        for (Node node : getNodes()) {
            node.getVariables().remove(f);
        }
        return this;
    }
}
