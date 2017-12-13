package io.ermdev.mapfierj;

import java.util.ArrayList;
import java.util.List;

public class AnimalDto {

    private String name;
    private Integer size;

    private List<FoodDto> foods = new ArrayList<>();

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

    @Override
    public String toString() {
        return "AnimalDto{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", foods=" + foods +
                '}';
    }
}
