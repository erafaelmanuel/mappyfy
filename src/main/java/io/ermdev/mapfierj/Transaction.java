package io.ermdev.mapfierj;

public class Transaction<To, From> {

    private To obj;

    public Transaction(To obj) {
        this.obj=obj;
        if(obj instanceof ToModel)
            System.out.println("Not Raw");
        else
            System.out.println("Raw");
    }

    public <T> T mapTo(Class<T> _class) throws IllegalAccessException, InstantiationException {
        return _class.newInstance();
    }

    public From map() {
        From f = null;
        return f;
    }
}
