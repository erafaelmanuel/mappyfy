package io.ermdev.mapfierj.core;

public abstract class TypeConverterAdapter<T1, T2> {

    public abstract T2 convertTo(T1 o);
    public abstract T1 convertFrom(T2 o);
}
