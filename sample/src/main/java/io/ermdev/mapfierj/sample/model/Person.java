package io.ermdev.mapfierj.sample.model;

import io.ermdev.mapfierj.ConvertTo;

public class Person {

    public String name;
    public short age;
    public Short height;

    @ConvertTo(GG.class)
    public int petId;

    public Person(String name, Short age, Short height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }
}
