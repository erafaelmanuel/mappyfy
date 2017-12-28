package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class IntegerStringConverter extends TypeConverterAdapter<Integer, String> {

    @Override
    public Object convert(Object o) {
        if(o != null) {
            if(o instanceof Integer)
                return convertTo((Integer) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else return null;
        }
        return null;
    }

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
