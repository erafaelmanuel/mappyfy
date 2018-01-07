package io.ermdev.mapfierj;

public class NoConverter extends TypeConverterAdapter<Object, Object> {

    public NoConverter(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
        return o;
    }

    @Override
    public Object convertTo(Object o) throws TypeException {
        return o;
    }

    @Override
    public Object convertFrom(Object o) throws TypeException {
        return o;
    }
}
