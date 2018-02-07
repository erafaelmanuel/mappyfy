package mapfierj.converter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class LongStringConverter extends TypeConverterAdapter<Long, String> {

    @Override
    public String convertTo(Long o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Long convertFrom(String o) throws TypeException {
        try {
            return Long.parseLong(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
