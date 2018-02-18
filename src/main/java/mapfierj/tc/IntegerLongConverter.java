package mapfierj.tc;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

@TypeConverter
public class IntegerLongConverter extends TypeConverterAdapter<Integer, Long> {

    @Override
    public Long convertTo(Integer o) throws TypeException {
        try {
            return o.longValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Integer convertFrom(Long o) throws TypeException {
        try {
            return o.intValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
