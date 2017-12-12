package io.ermdev.mapfierj;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotRawValidator.class)
public @interface NotRaw {
    String message() default "Its a raw object";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
