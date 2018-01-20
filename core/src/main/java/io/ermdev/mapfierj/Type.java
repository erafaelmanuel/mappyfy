package io.ermdev.mapfierj;

public enum Type {

    SINGLE(1), COLLECTION(2);

    int value;
    Type(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
