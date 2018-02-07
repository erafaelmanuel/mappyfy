package mapfierj.converter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class ShortIntegerConverter extends TypeConverterAdapter<Short, Integer> {

    @Override
    public Integer convertTo(Short o) throws TypeException {
        try {
            return o.intValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Short convertFrom(Integer o) throws TypeException {
        try {
            return o.shortValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
