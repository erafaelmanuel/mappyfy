package io.ermdev.mapfierj.typeconverter.v2;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeException;
import io.ermdev.mapfierj.v2.TypeConverterAdapter;

@TypeConverter
public class BooleanStringConverter extends TypeConverterAdapter<Boolean, String> {

    @Override
    public String convertTo(Boolean o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Boolean convertFrom(String o) throws TypeException {
        try {
            return Boolean.getBoolean(o);
        } catch (NullPointerException e) {
            throw new TypeException("Failed to convert");
        }
    }
}
