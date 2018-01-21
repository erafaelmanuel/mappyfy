package io.ermdev.mapfierj.v2;

import io.ermdev.mapfierj.Type;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface ConvertTo {

    Class<?> value();

    Type type() default Type.SINGLE;
}
