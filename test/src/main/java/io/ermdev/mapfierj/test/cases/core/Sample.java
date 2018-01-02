package io.ermdev.mapfierj.test.cases.core;

import io.ermdev.mapfierj.ModelMapper;
import io.ermdev.mapfierj.test.model.Person;
import io.ermdev.mapfierj.test.model.PersonDto;
import io.ermdev.mapfierj.test.model.PetDto;
import org.junit.Test;

public class Sample {

    @Test
    public void test() {
        ModelMapper mapper = new ModelMapper("io.ermdev.mapfierj.test.model");
        PersonDto person = mapper.set(new Person("Rafael", 22, 55))
                .field("petId", "pet")
                //.converter("pet", new GG())
                .convertFieldToType("pet", PetDto.class)
                .getTransaction().mapTo(PersonDto.class);
        System.out.println(person);
    }
}
