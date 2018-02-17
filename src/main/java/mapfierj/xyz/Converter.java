package mapfierj.xyz;

import mapfierj.MappingException;
import mapfierj.TypeConverterAdapter;
import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Converter {

    private final String BASE_PACKAGE = "mapfierj.converter";

    private final Set<TypeConverterAdapter> LOCAL_CONVERTER = new HashSet<>();

    public Converter() {
        final Reflections reflections = new Reflections(BASE_PACKAGE);
        reflections.getSubTypesOf(TypeConverterAdapter.class)
                .parallelStream().forEach((c -> LOCAL_CONVERTER.add(InstanceCreator.create(c))));
    }

    public Transaction set(Object o) {
        return new Transaction(o);
    }

    public class Transaction {

        private Object o;
        private Set<TypeConverterAdapter> validConverter = new HashSet<>();

        public Transaction(Object o) {
            this.o = o;
        }

        @SuppressWarnings("unchecked")
        public <T> T convertTo(Class<T> c) throws MappingException {
            Set<TypeConverterAdapter> converters = new HashSet<>();
            converters.addAll(LOCAL_CONVERTER);

            converters.parallelStream().filter(adapter -> {
                final Type types[] = (((ParameterizedType)
                        adapter.getClass().getGenericSuperclass()).getActualTypeArguments());
                return Arrays.asList(types).parallelStream()
                        .anyMatch(type -> type.toString().equals(o.getClass().toString()));
            }).filter(adapter -> {
                final Type types[] = (((ParameterizedType)
                        adapter.getClass().getGenericSuperclass()).getActualTypeArguments());
                return Arrays.asList(types).parallelStream()
                        .anyMatch(type -> type.toString().equals(c.toString()));
            }).forEach(adapter -> validConverter.add(adapter));

            if(validConverter.size() > 0) {
                while (validConverter.iterator().hasNext()) {
                    try {
                        return (T) validConverter.iterator().next().convert(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                throw new MappingException("Something came up!");
            } else {
                throw new MappingException("No converter match for your object!");
            }
        }
    }
}
