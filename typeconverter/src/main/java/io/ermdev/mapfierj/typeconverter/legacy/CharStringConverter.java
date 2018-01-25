package io.ermdev.mapfierj.typeconverter.legacy;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.legacy.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class CharStringConverter extends TypeConverterAdapter<Character, String> {

    public CharStringConverter() {
        super(null);
    }

    public CharStringConverter(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
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
