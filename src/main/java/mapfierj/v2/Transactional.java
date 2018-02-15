package mapfierj.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Transactional {

    List<Load> loads = new ArrayList<>();

    public List<Load> getLoads() {
        return loads;
    }

    abstract <T> T mapTo(Class<T> c);

    abstract <T> List<T> mapToList(Class<T> c);

    abstract <T> Set<T> mapToSet(Class<T> c);

    abstract <T> T[] mapToArray(Class<T> c);
}
