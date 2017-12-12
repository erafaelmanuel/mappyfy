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
        ModelMapper<Animal> mapper = new ModelMapper<>();
        AnimalDto dto = mapper.set(animal).mapTo(AnimalDto.class);

        Assert.assertEquals(animal.getName(), dto.getName());
        Assert.assertEquals(animal.getSize(), dto.getSize());
    }

    @Test
    public void shouldPassOnSimpleMapper() {
        Animal animal = new Animal("Dog", 5);
        SimpleMapper mapper = new SimpleMapper();
        AnimalDto dto = mapper.set(animal).mapTo(AnimalDto.class);

        Assert.assertEquals(animal.getName(), dto.getName());
        Assert.assertEquals(animal.getSize(), dto.getSize());
    }
}
