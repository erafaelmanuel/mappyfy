package test1;

import mapfierj.Mapper;
import org.junit.Test;

import java.util.logging.Logger;

public class Test1 {

    Logger logger = Logger.getLogger(Test1.class.getSimpleName());

    @Test
    public void test() {
        Mapper mapper = new Mapper();

        PersonDto dto = mapper.set(new Person("Rafael", 18)).mapTo(PersonDto.class);

        logger.info(dto.toString());
    }
}
