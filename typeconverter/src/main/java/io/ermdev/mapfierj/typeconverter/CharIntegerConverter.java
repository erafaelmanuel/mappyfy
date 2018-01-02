package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class CharIntegerConverter extends TypeConverterAdapter<Character, Integer> {

    @Override
    public Object convert(Object o) throws TypeException {
        if(o != null) {
            if(o instanceof Character)
                return convertTo((Character) o);
            else if(o instanceof Integer)
                return convertFrom((Integer) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public Integer convertTo(Character o) throws TypeException {
        try {
            return (int) o;
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Character convertFrom(Integer o) throws TypeException {
        try {
            return (char) (o + '0');
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }

    }
}
