package test1;

public class PersonDto {

    String name;

    int age;

    @Override
    public String toString() {
        return "PersonDto{" +
                "fullName='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
