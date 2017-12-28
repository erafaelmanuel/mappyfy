package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class IntegerStringConverter extends TypeConverterAdapter<Integer, String> {

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
