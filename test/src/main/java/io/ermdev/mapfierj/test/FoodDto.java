package io.ermdev.mapfierj.test;

public class FoodDto {

    private String name;

    public FoodDto() {
    }

    public FoodDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FoodDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
