package io.ermdev.mapfierj.sample.sample1;

public class CarRepository {

    private final String[] cars = {"Jeep", "Datsun", "Chrysler", "Lada", "Roewe", "Lagonda", "MINI", "Spyker  Cars"};
    public Car getById(int id) {
        try {
            return new Car(id, cars[id-1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}
