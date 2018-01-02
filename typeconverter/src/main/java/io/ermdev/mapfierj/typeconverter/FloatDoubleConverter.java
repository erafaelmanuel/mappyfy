package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class FloatDoubleConverter extends TypeConverterAdapter<Float, Double> {

    @Override
    public Object convert(Object o) throws TypeException {
        if(o != null) {
            if(o instanceof Float)
                return convertTo((Float) o);
            else if(o instanceof Double)
                return convertFrom((Double) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public Double convertTo(Float o) throws TypeException{
        try {
            return o.doubleValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Float convertFrom(Double o) throws TypeException {
        try {
            return o.floatValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
