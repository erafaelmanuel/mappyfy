package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;
import io.ermdev.mapfierj.exception.TypeException;

@TypeConverter
public class LongStringConverter extends TypeConverterAdapter<Long, String> {

    @Override
    public Object convert(Object o) throws TypeException{
        if(o != null) {
            if(o instanceof Long)
                return convertTo((Long) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public String convertTo(Long o) {
        return String.valueOf(o);
    }

    @Override
    public Long convertFrom(String o) {
        try {
            return Long.parseLong(o);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
