package io.ermdev.mapfierj.test;

import io.ermdev.mapfierj.core.ModelMapper;
import io.ermdev.mapfierj.core.SimpleMapper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MapperTest {

    @Test
    public void shouldPass() {
        int c = 1;
        Assert.assertEquals(++c, 2);
    }

    @Test
    public void shouldPassOnModelMapper() {

        Animal animal = new Animal("Dog", 5);

        Set<Food> foods = new HashSet<>();
        foods.add(new Food("Tae"));
        foods.add(new Food("Milf"));
        foods.add(new Food("Fuck"));
        animal.setFoods(foods);

        Animal animal1 = new Animal("Dog", 5);

        Set<Animal> animals = new HashSet<>();
        animals.add(animal);
        animals.add(animal1);

        SimpleMapper mapper = new SimpleMapper();
        AnimalDto dto = mapper.set(animal).mapAllTo(AnimalDto.class);

//        Assert.assertEquals(animal.getName(), dto.getName());
//        Assert.assertEquals(animal.getSize(), dto.getSize());

        System.out.println(mapper.set(animals).mapToList(AnimalDto.class));
        System.out.println(mapper.set(animal).mapAllTo(AnimalDto.class));
    }

    @Test
    public void shouldPassOnSimpleMapper() {
        Animal animal = new Animal("Dog", 5);
        Set<Food> foods = new HashSet<>();
        foods.add(new Food("Tae"));
        foods.add(new Food("Milf"));
        foods.add(new Food("Fuck"));

        //animal.setFoods(foods);

//        SimpleMapper mapper = new SimpleMapper();
//        AnimalDto dto = mapper.set(animal).mapAllTo(AnimalDto.class);

        ModelMapper mapper = new ModelMapper();
        AnimalDto dto = mapper.set(animal)
                .field("title", "name")
                .field("width", "size")
                .getTransaction()
                .mapAllTo(AnimalDto.class);



//        Assert.assertEquals(animal.getName(), dto.getName());
//        Assert.assertEquals(animal.getSize(), dto.getSize());

        System.out.println(dto);
    }
}
