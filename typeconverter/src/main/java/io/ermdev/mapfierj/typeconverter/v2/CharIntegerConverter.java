package io.ermdev.mapfierj.typeconverter.v2;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeException;
import io.ermdev.mapfierj.TypeConverterAdapter;

@TypeConverter
public class CharIntegerConverter extends TypeConverterAdapter<Character, Integer> {

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
