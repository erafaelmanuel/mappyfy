package mapfierj;

public enum Type {

    DEFAULT(1), LIST(2), SET(3);

    int value;
    Type(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
