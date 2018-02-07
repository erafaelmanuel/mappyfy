package mapfierj.converter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

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
