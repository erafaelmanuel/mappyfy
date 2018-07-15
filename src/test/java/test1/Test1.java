package test1;

import com.rem.mappyfy.Mapper;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Test1 {

    Logger logger = Logger.getLogger(Test1.class.getSimpleName());

    @Test
    public void test() {
        Mapper mapper = new Mapper();

        PersonDto dto = new PersonDto();
        mapper.set(new Person("Rafael", 18)).mapTo(dto);

        logger.info(dto.toString());
        logger.info(mapper.set(new Person("Verile", 89)).mapTo(Doctor.class).toString());
    }


    @Test
    public void test2() {
        Mapper mapper = new Mapper();

        Person[] persons = {new Person("Rafael", 18), new Person("Albert", 15)};
        mapper.set(persons).map(PersonDto.class, System.out::println);

        for (PersonDto personDto : mapper.set(persons).toArray(PersonDto.class)) {
            System.out.println(personDto);
        }

        List<Person> personList = Arrays.asList(new Person("Rafael", 18), new Person("Albert", 15));

        mapper.set(persons).bind("name", "fullname").map(Doctor.class, System.out::println);
    }
}
