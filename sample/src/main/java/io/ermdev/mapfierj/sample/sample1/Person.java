package io.ermdev.mapfierj.sample.sample1;

import io.ermdev.mapfierj.ConvertTo;
import io.ermdev.mapfierj.FieldName;

public class Person {

    @FieldName("fullName")
    String name;
    int age;

    @FieldName("car")
    @ConvertTo(value = Car.class, converter = IntegerCarConverter.class)
    int carId;

    public Person(String name, int age, int carId) {
        this.name = name;
        this.age = age;
        this.carId = carId;
    }
}
