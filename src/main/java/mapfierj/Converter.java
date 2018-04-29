package mapfierj;

public interface Converter {

    void scanPackages(String... packages);

    void register(TypeConverterAdapter adapter);
}
