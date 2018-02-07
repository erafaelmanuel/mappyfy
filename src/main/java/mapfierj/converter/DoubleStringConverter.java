package mapfierj.converter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class DoubleStringConverter extends TypeConverterAdapter<Double, String> {

    @Override
    public String convertTo(Double o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Double convertFrom(String o) throws TypeException {
        try {
            return Double.parseDouble(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}