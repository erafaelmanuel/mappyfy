package io.ermdev.mapfierj.typeconverter;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class ShortIntegerConverter extends TypeConverterAdapter<Short, Integer> {

    public ShortIntegerConverter() {
        super(null);
    }

    public ShortIntegerConverter(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
        if(o != null) {
            if(o instanceof Short)
                return convertTo((Short) o);
            else if(o instanceof Integer)
                return convertFrom((Integer) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

    @Override
    public Integer convertTo(Short o) throws TypeException {
        try {
            return o.intValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }

    @Override
    public Short convertFrom(Integer o) throws TypeException {
        try {
            return o.shortValue();
        } catch (Exception e) {
            throw new TypeException("Failed to convert");
        }
    }
}
