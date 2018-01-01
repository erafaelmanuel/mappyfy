package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;
import io.ermdev.mapfierj.exception.TypeException;

@TypeConverter
public class CharStringConverter extends TypeConverterAdapter<Character, String> {

    @Override
    public Object convert(Object o) throws TypeException {
        if(o != null) {
            if(o instanceof Character)
                return convertTo((Character) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public String convertTo(Character o) {
        return String.valueOf(o);
    }

    @Override
    public Character convertFrom(String o) {
        try {
            return (o.trim().charAt(0));
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
