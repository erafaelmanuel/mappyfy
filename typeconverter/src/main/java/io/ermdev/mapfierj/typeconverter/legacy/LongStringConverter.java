package io.ermdev.mapfierj.typeconverter.legacy;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.legacy.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class LongStringConverter extends TypeConverterAdapter<Long, String> {

    public LongStringConverter() {
        super(null);
    }

    public LongStringConverter(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
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
    public String convertTo(Long o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Long convertFrom(String o) throws TypeException {
        try {
            return Long.parseLong(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
