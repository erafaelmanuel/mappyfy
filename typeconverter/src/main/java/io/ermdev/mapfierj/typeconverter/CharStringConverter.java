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
    public String convertTo(Character o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Character convertFrom(String o) throws TypeException {
        try {
            return (o.trim().charAt(0));
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
