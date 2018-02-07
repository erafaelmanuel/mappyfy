package mapfierj.converter;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

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