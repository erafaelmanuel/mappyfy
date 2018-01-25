package io.ermdev.mapfierj.typeconverter.legacy;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.legacy.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class ByteIntegerConverter extends TypeConverterAdapter<Byte, Integer> {

    public ByteIntegerConverter() {
        super(null);
    }

    public ByteIntegerConverter(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
        if(o != null) {
            if(o instanceof Byte)
                return convertTo((Byte) o);
            else if(o instanceof Integer)
                return convertFrom((Integer) o);
            else
                throw new TypeException("Invalid Type");
        }
        throw new TypeException("You can't convert a null object");
    }

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
