package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class DoubleStringConverter extends TypeConverterAdapter<Double, String> {

    @Override
    public String convertTo(Double o) {
        return String.valueOf(o);
    }

    @Override
    public Double convertFrom(String o) {
        try {
            return Double.parseDouble(o);
        } catch (NumberFormatException e) {
            return 0d;
        }
    }
}
