package mapfierj;

import java.util.Collection;
import java.util.HashMap;

@Deprecated
public class ModelMapper {

    private Transaction transaction;

    private HashMap<String, Object> map = new HashMap<>();

    private final Converter converter;

    public ModelMapper() {
        converter = new Converter();
    }

    public ModelMapper set(Object o) {
        try {
            transaction = new Transaction(o);
            map = transaction.getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelMapper set(HashMap<String, Object> o) {
        try {
            transaction = new Transaction(o);
            map = transaction.getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelMapper set(Collection o) {
        transaction = new Transaction(o);
        map = transaction.getMap();
        return this;
    }

    public ModelMapper field(String field, String field2) {
        final Object o = map.get(field);
        map.remove(field);
        if (o != null) {
            map.put(field2, o);
        }
        return this;
    }

    public ModelMapper exclude(String field) {
        map.remove(field);
        return this;
    }

    public ModelMapper excludeAll(String field) {
        transaction.getExcludedField().add(field);
        return this;
    }

    public ModelMapper convertField(String field, Class<?> type) {
        try {
            final Object instance = converter.convertTo(map.get(field), type);
            if (instance != null)
                map.put(field, instance);
            else
                map.remove(field);
        } catch (MappingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ModelMapper convertFieldBy(String field, TypeConverterAdapter adapter) {
        final Object o = map.get(field);
        map.remove(field);
        if (o != null) {
            try {
                final Converter.Session session = converter.openSession();
                session.set(o);
                session.adapter(adapter);

                Object instance = session.convert();
                if (instance != null) {
                    map.put(field, instance);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public ModelMapper convertFieldBy(String field, Class<? extends TypeConverterAdapter> adapter) {
        final Object o = map.get(field);
        map.remove(field);
        if (o != null) {
            try {
                final Converter.Session session = converter.openSession();
                session.set(o);
                session.adapter(adapter);

                final Object instance = session.convert();
                if (instance != null) {
                    map.put(field, instance);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public <T> T mapTo(Class<T> c) {
        try {
            return transaction.mapTo(c);
        } catch (Exception e) {
            return transaction.mapAllTo(c);
        }
    }

    public Transaction transactional() {
        return transaction;
    }

    public Converter getConverter() {
        return converter;
    }
}
