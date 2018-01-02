package io.ermdev.mapfierj.test.cases.core;

import io.ermdev.mapfierj.core.ModelMapper;
import io.ermdev.mapfierj.test.model.Person;
import io.ermdev.mapfierj.test.model.PersonDto;
import org.junit.Test;

public class Sample {

    @Test
    public void test() {
        ModelMapper mapper = new ModelMapper();
        PersonDto person = mapper.set(new Person("Rafael", 22, 55))
                .convertFieldToType("height", Double.class)
                .getTransaction().mapAllTo(PersonDto.class);
        System.out.println(person);
    }
}
