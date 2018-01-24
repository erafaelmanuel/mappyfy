package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class BooleanStringConverter extends TypeConverterAdapter<Boolean, String> {

    public BooleanStringConverter() {
        super(null);
    }

    public BooleanStringConverter(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
        if (o != null) {
            if (o instanceof Boolean) {
                return convertTo((Boolean) o);
            } else if (o instanceof String) {
                return convertFrom((String) o);
            } else {
                throw new TypeException("Invalid Type");
            }
        } else {
            throw new TypeException("You can't convert a null object");
        }
    }

    @Override
    public String convertTo(Boolean o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Boolean convertFrom(String o) throws TypeException {
        try {
            return Boolean.getBoolean(o);
        } catch (NullPointerException e) {
            throw new TypeException("Failed to convert");
        }
    }
}
