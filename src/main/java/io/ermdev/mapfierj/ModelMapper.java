package io.ermdev.mapfierj;

import java.util.HashMap;

public class ModelMapper<F> {

    private Transaction transaction;

    public Transaction set(F obj) {
        try {
            transaction=new Transaction(obj);
            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Transaction set(HashMap<String, Object> map) {
        try {
            transaction=new Transaction(map);
            return transaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
