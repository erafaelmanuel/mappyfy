package com.rem.mappyfy;

import java.lang.reflect.Array;
import java.util.*;

public class Auxiliary {

    private final List<Node> nodes = new ArrayList<>();
    private final Collection<Object> collection = new ArrayList<>();

    public Auxiliary(Collection args) {
        collection.addAll(args);
    }

    public Auxiliary(Object[] args) {
        if (args != null) {
            collection.addAll(Arrays.asList(args));
        }
    }

    public Produce just(String field) {
        collection.forEach(o ->
                nodes.add(Node.of(field, o)));
        return new Produce();
    }

    public class Produce {

        @SuppressWarnings("unchecked")
        public <T> T[] toArrayOf(Class<T> c) {
            final T array[] = (T[]) Array.newInstance(c, nodes.size());
            var ctr = 0;
            for (var node : nodes) {
                if (c.isInstance(node.getValue())) {
                    array[ctr] = (T) node.getValue();
                    ctr++;
                }
            }
            return array;
        }

        @SuppressWarnings("unchecked")
        public <T> List<T> toListOf(Class<T> c) {
            final var list = new ArrayList<T>();
            nodes.forEach(node -> {
                if (c.isInstance(node.getValue())) {
                    list.add((T) node.getValue());
                }
            });
            return list;
        }

        @SuppressWarnings("unchecked")
        public <T> Set<T> toSetOf(Class<T> c) {
            final var set = new HashSet<T>();
            nodes.forEach(node -> {
                if (c.isInstance(node.getValue())) {
                    set.add((T) node.getValue());
                }
            });
            return set;
        }
    }
}
