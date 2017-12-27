package io.ermdev.mapfierj.core;

import java.util.HashMap;

public class ModelMapper {

    private Transaction transaction;
    private HashMap<String, Object> map = new HashMap<>();

    public ModelMapper set(Object obj) {
        try {
            transaction=new Transaction(obj);
            map=transaction.getMap();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Transaction setAndGetTransaction(Object obj) {
        try {
            transaction=new Transaction(obj);
            map=transaction.getMap();
            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelMapper field(String name1, String name2) {
        final Object o = map.get(name1);
        map.remove(name1);
        if(o != null) {
            map.put(name2, o);
            setTransaction(new Transaction(map));
        }
        return this;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
