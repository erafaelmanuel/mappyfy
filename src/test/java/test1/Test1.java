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
        mapper.from(new Person("Rafael", 18)).to(dto);

        logger.info(dto.toString());
        logger.info(mapper.from(new Person("Verile", 89)).toInstanceOf(Doctor.class).toString());
    }


    @Test
    public void test2() {
        Mapper mapper = new Mapper();

//        Person[] persons = {new Person("Rafael", 18), new Person("Albert", 15)};
        //mapper.set(persons).map(PersonDto.class, System.out::println);

        List<Person> persons = Arrays.asList(new Person("Rafael", 18), new Person("Albert", 15));
//        mapper.set(persons).map(PersonDto.class, System.out::println);
        System.out.println(mapper.from(persons).toListOf(PersonDto.class));

        for (PersonDto personDto : mapper.from(persons).toArrayOf(PersonDto.class)) {
            System.out.println(personDto);
        }

        List<Person> personList = Arrays.asList(new Person("Rafael", 18), new Person("Albert", 15));

        mapper.from(persons).bind("name", "fullname").map(Doctor.class, System.out::println);

//        for (Integer s: mapper.from(personList).only("age").toArrayOf(Integer.class))
//            System.out.println(s);

        System.out.println(mapper.from(personList).only("age").toListOf(Integer.class));
    }

    @Test
    public void test3() {
        Mapper mapper = new Mapper();
        Bar bar = new Bar();
        bar.name = Arrays.asList("rafael", "manuel");

        Foo foo = mapper.from(bar).toInstanceOf(Foo.class);

        System.out.println(foo);
    }
}
