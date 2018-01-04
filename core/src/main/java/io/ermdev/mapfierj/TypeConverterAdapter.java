package io.ermdev.mapfierj;

public abstract class TypeConverterAdapter<T1, T2> {

    protected Object o;

    public TypeConverterAdapter(Object obj) {
        o = obj;
    }

    public abstract Object convert() throws TypeException;
    public abstract T2 convertTo(T1 o) throws TypeException;
    public abstract T1 convertFrom(T2 o) throws TypeException;

    public Object getObject() {
        return o;
    }

    public void setObject(Object o) {
        this.o = o;
    }
}
