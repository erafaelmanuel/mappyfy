package io.ermdev.mapfierj.sample.model;

public class PersonDto {

    public String name;
    public int age;
    public String height;
    public PetDto pet;

    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", pet=" + pet +
                '}';
    }
}
