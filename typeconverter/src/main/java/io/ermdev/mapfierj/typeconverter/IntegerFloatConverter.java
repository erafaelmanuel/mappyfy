package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class IntegerFloatConverter extends TypeConverterAdapter<Integer, Float> {

    @Override
    public Float convertTo(Integer o) throws TypeException {
        try {
            return o.floatValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Integer convertFrom(Float o) throws TypeException {
        try {
            return o.intValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
