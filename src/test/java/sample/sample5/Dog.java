package sample.sample5;

import mapfierj.Field;

public class Dog {

    @Field(name = "petName")
    String name;

    @Field(name = "petSize")
    Integer size;

    public Dog(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
