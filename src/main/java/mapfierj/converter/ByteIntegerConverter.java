package mapfierj.converter;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

@TypeConverter
public class ByteIntegerConverter extends TypeConverterAdapter<Byte, Integer> {

    @Override
    public Integer convertTo(Byte o) throws TypeException {
        try {
            return o.intValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Byte convertFrom(Integer o) throws TypeException {
        try {
            return o.byteValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
