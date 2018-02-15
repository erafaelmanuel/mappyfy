package mapfierj.v2;

import mapfierj.Converter;
import mapfierj.MappingException;
import mapfierj.TypeConverterAdapter;

public class Mapper {

    private Converter converter = new Converter();

    public Converter getConverter() {
        return converter;
    }

    public Session set(Object o) {
        return new Session(o);
    }

    public Session set(Object... o) {
        return new Session(o);
    }

    public class Session {

        private Transactional worker;

        private Session(Object o) {
            try {
                worker = new LazyWorker(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Session(Object... o) {
            try {
                worker = new LazyWorker(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Session convertField(String f, Class<?> t) {
            try {
                for (Load load : worker.getLoads()) {
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

        public Session convertFieldBy(String f, TypeConverterAdapter adapter) {
            try {
                for (Load load : worker.getLoads()) {
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

        public Session exclude(String f) {
            for (Load load : worker.getLoads()) {
                load.getFields().remove(f);
            }
            return this;
        }

        public Session field(String f1, String f2) {
            for (Load load : worker.getLoads()) {
                Object fieldValue = load.getFields().get(f1);
                if (fieldValue != null) {
                    load.getFields().put(f2, fieldValue);
                    load.getFields().remove(f1);
                }
            }
            return this;
        }

        public <T> T mapTo(Class<T> c) {
            return worker.mapTo(c);
        }

        public Transactional transactional() {
            return worker;
        }
    }
}
