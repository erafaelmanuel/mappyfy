package sample.sample5;


import java.util.List;

public class Person {

    String name;
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
