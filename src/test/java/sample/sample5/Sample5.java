package sample.sample5;

import mapfierj.Converter;
import mapfierj.MappingException;
import mapfierj.TypeException;
import mapfierj.xyz.Mapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Sample5 {

    Logger logger = Logger.getLogger(Sample5.class.getSimpleName());

    @Test
    public void mappingWithLessAnnotation() {
        Mapper mapper = new Mapper();

        List<Dog> dogs = new ArrayList<>();


        //Create our dogs
        Dog dog1 = new Dog("Kelvin", 21);
        Dog dog2 = new Dog("Ralen", 22);

        dogs.add(dog1);
        dogs.add(dog2);

        Pet pet[] = mapper.set(dogs)
                .transact().mapToArray(Pet.class);

        for(Pet p : pet) {
            System.out.println(p);
        }
        System.out.println(mapper.set(dog1).transact().mapTo(Pet.class));

        //Create the owner of our dogs
        Person person = new Person("Rafael", dogs);

        PetOwner petOwner = mapper.set(person).field("dog", "pet").mapTo(PetOwner.class);

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
        Character c = converter.convertTo(num, Character.class);
        System.out.println(c);
    }

    @Test
    public void wannabe() {
        Mapper mapper = new Mapper();
        SuperMan superMan = new SuperMan();
        superMan.name = "Rafael";
        superMan.age = 15;
        superMan.enemy = new String[]{"Dog", "Cat", "Pink"};

        Set<BatMan> batMan = mapper.set(superMan)
                .field("name", "fullname")
                .convertField("age", Long.class)
                .transact()
                .mapToSet(BatMan.class);

        System.out.println(batMan);
    }

    @Test
    public void thinking() throws MappingException {
        mapfierj.xyz.Converter converter = new mapfierj.xyz.Converter();
        short num = converter.set("5").convertTo(Short.class);
        System.out.println(num + 1);
    }
}
