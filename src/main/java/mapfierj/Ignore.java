package mapfierj;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Deprecated
@Target(FIELD)
@Retention(RUNTIME)
public @interface Ignore {}
