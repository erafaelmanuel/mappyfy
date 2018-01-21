package io.ermdev.mapfierj.sample.sample3;

import io.ermdev.mapfierj.SimpleMapper;
import org.junit.Test;

import java.util.logging.Logger;

public class Sample3 {

    Logger logger = Logger.getLogger(Sample3.class.getSimpleName());

    @Test
    public void simpleMapper() {
        int carId = (int) (Math.random() * 10) + 1;

        SimpleMapper mapper = new SimpleMapper();

        Person person = new Person("Rafael", 18, carId);

        PersonDto personDto = mapper.set(person).mapTo(PersonDto.class);

        logger.info(personDto.toString());
    }
}
