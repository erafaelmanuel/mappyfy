package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.core.TypeConverter;
import io.ermdev.mapfierj.core.TypeConverterAdapter;

@TypeConverter
public class CharStringConverter extends TypeConverterAdapter<Character, String> {

    @Override
    public Object convert(Object o) {
        if(o != null) {
            if(o instanceof Character)
                return convertTo((Character) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else return null;
        }
        return null;
    }

    @Override
    public String convertTo(Character o) {
        return String.valueOf(o);
    }

    @Override
    public Character convertFrom(String o) {
        try {
            return (o.trim().charAt(0));
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
