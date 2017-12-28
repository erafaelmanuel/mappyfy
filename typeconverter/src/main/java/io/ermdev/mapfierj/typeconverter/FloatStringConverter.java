package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class FloatStringConverter extends TypeConverterAdapter<Float, String> {

    @Override
    public Object convert(Object o) {
        if(o != null) {
            if(o instanceof Float)
                return convertTo((Float) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else return null;
        }
        return null;
    }

    @Override
    public String convertTo(Float o) {
        return String.valueOf(o);
    }

    @Override
    public Float convertFrom(String o) {
        try {
            return Float.parseFloat(o);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }
}
