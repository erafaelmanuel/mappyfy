package io.ermdev.mapfierj.sample.sample1;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class IntegerCarConverter extends TypeConverterAdapter<Integer, Car>{

    private CarRepository carRepository;

    public IntegerCarConverter(Object obj) {
        super(obj);
        carRepository = new CarRepository();
    }

    public IntegerCarConverter(Object obj, CarRepository carRepository) {
        this(obj);
        this.carRepository = carRepository;
    }

    @Override
    public Object convert() throws TypeException {
        if(getObject() instanceof Integer)
            return convertTo((Integer) getObject());
        else if(getObject() instanceof Car)
            return convertFrom((Car) getObject());
        else
            return null;
    }

    @Override
    public Car convertTo(Integer o) throws TypeException {
        return carRepository.getById(o);
    }

    @Override
    public Integer convertFrom(Car o) throws TypeException {
        return o.getId();
    }
}
