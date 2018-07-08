package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.TypeMissmatchException;

public class TypeConverterTest {

    @Test
    public void testConvertStringToInt(){
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessaray("3",Integer.class);

        Assert.assertEquals(3,i.intValue());
        try {
            converter.convertIfNecessaray("3.1",Integer.class);
        } catch (TypeMissmatchException e) {
            return;
        }
        Assert.fail();

    }
    @Test
    public void testConvertStringToBoolean(){
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessaray("true",Boolean.class);

        Assert.assertEquals(true,b.booleanValue());

        try {
            converter.convertIfNecessaray("asfjlafj",Boolean.class);
        } catch (TypeMissmatchException e) {
            return;
        }
        Assert.fail();


    }
}
