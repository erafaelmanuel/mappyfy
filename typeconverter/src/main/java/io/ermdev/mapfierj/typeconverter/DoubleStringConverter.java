package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class DoubleStringConverter extends TypeConverterAdapter<Double, String> {

    @Override
    public Object convert(Object o) {
        if(o != null) {
            if(o instanceof Double)
                return convertTo((Double) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else return null;
        }
        return null;
    }

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
