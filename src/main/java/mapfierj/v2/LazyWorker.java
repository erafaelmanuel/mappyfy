package mapfierj.v2;

import java.util.ArrayList;
import java.util.List;

public class LazyWorker extends Transactional {

    LazyWorker(Object o) {
        try {
            if (o != null) {
                loads.add(new Load(o));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    LazyWorker(Object... os) {
        try {
            for(Object o : os) {
                loads.add(new Load(o));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T mapTo(Class<T> c) {
        switch (loads.size()) {
            case 0: {
                throw new RuntimeException("Invalid to map a null object");
            }
            case 1: {
                Load load = loads.iterator().next();
                return new InstanceCreator<T>(load, c).newInstance();
            }
            default: {
                throw new RuntimeException("Multiple objects is illegal");
            }
        }
    }

    @Override
    <T> List<T> mapToList(Class<T> c) {
        List<T> list = new ArrayList<>();
        for(Load load : loads) {
            list.add(new InstanceCreator<T>(load, c).newInstance());
        }
        return list;
    }
}
