package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;
import io.ermdev.mapfierj.exception.TypeException;

@TypeConverter
public class BooleanStringConverter extends TypeConverterAdapter<Boolean, String> {

    @Override
    public Object convert(Object o) throws TypeException {
        if(o != null) {
            if(o instanceof Boolean)
                return convertTo((Boolean) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public String convertTo(Boolean o) {
        return String.valueOf(o);
    }

    @Override
    public Boolean convertFrom(String o) {
        try {
            return Boolean.getBoolean(o);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
