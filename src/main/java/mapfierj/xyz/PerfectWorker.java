package mapfierj.xyz;

import java.util.List;
import java.util.Set;

public class PerfectWorker extends Transactional {

    @Override
    public <T> T mapTo(Class<T> c) {
        return null;
    }

    @Override
    public <T> List<T> mapToList(Class<T> c) {
        return null;
    }

    @Override
    public <T> Set<T> mapToSet(Class<T> c) {
        return null;
    }

    @Override
    public <T> T[] mapToArray(Class<T> c) {
        return null;
    }
}
