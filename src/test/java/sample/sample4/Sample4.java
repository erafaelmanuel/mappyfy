package sample.sample4;

import mapfierj.Converter;
import mapfierj.MappingException;
import mapfierj.ModelMapper;
import mapfierj.TypeException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Sample4 {

    Logger logger = Logger.getLogger(Sample4.class.getSimpleName());

    @Test
    public void mappingWithLessAnnotation() {
        ModelMapper mapper = new ModelMapper();

        List<Dog> dogs = new ArrayList<>();

        //Create our dogs
        Dog dog1 = new Dog("Kelvin", 21);
        Dog dog2 = new Dog("Ralen", 22);

        dogs.add(dog1);
        dogs.add(dog2);

        //Create the owner of our dogs
        Person person = new Person("Rafael", dogs);
        
        PetOwner petOwner = mapper.set(person)
                .field("dogs", "pets")
                .getTransaction().mapAllTo(PetOwner.class);

        logger.info(petOwner.toString());
    }

    @Test
    public void dample() throws TypeException {
        SampleConverter converter = new SampleConverter("");
        String num = converter.convert(12);
        System.out.println(num);
    }

    @Test
    public void converterTest() throws MappingException {
        Converter converter = new Converter();
        final int num = 1;
        Character c = converter.convertTo( num, Character.class);
        System.out.println(c);
    }
}
