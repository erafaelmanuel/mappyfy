package com.rem.mappyfy;

public class Mapper {

    public Singular set(Object o) {
        return new Singular(o);
    }

    public Optional set(Object[] o) {
        return new Optional(o);
    }
}
