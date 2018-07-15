package com.rem.mappyfy;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeConverter<A, B> {

    public Object convert(Object o) {
        if (o == null) return null;
        try {
            final Type types[] = (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments());
            if (types[0].equals(o.getClass())) {
                return convertTo((A) o);
            } else {
                return convertFrom((B) o);
            }
        } catch (Exception e) {
            return null;
        }
    }


    public abstract B convertTo(A o) throws Exception;

    public abstract A convertFrom(B o) throws Exception;
}
