package mapfierj.xyz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Transactional {

    List<Load> loads = new ArrayList<>();

    public List<Load> getLoads() {
        return loads;
    }

    public abstract <T> T mapTo(Class<T> c);

    public abstract <T> List<T> mapToList(Class<T> c);

    public abstract <T> Set<T> mapToSet(Class<T> c);

    public abstract <T> T[] mapToArray(Class<T> c);
}
