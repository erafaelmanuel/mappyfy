package io.ermdev.mapfierj;

import org.junit.Assert;
import org.junit.Test;

public class MapperTest {

    @Test
    public void shouldPass() {
        Assert.assertEquals(1+1, 2);
    }

    @Test
    public void shouldPassOnModelMapper() {
        Animal animal = new Animal("Dog", 5);
        animal.setFood(new Food("Bone"));
        ModelMapper<Animal> mapper = new ModelMapper<>();
        AnimalDto dto = mapper.set(animal).mapTo(AnimalDto.class);

        Assert.assertEquals(animal.getName(), dto.getName());
        Assert.assertEquals(animal.getSize(), dto.getSize());

        System.out.println(dto);
    }

    @Test
    public void shouldPassOnSimpleMapper() {
        Animal animal = new Animal("Dog", 5);
        //animal.setFood(new Food("Chicken"));
        SimpleMapper mapper = new SimpleMapper();
        AnimalDto dto = mapper.set(animal).mapTo(AnimalDto.class);

        Assert.assertEquals(animal.getName(), dto.getName());
        Assert.assertEquals(animal.getSize(), dto.getSize());

        System.out.println(dto);
    }
}
