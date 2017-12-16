package io.ermdev.mapfierj;

public @interface TypeConverter {

    Class<?>[] applyTo() default {};
}
