package mapfierj.xyz;

import mapfierj.MappingException;
import mapfierj.TypeConverterAdapter;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

public class Converter {

    private final String BASE_PACKAGE = "mapfierj.converter";

    private final Set<TypeConverterAdapter> LOCAL_CONVERTER = new HashSet<>();

    public Converter() {
        final Reflections reflections = new Reflections(BASE_PACKAGE);
        reflections.getSubTypesOf(TypeConverterAdapter.class)
                .forEach((c -> LOCAL_CONVERTER.add(InstanceCreator.create(c))));
    }

    public Transaction set(Object o) {
        return new Transaction(o);
    }

    public class Transaction {

        private Object o;

        public Transaction(Object o) {
            this.o = o;
        }

        public <T> T convertTo(Class<T> type) throws MappingException {
            return null;
        }
    }
}
