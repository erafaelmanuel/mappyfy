package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class IntegerStringConverter extends TypeConverterAdapter<Integer, String> {

    @Override
    public String convertTo(Integer o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Integer convertFrom(String o) throws TypeException {
        try {
            return Integer.parseInt(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
