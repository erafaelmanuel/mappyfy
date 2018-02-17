package mapfierj.xyz;

import mapfierj.TypeConverterAdapter;

public interface Converter {

    String BASE_PACKAGE = "mapfierj.converter";

    void scanPackages(String... packages);

    void register(TypeConverterAdapter adapter);
}
