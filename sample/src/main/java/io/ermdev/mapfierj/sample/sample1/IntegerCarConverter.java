package io.ermdev.mapfierj.sample.sample1;

import io.ermdev.mapfierj.TypeConverter;
import io.ermdev.mapfierj.TypeConverterAdapter;
import io.ermdev.mapfierj.TypeException;

@TypeConverter
public class IntegerCarConverter extends TypeConverterAdapter<Integer, Car> {

    private CarRepository carRepository;

    public IntegerCarConverter() {
    }

    public IntegerCarConverter(CarRepository carRepository) {
        this.carRepository = carRepository;
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
