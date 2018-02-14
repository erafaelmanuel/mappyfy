package mapfierj;

import java.util.Collection;
import java.util.HashMap;

public class Mapper {

    private ModelMapper modelMapper;

    public Mapper() {
        modelMapper = new ModelMapper();
    }

    public Mapper(String packagesToScan) {
        modelMapper = new ModelMapper(packagesToScan);
    }

    public ModelMapper set(Object o) {
        modelMapper.set(o);
        return modelMapper;
    }

    public ModelMapper set(Collection o) {
        modelMapper.set(o);
        return modelMapper;
    }

    public ModelMapper set(HashMap<String, Object> o) {
        modelMapper.set(o);
        return modelMapper;
    }
}
