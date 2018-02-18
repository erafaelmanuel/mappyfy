package mapfierj;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeConverterAdapter<A, B> {

    @SuppressWarnings("unchecked")
    public Object convert(Object o) throws TypeException {
        if (o != null) {

            try {
                Type types[] = (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments());
                if (types[0].equals(o.getClass())) {
                    return convertTo((A) o);
                }
                if (types[1].equals(o.getClass())) {
                    return convertFrom((B) o);
                }
                throw new Exception();
            } catch (Exception e) {
                throw new TypeException("Unable to parse the object");
            }
        } else {
            throw new TypeException("You can't convert a null object");
        }
    }

    public abstract B convertTo(A o) throws TypeException;

    public abstract A convertFrom(B o) throws TypeException;
}
