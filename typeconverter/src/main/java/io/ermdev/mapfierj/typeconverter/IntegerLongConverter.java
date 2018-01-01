package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;
import io.ermdev.mapfierj.exception.TypeException;

@TypeConverter
public class IntegerLongConverter extends TypeConverterAdapter<Integer, Long> {

    @Override
    public Object convert(Object o) throws TypeException {
        if(o != null) {
            if(o instanceof Integer)
                return convertTo((Integer) o);
            else if(o instanceof Long)
                return convertFrom((Long) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public Long convertTo(Integer o) {
        return o.longValue();
    }

    @Override
    public Integer convertFrom(Long o) {
        return o.intValue();
    }
}
