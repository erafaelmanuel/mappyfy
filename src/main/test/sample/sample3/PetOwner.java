package io.ermdev.mapfierj.sample.sample3;

import java.util.ArrayList;
import java.util.List;

public class PetOwner {

    String name;
    List<Pet> pets = new ArrayList<>();

    @Override
    public String toString() {
        return "PetOwner{" +
                "name='" + name + '\'' +
                ", pets=" + pets +
                '}';
    }
}
