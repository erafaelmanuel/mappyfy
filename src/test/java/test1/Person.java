package test1;

import mapfierj.Ignore;

public class Person {

    @Ignore
    String name;

    int age;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
