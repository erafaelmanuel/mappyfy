package io.ermdev.mapfierj.core;

public class TypeChecker {

    private static final String[] PRIMITIVE_TYPES = {"byte", "short", "int", "long", "float", "double","char", "boolean"};

    public static boolean compare(Class<?> primitive, Class<?> reference) {
        if(primitive == null || reference == null) return false;
        switch (primitive.toString()) {
            case "byte" : return reference.equals(Byte.class);
            case "short" : return reference.equals(Short.class);
            case "int" : return reference.equals(Integer.class);
            case "long" : return reference.equals(Long.class);
            case "float" : return reference.equals(Float.class);
            case "double" : return reference.equals(Double.class);
            case "char" : return reference.equals(Character.class);
            case "boolean" : return reference.equals(Boolean.class);
            default: return false;
        }
    }

    public static boolean isPrimitive(Class<?> c) {
        if(c == null) return false;
        for(String p : PRIMITIVE_TYPES) {
            if(p.equals(c.toString()))
                return true;
        }
        return false;
    }

    public static boolean isReference(Class<?> c) {
        return !isPrimitive(c);
    }
}
