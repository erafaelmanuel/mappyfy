package mapfierj.xyz;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LazyWorker extends Transactional {

    LazyWorker(Object o) {
        if (o != null) {
            loads.add(new Load(o));
        }
    }

    LazyWorker(Object[] os) {
        if (os != null) {
            for (Object o : os) {
                loads.add(new Load(o));
            }
        }
    }

    @Override
    public <T> T mapTo(Class<T> c) {
        switch (loads.size()) {
            case 0: {
                throw new RuntimeException("No load to map!");
            }
            case 1: {
                Load load = loads.iterator().next();
                return new InstanceCreator<>(load, c).newInstance();
            }
            default: {
                throw new RuntimeException("Unable to use mapTo when the load is more than one");
            }
        }
    }

    @Override
    public <T> List<T> mapToList(Class<T> c) {
        List<T> list = new ArrayList<>();
        for (Load load : loads) {
            list.add(new InstanceCreator<>(load, c).newInstance());
        }
        return list;
    }

    @Override
    public <T> Set<T> mapToSet(Class<T> c) {
        Set<T> set = new HashSet<>();
        for (Load load : loads) {
            set.add(new InstanceCreator<>(load, c).newInstance());
        }
        return set;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] mapToArray(Class<T> c) {
        try {
            T array[] = (T[]) Array.newInstance(c, loads.size());
            for (int i = 0; i < array.length; i++) {
                array[i] = new InstanceCreator<>(loads.get(i), c).newInstance();
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
