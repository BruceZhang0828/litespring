package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

public class CustomNumberEditorTest {

    @Test
    public void testCoverString(){
        //给定一个字符串 转换为数字类型
        CustomNumberEditor customNumberEditor = new CustomNumberEditor(Integer.class,true);
        customNumberEditor.setAsText("3");
        Object value = customNumberEditor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3,((Integer) value).intValue());
        //传入的空字符串 返回值为 null
        customNumberEditor.setAsText("");
        Assert.assertTrue(customNumberEditor.getValue()==null);

        try {
            //传入的字符串 不符合 int 类型 则抛出 IllegalArgument
            customNumberEditor.setAsText("v3.1");
        } catch (IllegalArgumentException e) {
            return;
        }

        Assert.fail();
    }

}
