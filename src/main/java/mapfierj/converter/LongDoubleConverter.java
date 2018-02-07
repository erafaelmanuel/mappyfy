package mapfierj.converter;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

@TypeConverter
public class LongDoubleConverter extends TypeConverterAdapter<Long, Double> {

    @Override
    public Double convertTo(Long o) throws TypeException {
        try {
            return o.doubleValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Long convertFrom(Double o) throws TypeException {
        try {
            return o.longValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
