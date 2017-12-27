package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class CharStringConverter extends TypeConverterAdapter<Character, String> {

    @Override
    public Character convertTo(String o) {
        try {
            return (o.trim().charAt(0));
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public String convertFrom(Character o) {
        return String.valueOf(o);
    }
}
