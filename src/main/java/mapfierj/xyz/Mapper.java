package mapfierj.xyz;

import mapfierj.Converter;
import mapfierj.MappingException;
import mapfierj.TypeConverterAdapter;

import java.util.Collection;

public class Mapper {

    private Converter converter = new Converter();

    public Converter getConverter() {
        return converter;
    }

    public Transaction set(Object o) {
        return new Transaction(o);
    }

    public Transaction set(Object[] o) {
        return new Transaction(o);
    }

    public class Transaction {

        private Transactional transactional;

        private Transaction(Object o) {
            try {
                if(o instanceof Collection) {
                    transactional = new LazyWorker(((Collection) o).toArray());
                } else {
                    transactional = new LazyWorker(o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Transaction(Object[] o) {
            try {
                transactional = new LazyWorker(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Transaction convertField(String f, Class<?> t) {
            try {
                for (Load load : transactional.getLoads()) {
                    Object fieldValue = load.getFields().get(f);
                    Object newObject = converter.convertTo(fieldValue, t);
                    if (newObject != null) {
                        load.getFields().put(f, newObject);
                    } else {
                        load.getFields().remove(f);
                    }
                }
            } catch (MappingException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Transaction convertFieldBy(String f, TypeConverterAdapter adapter) {
            try {
                for (Load load : transactional.getLoads()) {
                    Object fieldValue = load.getFields().get(f);
                    if (fieldValue != null) {
                        Converter.Session session = converter.openSession();
                        session.set(fieldValue);
                        session.adapter(adapter);
                        load.getFields().put(f, session.convert());
                    } else {
                        load.getFields().remove(f);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public Transaction exclude(String f) {
            for (Load load : transactional.getLoads()) {
                load.getFields().remove(f);
            }
            return this;
        }

        public Transaction field(String f1, String f2) {
            for (Load load : transactional.getLoads()) {
                Object fieldValue = load.getFields().get(f1);
                if (fieldValue != null) {
                    load.getFields().put(f2, fieldValue);
                    load.getFields().remove(f1);
                }
            }
            return this;
        }

        public <T> T mapTo(Class<T> c) {
            return transactional.mapTo(c);
        }

        public Transactional transact() {
            return transactional;
        }
    }
}
