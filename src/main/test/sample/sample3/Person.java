package io.ermdev.mapfierj.sample.sample3;


import io.ermdev.mapfierj.FieldName;
import io.ermdev.mapfierj.Type;
import io.ermdev.mapfierj.MapTo;

import java.util.List;

public class Person {

    String name;

    @FieldName("pets")
    @MapTo(value = Pet.class, type = Type.LIST)
    List<Dog> dogs;

    public Person(String name, List<Dog> dogs) {
        this.name = name;
        this.dogs = dogs;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", dogs=" + dogs +
                '}';
    }
}
