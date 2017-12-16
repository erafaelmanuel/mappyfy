package io.ermdev.mapfierj;

@TypeConverter(applyTo = Animal.class)
public class LongAnimalTypeConverter extends TypeConverterAdapter<String, Food> {

    @Override
    public String convertTo(Food food) {
        return food.getName();
    }

    @Override
    public Food convertFrom(String name) {
        return new Food(name);
    }
}
