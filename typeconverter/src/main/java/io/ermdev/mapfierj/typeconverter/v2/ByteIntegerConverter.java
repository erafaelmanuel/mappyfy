package io.ermdev.mapfierj.typeconverter.v2;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeException;
import io.ermdev.mapfierj.TypeConverterAdapter;

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
