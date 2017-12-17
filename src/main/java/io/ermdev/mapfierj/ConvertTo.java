package io.ermdev.mapfierj;

public @interface ConvertTo {

    Class<? extends TypeConverterAdapter> value();
}
