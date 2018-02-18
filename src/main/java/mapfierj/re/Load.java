package mapfierj.re;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Load {

    private HashMap<String, Object> fields = new HashMap<>();

    Load(Object o) {
        try {
            if (o != null) {
                Field variables[] = o.getClass().getDeclaredFields();
                for (Field var : variables) {
                    var.setAccessible(true);
                    if (var.getAnnotation(mapfierj.re.Field.class) != null) {
                        String name = var.getAnnotation(mapfierj.re.Field.class).name();
                        fields.put(name, var.get(o));
                    } else {
                        fields.put(var.getName(), var.get(o));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Object> getFields() {
        return fields;
    }
}
