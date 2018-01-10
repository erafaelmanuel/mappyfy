package io.ermdev.mapfierj.sample.sample1;

public class PersonDto {

    String fullName;
    int age;
    Car car;

    @Override
    public String toString() {
        return "PersonDto{" +
                "fullName='" + fullName + '\'' +
                ", age=" + age +
                ", car=" + car +
                '}';
    }
}
