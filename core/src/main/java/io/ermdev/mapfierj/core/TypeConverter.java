package io.ermdev.mapfierj.core;

public @interface TypeConverter {

    Class<?>[] applyTo() default {};
}
