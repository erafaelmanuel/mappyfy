package io.ermdev.mapfierj;

public class ModelMapper<To, From> {

    private String sample;

    public Transaction<To, From> set(To obj) {
        return new Transaction<>(obj);
    }
}
