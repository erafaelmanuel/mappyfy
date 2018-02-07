package sample.sample2;

import mapfierj.TypeConverter;
import mapfierj.TypeConverterAdapter;
import mapfierj.TypeException;

@TypeConverter
public class IntegerCarConverter extends TypeConverterAdapter<Integer, Car> {

    private CarRepository carRepository;

    public IntegerCarConverter() {
        carRepository = new CarRepository();
    }

    public IntegerCarConverter(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car convertTo(Integer o) throws TypeException {
        try {
            return carRepository.getById(o);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Integer convertFrom(Car o) throws TypeException {
        return o.getId();
    }
}
