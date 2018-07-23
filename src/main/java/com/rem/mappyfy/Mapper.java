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
}
