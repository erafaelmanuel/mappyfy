package com.rem.mappyfy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Only {

    private Set<Object> objects;

    public Only(Set<Object> objects) {
        this.objects = objects;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArrayOf(Class<T> c) {
        final T array[] = (T[]) Array.newInstance(c, objects.size());
        int ctr = 0;

        for (Object o : objects) {
            if (c.isInstance(o)) {
                array[ctr] = (T) o;
                ctr++;
            }
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> toListOf(Class<T> c) {
        final List<T> list = new ArrayList<>();

        objects.forEach(o -> {
            if (c.isInstance(o)) {
                list.add((T) o);
            }
        });
        return list;
    }

    @SuppressWarnings("unchecked")
    public <T> Set<T> toSetOf(Class<T> c) {
        final Set<T> set = new HashSet<>();

        objects.forEach(o -> {
            if (c.isInstance(o)) {
                set.add((T) o);
            }
        });
        return set;
    }
}
