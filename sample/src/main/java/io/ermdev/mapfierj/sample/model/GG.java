package io.ermdev.mapfierj.sample.model;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class GG extends TypeConverterAdapter<Integer, PetDto>{

    public GG(Object obj) {
        super(obj);
    }

    @Override
    public Object convert() throws TypeException {
        if(o instanceof Integer)
            return convertTo((Integer) o);
        else
            return convertFrom((PetDto) o);
    }

    @Override
    public PetDto convertTo(Integer o) throws TypeException {
        return new PetDto("Rafael", 22);
    }

    @Override
    public Integer convertFrom(PetDto o) throws TypeException {
        return 22;
    }
}
