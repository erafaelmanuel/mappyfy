package io.ermdev.mapfierj.core;

public @interface ConvertTo {

    Class<? extends TypeConverterAdapter> value();
}
