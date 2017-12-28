package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class BooleanStringConverter extends TypeConverterAdapter<Boolean, String> {

    @Override
    public Object convert(Object o) {
        if(o != null) {
            if(o instanceof Boolean)
                return convertTo((Boolean) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else return null;
        }
        return null;
    }

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
