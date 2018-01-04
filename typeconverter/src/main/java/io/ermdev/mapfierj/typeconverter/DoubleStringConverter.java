package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class DoubleStringConverter extends TypeConverterAdapter<Double, String> {

    public DoubleStringConverter() {
        super(null);
    }

    public DoubleStringConverter(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
        if(o != null) {
            if(o instanceof Double)
                return convertTo((Double) o);
            else if(o instanceof String)
                return convertFrom((String) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public String convertTo(Double o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Double convertFrom(String o) throws TypeException {
        try {
            return Double.parseDouble(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
