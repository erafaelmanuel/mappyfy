package io.ermdev.mapfierj;

import java.util.ArrayList;
import java.util.List;

public class Animal{

    private String name;
    private Integer size;
    private List<String> list = new ArrayList<>();

    public Animal(){}

    public Animal(String name, Integer size) {
        this.name = name;
        this.size = size;
        list.add("sample");
        list.add("apple");
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

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", list=" + list +
                '}';
    }
}
