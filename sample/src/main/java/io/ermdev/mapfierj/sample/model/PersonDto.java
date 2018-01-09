package io.ermdev.mapfierj.sample.model;

public class PersonDto {


    public String name;
    public int age;
    public Double height;
    public PetDto pet;
    public Car car;

    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", pet=" + pet +
                ", car=" + car +
                '}';
    }
}
