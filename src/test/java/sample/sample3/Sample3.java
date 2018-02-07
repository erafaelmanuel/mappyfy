package sample.sample3;

import mapfierj.SimpleMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Sample3 {

    Logger logger = Logger.getLogger(Sample3.class.getSimpleName());

    @Test
    public void simpleMapper() {
        SimpleMapper mapper = new SimpleMapper();

        List<Dog> dogs = new ArrayList<>();

        //Create our dogs
        Dog dog1 = new Dog("Kelvin", 21);
        Dog dog2 = new Dog("Ralen", 22);

        dogs.add(dog1);
        dogs.add(dog2);

        //Create the owner of our dogs
        Person person = new Person("Rafael", dogs);

        PetOwner petOwner = mapper.set(person).mapTo(PetOwner.class);

        logger.info(petOwner.toString());
    }
}
