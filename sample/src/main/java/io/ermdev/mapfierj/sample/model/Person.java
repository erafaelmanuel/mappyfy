package io.ermdev.mapfierj.sample.model;

import io.ermdev.mapfierj.ConvertTo;
import io.ermdev.mapfierj.FieldName;
import io.ermdev.mapfierj.sample.cases.typeconverter.IntegerCarConverter;

public class Person {

    public String name;

    @ConvertTo(value = Integer.class, scanPackages = "io.ermdev.mapfierj.sample.model")
    public short age;

    @ConvertTo(value = Double.class, scanPackages = "io.ermdev.mapfierj.sample.model")
    public Short height;

    @FieldName("pet")
    @ConvertTo(value = PetDto.class, converter = GG.class)
    public int petId;

    @FieldName("car")
    @ConvertTo(value = Car.class, converter = IntegerCarConverter.class)
    public int carId;

    public Person(String name, short age, Short height, int carId) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.carId = carId;
    }
}
