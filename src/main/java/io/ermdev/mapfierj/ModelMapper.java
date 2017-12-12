package io.ermdev.mapfierj;

public class ModelMapper<To, From> {

    public Converter<To, From> set(To obj) {
        return new Converter<>(obj);
    }
}
