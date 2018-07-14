package com.rem.mappyfy;

import java.util.Collection;

public class Mapper {

    public Singular set(Object o) {
        return new Singular(o);
    }

    public Plural set(Object[] o) {
        return new Plural(o);
    }

    public Plural set(Collection o) {
        return new Plural(o);
    }
}
