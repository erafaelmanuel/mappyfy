package mapfierj.converter;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

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
