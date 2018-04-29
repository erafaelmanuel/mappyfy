package mapfierj;

import org.reflections.Reflections;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConverterImpl implements Converter {

    private final String BASE_PACKAGE = "mapfierj.tc";

    private final Set<TypeConverterAdapter> ADAPTERS = new HashSet<>();

    public ConverterImpl() {
        new Reflections(BASE_PACKAGE).getSubTypesOf(TypeConverterAdapter.class)
                .parallelStream().forEach((c -> ADAPTERS.add(InstanceHelper.create(c))));
    }

    @Override
    public void scanPackages(String... packages) {
        for (String item : packages) {
            if (item != null && !item.trim().isEmpty()) {
                new Reflections(item).getSubTypesOf(TypeConverterAdapter.class)
                        .parallelStream().forEach((c -> ADAPTERS.add(InstanceHelper.create(c))));
            }
        }
    }

    @Override
    public void register(TypeConverterAdapter adapter) {
        ADAPTERS.add(adapter);
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
        public <T> T convertTo(Class<T> c) throws MapException {
            ADAPTERS.parallelStream().filter(adapter -> {
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

            if (validConverter.size() > 0) {
                while (validConverter.iterator().hasNext()) {
                    try {
                        return (T) validConverter.iterator().next().convert(o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                throw new MapException("Unable to convert!");
            } else {
                throw new MapException("No tc match for your object!");
            }
        }

        @SuppressWarnings("unchecked")
        public <T> T convertBy(TypeConverterAdapter adapter) throws MapException {
            try {
                return (T) adapter.convert(o);
            } catch (Exception e) {
                throw new MapException("Unable to convert!");
            }
        }
    }
}
