package io.ermdev.mapfierj;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface MapTo {

    Class<?> value();

    boolean isCollection() default false;

    Class<?> type() default Object.class;
}
