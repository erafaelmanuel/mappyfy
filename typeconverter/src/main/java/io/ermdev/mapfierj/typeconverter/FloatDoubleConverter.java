package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeException;
import io.ermdev.mapfierj.TypeConverterAdapter;

@TypeConverter
public class FloatDoubleConverter extends TypeConverterAdapter<Float, Double> {

    @Override
    public Double convertTo(Float o) throws TypeException {
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