package mapfierj;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Load {

    private HashMap<String, Variable> variables = new HashMap<>();

    Load(Object o) {
        try {
            if (o != null) {
                Field fields[] = o.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getAnnotation(mapfierj.Field.class) != null) {
                        String name = field.getAnnotation(mapfierj.Field.class).name();
                        variables.put(name, new Variable(field.getType().toString(), field.get(o)));
                    } else {
                        variables.put(field.getName(), new Variable(field.getType().toString(), field.get(o)));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }
}
