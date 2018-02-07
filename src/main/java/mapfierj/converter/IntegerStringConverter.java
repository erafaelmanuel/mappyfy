package mapfierj.converter;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

@TypeConverter
public class IntegerStringConverter extends TypeConverterAdapter<Integer, String> {

    @Override
    public String convertTo(Integer o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Integer convertFrom(String o) throws TypeException {
        try {
            return Integer.parseInt(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
