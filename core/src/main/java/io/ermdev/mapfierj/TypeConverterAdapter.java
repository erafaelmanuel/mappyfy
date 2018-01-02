package io.ermdev.mapfierj;

public abstract class TypeConverterAdapter<T1, T2> {

    public abstract Object convert(Object o) throws TypeException;
    public abstract T2 convertTo(T1 o) throws TypeException;
    public abstract T1 convertFrom(T2 o) throws TypeException;
}
