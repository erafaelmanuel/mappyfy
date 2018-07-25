package com.rem.mappyfy;

import java.util.Collection;

public class Mapper {

    public Singular from(Object o) {
        return new Singular(o);
    }

    public Plural from(Object[] o) {
        return new Plural(o);
    }

    public Plural from(Collection o) {
        return new Plural(o);
    }

    public Only set(Object[] o) {
        return new Only(o);
    }

    public Only set(Collection o) {
        return new Only(o);
    }
}
