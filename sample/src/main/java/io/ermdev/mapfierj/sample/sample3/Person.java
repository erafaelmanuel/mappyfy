package io.ermdev.mapfierj.sample.sample3;

import io.ermdev.mapfierj.Type;
import io.ermdev.mapfierj.v2.ConvertTo;

public class Person {

    String name;
    int age;
    @ConvertTo(value = Car.class, type = Type.SINGLE)
    int carId;

    public Person(String name, int age, int carId) {
        this.name = name;
        this.age = age;
        this.carId = carId;
    }
}
