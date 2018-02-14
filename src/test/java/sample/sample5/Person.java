package sample.sample5;

import java.util.List;

public class Person {

    String name;
    List<Dog> dog;

    public Person(String name, List<Dog> dog) {
        this.name = name;
        this.dog = dog;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", dog=" + dog +
                '}';
    }
}
