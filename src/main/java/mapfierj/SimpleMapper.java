package mapfierj;

import java.util.Collection;
import java.util.HashMap;

@Deprecated
public class SimpleMapper {

    private Transaction transaction;

    public Transaction set(Object obj) {
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

    public Transaction set(Collection collection) {
        return transaction=new Transaction(collection);
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
