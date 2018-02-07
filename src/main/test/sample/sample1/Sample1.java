package io.ermdev.mapfierj.sample.sample1;

import io.ermdev.mapfierj.ModelMapper;
import org.junit.Test;

import java.util.logging.Logger;

public class Sample1 {

    Logger logger = Logger.getLogger(Sample1.class.getSimpleName());

    @Test
    public void modelMapper() {
        final int carId = (int) (Math.random() * 8) + 1;

        CarRepository carRepository = new CarRepository();
        CarConverter carConverter = new CarConverter(carRepository);

        ModelMapper mapper = new ModelMapper();
        mapper.getConverter().register(carConverter);

        Person person = new Person("Rafael", 18, carId);
        PersonDto personDto = mapper.set(person)
                .field("name", "fullName")
                .field("carId", "car")
                .convertFieldToType("car", Car.class)
                .getTransaction().mapTo(PersonDto.class);

        logger.info("CAR ID : " + carId);
        logger.info(personDto.toString());
    }
}
