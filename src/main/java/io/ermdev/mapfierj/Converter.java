package io.ermdev.mapfierj;

public class Converter<To, From> {

    private To obj;

    public Converter(To obj) {
        this.obj=obj;
    }

    public <T> T mapTo(Class<T> _class) throws IllegalAccessException, InstantiationException {
        return _class.newInstance();
    }

    public From map() {
        From f = null;
        return f;
    }
}
