package io.ermdev.mapfierj.test.model;

public class PersonDto {

    public String name;
    public int age;
    public double height;

    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height='" + height + '\'' +
                '}';
    }
}
