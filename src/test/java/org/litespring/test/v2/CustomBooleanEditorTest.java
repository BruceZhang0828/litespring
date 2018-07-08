package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomBooleanEditor;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

public class CustomBooleanEditorTest {

    @Test
    public void testCoverStringToBoolean(){
        CustomBooleanEditor customBooleanEditor = new CustomBooleanEditor(true);

        customBooleanEditor.setAsText("true");
        Assert.assertEquals(true,((Boolean)customBooleanEditor.getValue()).booleanValue());
        customBooleanEditor.setAsText("false");
        Assert.assertEquals(false,((Boolean)customBooleanEditor.getValue()).booleanValue());

        customBooleanEditor.setAsText("on");
        Assert.assertEquals(true,((Boolean)customBooleanEditor.getValue()).booleanValue());
        customBooleanEditor.setAsText("off");
        Assert.assertEquals(false,((Boolean)customBooleanEditor.getValue()).booleanValue());

        customBooleanEditor.setAsText("yes");
        Assert.assertEquals(true,((Boolean)customBooleanEditor.getValue()).booleanValue());
        customBooleanEditor.setAsText("no");
        Assert.assertEquals(false,((Boolean)customBooleanEditor.getValue()).booleanValue());

        try {
            //传入的字符串 不符合 int 类型 则抛出 IllegalArgument
            customBooleanEditor.setAsText("abcaklfjal");
        } catch (IllegalArgumentException e) {
            return;
        }

        Assert.fail();
    }
}
