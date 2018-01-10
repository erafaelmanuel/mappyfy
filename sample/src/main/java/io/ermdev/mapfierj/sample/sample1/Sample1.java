package io.ermdev.mapfierj.sample.sample1;

import io.ermdev.mapfierj.ModelMapper;
import io.ermdev.mapfierj.SimpleMapper;
import org.junit.Test;

import java.util.logging.Logger;

public class Sample1 {

    Logger logger = Logger.getLogger(Sample1.class.getSimpleName());

    @Test
    public void simpleMapper() {
        SimpleMapper mapper = new SimpleMapper();

        final int carId = (int) (Math.random() * 10) + 1;
        Person person = new Person("Rafael", 18, carId);

        PersonDto personDto = mapper.set(person).mapTo(PersonDto.class);

        logger.info(personDto.toString());
    }

    @Test
    public void modelMapper() {
        final String PACKAGE_NAME = getClass().getPackage().toString();
        int carId = (int) (Math.random() * 10) + 1;

        ModelMapper mapper = new ModelMapper(PACKAGE_NAME);
        CarRepository repository = new CarRepository();
        Person person = new Person("Rafael", 18, carId);

        PersonDto personDto = mapper.set(person)
                .field("name", "fullName")
                .field("carId", "car")
                .converter("car", IntegerCarConverter.class)
                .getTransaction().mapTo(PersonDto.class);

        logger.info("CAR ID : " + carId);
        logger.info("Package : " + PACKAGE_NAME);
        logger.info(personDto.toString());
    }
}
