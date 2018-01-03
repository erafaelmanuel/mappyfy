package io.ermdev.mapfierj.sample.cases.core;

import io.ermdev.mapfierj.ModelMapper;
import io.ermdev.mapfierj.sample.model.Person;
import io.ermdev.mapfierj.sample.model.PersonDto;
import io.ermdev.mapfierj.sample.model.PetDto;
import org.junit.Test;

public class Sample {

    @Test
    public void test() {
        ModelMapper mapper = new ModelMapper("io.ermdev.mapfierj.sample.model");
        PersonDto person = mapper.set(new Person("Rafael", (short) 22, (short) 5))
                .field("petId", "pet")
                .exclude("pet")
                .exclude("age")
                .convertFieldToType("pet", PetDto.class)
                .convertFieldToType("height", Double.class)
                .convertFieldToType("age", Integer.class)
                .getTransaction().mapTo(PersonDto.class);
        System.out.println(person);
    }
}
