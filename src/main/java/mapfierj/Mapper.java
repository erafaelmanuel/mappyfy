package mapfierj;

import java.util.Collection;

public class Mapper {

    private final Parser PARSER = new Parser();

    public Parser getConverter() {
        return PARSER;
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
                if (o instanceof Collection) {
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

        public Transaction convertField(String f, Class<?> type) {
            try {
                for (Load load : transactional.getLoads()) {
                    Object fieldValue = load.getVariables().get(f).getValue();
                    Object newObject = PARSER.set(fieldValue).convertTo(type);
                    if (newObject != null) {
                        /** TODO **/
                        load.getVariables().put(f, new Variable("", newObject));
                    } else {
                        load.getVariables().remove(f);
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
                    Object fieldValue = load.getVariables().get(f).getValue();
                    Object newObject = PARSER.set(fieldValue).convertBy(adapter);
                    if (fieldValue != null) {
                        /** TODO **/
                        load.getVariables().put(f, new Variable("", newObject));
                    } else {
                        load.getVariables().remove(f);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public Transaction exclude(String f) {
            for (Load load : transactional.getLoads()) {
                load.getVariables().remove(f);
            }
            return this;
        }

        public Transaction field(String f1, String f2) {
            for (Load load : transactional.getLoads()) {
                Object fieldValue = load.getVariables().get(f1).getValue();
                if (fieldValue != null) {
                    /** TODO **/
                    load.getVariables().put(f2, new Variable("", fieldValue));
                    load.getVariables().remove(f1);
                }
            }
            return this;
        }

        public <T> T mapTo(Class<T> c) {
            return transactional.mapTo(c);
        }

        public Transactional transactional() {
            return transactional;
        }
    }
}
