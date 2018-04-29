package mapfierj;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Deprecated
@Target(FIELD)
@Retention(RUNTIME)
public @interface MapTo {

    Class<?> value();

    Strategy strategy() default Strategy.SINGLE;

    Type type() default Type.DEFAULT;

    enum Strategy {
        SINGLE(1), COLLECTION(2);

        int value;
        Strategy(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
}
