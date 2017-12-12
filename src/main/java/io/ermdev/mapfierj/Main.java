package io.ermdev.mapfierj;

public class Main {

    public static void main(String args[]) throws InstantiationException, IllegalAccessException {
        ModelMapper mapper = new ModelMapper();
        AnimalDto animalDto = new Converter<>(new Animal()).mapTo(AnimalDto.class);

        ModelMapper<Animal, AnimalDto> animalMapper = new ModelMapper<>();
        AnimalDto animalDto1 = animalMapper.set(new Animal()).map();
    }
}
