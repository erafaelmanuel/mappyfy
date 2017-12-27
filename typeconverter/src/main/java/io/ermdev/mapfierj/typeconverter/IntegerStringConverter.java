package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class IntegerStringConverter extends TypeConverterAdapter<Integer, String> {

    @Override
    public Integer convertTo(String o) {
        try {
            return Integer.parseInt(o);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String convertFrom(Integer o) {
        return String.valueOf(o);
    }
}
