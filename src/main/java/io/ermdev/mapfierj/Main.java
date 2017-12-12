package io.ermdev.mapfierj;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class Main {

    public static void main(String args[]) throws InstantiationException, IllegalAccessException {
        ModelMapper mapper = new ModelMapper();
        AnimalDto animalDto = (AnimalDto) mapper.set(new Animal()).mapTo(AnimalDto.class);

        ModelMapper<Animal, AnimalDto> animalMapper = new ModelMapper<>();
        AnimalDto animalDto1 = animalMapper.set(new Animal()).map();


    }

    public void init() {
        ModelMapper mapper = new ModelMapper();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ModelMapper>> violations = validator.validate(mapper);

        for (ConstraintViolation<ModelMapper> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}
