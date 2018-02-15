package sample.sample5;

import java.util.Arrays;

public class BatMan {

    String fullname;
    Long age;
    String enemy[];

    @Override
    public String toString() {
        return "BatMan{" +
                "fullname='" + fullname + '\'' +
                ", age=" + age +
                ", enemy=" + Arrays.toString(enemy) +
                '}';
    }
}
