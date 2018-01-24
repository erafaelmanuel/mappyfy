package io.ermdev.mapfierj.v2;

import io.ermdev.mapfierj.TypeException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeConverterAdapter<A, B> {

    private Object object;

    public Object convert(Object o) throws TypeException {
        if(o != null) {
            this.object = o;
            Type types[];
            try {
                types = (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments());
            } catch (ClassCastException e) {
                types = new Type[2];
                types[0] = Object.class;
                types[1] = Object.class;
            }
            if (types[0].equals(o.getClass())) {
                return convertTo((A) o);
            } else if (types[1].equals(o.getClass())) {
                return convertFrom((B) o);
            } else {
                throw new TypeException("Invalid Type");
            }
        } else {
            throw new TypeException("You can't convert a null object");
        }
    }

    public abstract B convertTo(A o) throws TypeException;

    public abstract A convertFrom(B o) throws TypeException;
}
