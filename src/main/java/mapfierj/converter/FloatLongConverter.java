package mapfierj.converter;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

@TypeConverter
public class FloatLongConverter extends TypeConverterAdapter<Float, Long> {

    @Override
    public Long convertTo(Float o) throws TypeException {
        try {
            return o.longValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Float convertFrom(Long o) throws TypeException {
        try {
            return o.floatValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}