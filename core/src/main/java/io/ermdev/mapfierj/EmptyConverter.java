package io.ermdev.mapfierj;

import io.ermdev.mapfierj.legacy.TypeConverterAdapter;

@TypeConverter
public class EmptyConverter extends TypeConverterAdapter<Object, Object> {

    public EmptyConverter(Object obj) {
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
