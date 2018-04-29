package mapfierj;

import java.util.Collection;

public class Mapper {

    private final ConverterImpl converter = new ConverterImpl();

    public Transaction set(Object o) {
        return new Transaction(o);
    }

    public Transaction set(Object[] o) {
        return new Transaction(o);
    }

    public ConverterImpl getConverter() {
        return converter;
    }

    final public class Transaction {

        private Transactional transactional;

        private Transaction(Object o) {
            try {
                if (o instanceof Collection) {
                    transactional = new TransactionalImpl(((Collection) o).toArray());
                } else {
                    transactional = new TransactionalImpl(o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Transaction(Object[] o) {
            try {
                transactional = new TransactionalImpl(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Transaction convertField(String f, Class<?> type) {
            try {
                for (Load load : transactional.getLoads()) {
                    final Variable variable = load.getVariables().get(f);
                    final Object o = converter.set(variable.getValue()).convertTo(type);
                    if (o != null) {
                        /** TODO **/
                        load.getVariables().put(f, new Variable(type.toString(), o));
                    } else {
                        load.getVariables().remove(f);
                    }
                }
            } catch (MapException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Transaction convertFieldBy(String f, TypeConverterAdapter adapter) {
            try {
                for (Load load : transactional.getLoads()) {
                    final Variable variable = load.getVariables().get(f);
                    final Object o = converter.set(variable.getValue()).convertBy(adapter);
                    if (o != null) {
                        /** TODO **/
                        load.getVariables().put(f, new Variable(o.getClass().toString(), o));
                    } else {
                        load.getVariables().remove(f);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public Transaction ignore(String f) {
            for (Load load : transactional.getLoads()) {
                load.getVariables().remove(f);
            }
            return this;
        }

        public Transaction field(String f1, String f2) {
            for (Load load : transactional.getLoads()) {
                Variable variable = load.getVariables().get(f1);
                if (variable.getValue() != null) {
                    /** TODO **/
                    load.getVariables().put(f2, new Variable(variable.getType(), variable.getValue()));
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
