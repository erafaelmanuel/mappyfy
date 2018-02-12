package mapfierj.converter;

import mapfierj.Converter;
import mapfierj.MappingException;
import mapfierj.Transaction;
import mapfierj.TypeConverterAdapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Mapper {

    private Transaction transaction;
    private HashMap<String, Object> map = new HashMap<>();
    private final Converter converter;

    public Mapper() {
        converter = new Converter();
    }

    public Mapper(final String... scanPackages) {
        this();
        converter.scanPackages(scanPackages);
    }

    public Mapper set(Object o) {
        try {
            transaction = new Transaction(o);
            map = transaction.getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Mapper set(HashMap<String, Object> o) {
        try {
            transaction = new Transaction(o);
            map = transaction.getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Mapper set(Collection o) {
        transaction = new Transaction(o);
        map = transaction.getMap();
        return this;
    }

    public Mapper field(String field, String field2) {
        final Object o = map.get(field);
        map.remove(field);
        if (o != null) {
            map.put(field2, o);
        }
        return this;
    }

    public Mapper exclude(String field) {
        map.remove(field);
        return this;
    }

    public Mapper excludeAll(String field) {
        transaction.getExcludedField().add(field);
        return this;
    }

    public Mapper convertFieldToType(String field, Class<?> type) {
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

    public Mapper convertFieldByConverter(String field, TypeConverterAdapter adapter) {
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

    public Mapper convertFieldByConverter(String field, Class<? extends TypeConverterAdapter> adapter) {
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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        this.map = transaction.getMap();
    }

    public Converter getConverter() {
        return converter;
    }

    public <T> T mapTo(Class<T> o) {
        try {
            return getTransaction().mapTo(o);
        } catch (Exception e) {
            return getTransaction().mapAllTo(o);
        }
    }

    public <T> List<T> mapToList(Class<T> o) {
        return getTransaction().mapToList(o);
    }
}
