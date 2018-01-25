package io.ermdev.mapfierj.typeconverter.legacy;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.legacy.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class FloatDoubleConverter extends TypeConverterAdapter<Float, Double> {

    public FloatDoubleConverter() {
        super(null);
    }

    public FloatDoubleConverter(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
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
