package mapfierj;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Deprecated
@Target(TYPE)
@Retention(RUNTIME)
public @interface NoRepeat {}
