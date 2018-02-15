package sample.sample5;

import java.util.Arrays;

public class SuperMan {

    String name;
    int age;
    String enemy[];

    @Override
    public String toString() {
        return "SuperMan{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", enemy=" + Arrays.toString(enemy) +
                '}';
    }
}
