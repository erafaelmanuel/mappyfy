package mapfierj.v2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Transactional {

    Set<Load> loads = new HashSet<>();

    public Set<Load> getLoads() {
        return loads;
    }

    abstract  <T> T mapTo(Class<T> c);

    abstract  <T> List<T> mapToList(Class<T> c);
}
