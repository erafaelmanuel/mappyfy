package io.ermdev.mapfierj;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnimalDto {

    private String name;
    private Integer size;

    //private Set<FoodDto> foods = new HashSet<>();

    private Food food;

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

//    @Override
//    public String toString() {
//        return "AnimalDto{" +
//                "name='" + name + '\'' +
//                ", size=" + size +
//                ", foods=" + foods +
//                '}';
//    }


    @Override
    public String toString() {
        return "AnimalDto{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", food=" + food +
                '}';
    }
}
