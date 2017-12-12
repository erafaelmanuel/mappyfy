package io.ermdev.mapfierj;

public class AnimalDto {

    private String name;
    private Integer size;

    private FoodDto food;

    public AnimalDto(){}

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

    public FoodDto getFood() {
        return food;
    }

    public void setFood(FoodDto food) {
        this.food = food;
    }

    @Override
    public String toString() {
        return "AnimalDto{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", food=" + food +
                '}';
    }
}
