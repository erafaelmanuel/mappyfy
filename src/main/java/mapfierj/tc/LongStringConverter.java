package mapfierj.tc;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

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
