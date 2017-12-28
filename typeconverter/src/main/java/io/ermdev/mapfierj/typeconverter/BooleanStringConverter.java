package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class BooleanStringConverter extends TypeConverterAdapter<Boolean, String> {

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
