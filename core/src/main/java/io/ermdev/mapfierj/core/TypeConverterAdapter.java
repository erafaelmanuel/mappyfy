package io.ermdev.mapfierj.core;

import io.ermdev.mapfierj.exception.TypeException;

public abstract class TypeConverterAdapter<T1, T2> {

    public abstract Object convert(Object o) throws TypeException;
    public abstract T2 convertTo(T1 o);
    public abstract T1 convertFrom(T2 o);
}
