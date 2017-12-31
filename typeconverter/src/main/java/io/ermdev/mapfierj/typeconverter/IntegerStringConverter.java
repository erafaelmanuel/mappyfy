package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;
import io.ermdev.mapfierj.exception.TypeException;

@TypeConverter
public class IntegerStringConverter extends TypeConverterAdapter<Integer, String> {

    @Override
    public Object convert(Object o) throws TypeException{
        if(o != null) {
            if(o instanceof Integer)
                return convertTo((Integer) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public String convertTo(Integer o) {
        return String.valueOf(o);
    }

    @Override
    public Integer convertFrom(String o) {
        try {
            return Integer.parseInt(o);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
