package test1;

import com.rem.mappyfy.Mapper;
import org.junit.Test;

import java.util.logging.Logger;

public class Test1 {

    Logger logger = Logger.getLogger(Test1.class.getSimpleName());

    @Test
    public void test() {
        Mapper mapper = new Mapper();

        PersonDto dto = new PersonDto();
        mapper.set(new Person("Rafael", 18)).mapTo(dto);



        logger.info(dto.toString());
        logger.info(mapper.set(new Person("Verile", 89)).ignore("name").mapTo(PersonDto.class).toString());
    }
}
