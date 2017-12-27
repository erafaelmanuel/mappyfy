package io.ermdev.mapfierj.core;

public abstract class TypeConverterAdapter<T1, T2> {

    public abstract T1 convertTo(T2 o);
    public abstract T2 convertFrom(T1 o);
}
