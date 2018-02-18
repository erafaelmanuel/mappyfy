package mapfierj.tc;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

@TypeConverter
public class CharStringConverter extends TypeConverterAdapter<Character, String> {

    @Override
    public String convertTo(Character o) throws TypeException {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Character convertFrom(String o) throws TypeException {
        try {
            return (o.trim().charAt(0));
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
