package io.ermdev.mapfierj;

@TypeConverter
public class EmptyConverter extends TypeConverterAdapter<Object, Object> {

    @Override
    public Object convertTo(Object o) throws TypeException {
        return o;
    }

    @Override
    public Object convertFrom(Object o) throws TypeException {
        return o;
    }
}
