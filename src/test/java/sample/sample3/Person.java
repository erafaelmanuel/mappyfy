package sample.sample3;

import mapfierj.FieldName;
import mapfierj.MapTo;
import mapfierj.Type;

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
