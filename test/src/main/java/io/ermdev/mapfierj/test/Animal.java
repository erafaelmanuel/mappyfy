package io.ermdev.mapfierj.test;

import io.ermdev.mapfierj.core.FieldName;

import java.util.HashSet;
import java.util.Set;

public class Animal{

    @FieldName("name")
    private String title;

    private int size;

    private Set<Food> foods = new HashSet<>();

    public Animal(){}

    public Animal(String name, Integer size) {
        this.title = name;
        this.size = size;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
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
                "name='" + title + '\'' +
                ", size=" + size +
                ", foods=" + foods +
                '}';
    }
}
