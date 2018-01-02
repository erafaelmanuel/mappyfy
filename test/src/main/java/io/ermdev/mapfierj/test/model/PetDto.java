package io.ermdev.mapfierj.test.model;

public class PetDto {

    String name;
    int age;

    public PetDto(){}

    public PetDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "PetDto{" +
                "name=" + name +
                ", age=" + age +
                '}';
    }
}
