package mapfierj.converter;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

@TypeConverter
public class BooleanStringConverter extends TypeConverterAdapter<Boolean, String> {

    @Override
    public String convertTo(Boolean o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Boolean convertFrom(String o) throws TypeException {
        try {
            return Boolean.getBoolean(o);
        } catch (NullPointerException e) {
            throw new TypeException("Failed to convert");
        }
    }
}
