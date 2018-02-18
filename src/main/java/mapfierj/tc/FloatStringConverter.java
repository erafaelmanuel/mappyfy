package mapfierj.tc;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

@TypeConverter
public class FloatStringConverter extends TypeConverterAdapter<Float, String> {

    @Override
    public String convertTo(Float o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Float convertFrom(String o) throws TypeException {
        try {
            return Float.parseFloat(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
