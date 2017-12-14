package io.ermdev.mapfierj;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MapperTest {

    @Test
    public void shouldPass() {
        Assert.assertEquals(1+1, 2);
    }

    @Test
    public void shouldPassOnModelMapper() {
        Animal animal = new Animal("Dog", 5);
        //animal.setFood(new Food("Bone"));
        animal.setFoods(null);
        ModelMapper<Animal> mapper = new ModelMapper<>();
        AnimalDto dto = mapper.set(animal).mapTo(AnimalDto.class);

        Assert.assertEquals(animal.getName(), dto.getName());
        Assert.assertEquals(animal.getSize(), dto.getSize());

        System.out.println(dto);
    }

    @Test
    public void shouldPassOnSimpleMapper() {
        Animal animal = new Animal("Dog", 5);
        Set<Food> foods = new HashSet<>();
        foods.add(new Food("Tae"));
        foods.add(new Food("Milf"));
        foods.add(new Food("Fuck"));

        animal.setFoods(foods);
        SimpleMapper mapper = new SimpleMapper();
        AnimalDto dto = mapper.set(animal).mapTo(AnimalDto.class);

        Assert.assertEquals(animal.getName(), dto.getName());
        Assert.assertEquals(animal.getSize(), dto.getSize());

        System.out.println(dto);
    }
}
