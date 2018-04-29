package test1;

public class PersonDto {

    String fullName;

    int age;

    @Override
    public String toString() {
        return "PersonDto{" +
                "fullName='" + fullName + '\'' +
                ", age=" + age +
                '}';
    }
}
