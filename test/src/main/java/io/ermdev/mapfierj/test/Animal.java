package io.ermdev.mapfierj.test;

import io.ermdev.mapfierj.core.FieldName;
import io.ermdev.mapfierj.core.Ignore;

import java.util.HashSet;
import java.util.Set;

public class Animal{

    @Ignore
    private String title;

    private int width;

    private Set<Food> foods = new HashSet<>();

    public Animal(){}

    public Animal(String name, Integer size) {
        this.title = name;
        this.width = size;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public Integer getSize() {
        return width;
    }

    public void setSize(Integer size) {
        this.width = size;
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
                ", size=" + width +
                ", foods=" + foods +
                '}';
    }
}
