package test1;

import com.rem.mappyfy.Bind;

public class Doctor {

    @Bind(fields = {"name", "firstName"})
    private String fullname;

    private int age;

    @Override
    public String toString() {
        return "Doctor{" +
                "fullname='" + fullname + '\'' +
                ", age=" + age +
                '}';
    }
}
