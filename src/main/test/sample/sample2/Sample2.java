package io.ermdev.mapfierj.sample.sample2;

import io.ermdev.mapfierj.SimpleMapper;
import org.junit.Test;

import java.util.Map;
import java.util.logging.Logger;

public class Sample2 {

    Logger logger = Logger.getLogger(Sample2.class.getSimpleName());

    @Test
    public void simpleMapper() {
        int carId = (int) (Math.random() * 10) + 1;

        SimpleMapper mapper = new SimpleMapper();

        Person person = new Person("Rafael", 18, carId);

        PersonDto personDto = mapper.set(person).mapTo(PersonDto.class);

        logger.info(personDto.toString());
    }
}
