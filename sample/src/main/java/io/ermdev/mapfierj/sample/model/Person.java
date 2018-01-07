package io.ermdev.mapfierj.sample.model;

import io.ermdev.mapfierj.ConvertTo;
import io.ermdev.mapfierj.FieldName;

public class Person {

    public String name;
    public short age;
    public Short height;

    @FieldName("pet")
    @ConvertTo(value = PetDto.class, scanPackages = "io.ermdev.mapfierj.sample.model")
    public int petId;

    public Person(String name, Short age, Short height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }
}
