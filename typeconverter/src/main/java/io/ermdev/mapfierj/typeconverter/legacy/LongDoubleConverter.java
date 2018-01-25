package io.ermdev.mapfierj.typeconverter.legacy;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.legacy.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class LongDoubleConverter extends TypeConverterAdapter<Long, Double> {

    public LongDoubleConverter() {
        super(null);
    }

    public LongDoubleConverter(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
        if(o != null) {
            if(o instanceof Long)
                return convertTo((Long) o);
            else if(o instanceof Double)
                return convertFrom((Double) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public Double convertTo(Long o) throws TypeException {
        try {
            return o.doubleValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Long convertFrom(Double o) throws TypeException {
        try {
            return o.longValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
