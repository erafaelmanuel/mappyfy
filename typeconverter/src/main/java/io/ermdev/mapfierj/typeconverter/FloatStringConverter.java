package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class FloatStringConverter extends TypeConverterAdapter<Float, String> {

    @Override
    public Float convertTo(String o) {
        try {
            return Float.parseFloat(o);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    @Override
    public String convertFrom(Float o) {
        return String.valueOf(o);
    }
}
