package mapfierj;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Mapper {

    private Converter converter = new Converter();

    public Mapper(){}

    public Mapper(String... packages) {
        converter.scanPackages(packages);
    }

    public Converter getConverter() {
        return converter;
    }

    public Session set(Object o) {
        return new Session(o);
    }

    public Session set(Collection o) {
        return new Session(o);
    }

    public Session set(HashMap<String, Object> o) {
        return new Session(o);
    }

    public class Session {

        private Transaction transaction;

        public Session(Object o) {
            try {
                transaction = new Transaction(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Session(Collection o) {
            try {
                transaction = new Transaction(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Session(HashMap<String, Object> o) {
            try {
                transaction = new Transaction(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Session convertField(String f, Class<?> t) {
            try {
                final Object o = converter.convertTo(transaction.getMap().get(f), t);
                if (o != null) {
                    transaction.getMap().put(f, o);
                } else {
                    transaction.getMap().remove(f);
                }
            } catch (MappingException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Session convertFieldBy(String f, TypeConverterAdapter c) {
            final Object o = transaction.getMap().get(f);
            transaction.getMap().remove(f);
            if (o != null) {
                try {
                    Object instance;
                    Converter.Session session = converter.openSession();
                    session.set(o);
                    session.adapter(c);

                    if ((instance = session.convert()) != null) {
                        transaction.getMap().put(f, instance);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return this;
        }

        public Session exclude(String f) {
            transaction.getMap().remove(f);
            return this;
        }

        public Session excludeAll(String f) {
            transaction.getExcludedField().add(f);
            return this;
        }

        public Session field(String f1, String f2) {
            Object o = transaction.getMap().get(f1);
            transaction.getMap().remove(f1);
            if (o != null) {
                transaction.getMap().put(f2, o);
            }
            return this;
        }

        public <T> T mapTo(Class<T> c) {
            T o = transaction.mapTo(c);
            if(o != null) {
                return o;
            } else {
                return transaction.mapAllTo(c);
            }
        }

        public <T> List<T> mapToList(Class<T> c) {
            try {
                return transaction.mapToList(c);
            } catch (Exception e) {
                return null;
            }
        }

        public <T> Set<T> mapToSet(Class<T> c) {
            try {
                return transaction.mapToSet(c);
            } catch (Exception e) {
                return null;
            }
        }
    }
}
