package io.ermdev.mapfierj.test.cases.typeconverter;

import io.ermdev.mapfierj.TypeException;
import io.ermdev.mapfierj.typeconverter.IntegerDoubleConverter;
import org.junit.Assert;
import org.junit.Test;

public class IntegerDoubleConverterTest {

    @Test
    public void convertTo() throws TypeException {
        int from = 1;
        double to = 1D;
        Assert.assertEquals((double) new IntegerDoubleConverter().convertTo(from), to, 0.00);
    }

    @Test
    public void convertFrom() throws TypeException {
        double from = 1D;
        int to = 1;
        Assert.assertEquals((int) new IntegerDoubleConverter().convertFrom(from), to);
    }
}
