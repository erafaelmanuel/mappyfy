package io.ermdev.mapfierj;

import java.util.HashMap;

public class SimpleMapper {

    private Transaction transaction;

    public Transaction set(Object obj) {
        try {
            transaction=new Transaction(obj);
            return transaction;
        } catch (Exception e) {
            return null;
        }
    }

    public Transaction set(HashMap<String, Object> map) {
        try {
            transaction=new Transaction(map);
            return transaction;
        } catch (Exception e) {
            return null;
        }
    }
}
