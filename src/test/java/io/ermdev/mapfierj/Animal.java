package io.ermdev.mapfierj;

public class Animal{

    private String name;
    private Integer size;

    @MapTo(FoodDto.class)
    private Food food;

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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
