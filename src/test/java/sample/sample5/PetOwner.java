package sample.sample5;

import java.util.ArrayList;
import java.util.List;

public class PetOwner {

    String name;
    List<Pet> pet = new ArrayList<>();

    @Override
    public String toString() {
        return "PetOwner{" +
                "name='" + name + '\'' +
                ", pet=" + pet +
                '}';
    }
}
