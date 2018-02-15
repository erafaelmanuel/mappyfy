package sample.sample5;

import mapfierj.FieldName;

public class Dog {

    @FieldName("petName")
    String name;

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
