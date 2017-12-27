package io.ermdev.mapfierj.test;

import java.util.HashSet;
import java.util.Set;

public class Animal{

    private String name;

    private int size;

    private Set<Food> foods = new HashSet<>();

    public Animal(){}

    public Animal(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public void setFoods(Set<Food> foods) {
        this.foods = foods;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", foods=" + foods +
                '}';
    }
}