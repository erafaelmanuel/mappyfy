package mapfierj;

public class Mapper {

    public Session set(Object o) {
        return new Session(o);
    }

    public class Session {

        private Transaction transaction;
        private Converter converter = new Converter();

        public Session(Object o) {
            try {
                transaction = new Transaction(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Session exclude(String f) {
            transaction.getMap().remove(f);
            return this;
        }

        public Session excludeAll(String f) {
            transaction.getExcludedField().add(f);
            return this;
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

        public Session convertField(String f, TypeConverterAdapter c) {
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
    }
}
