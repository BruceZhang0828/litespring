package org.litespring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypeStringValue;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

import java.util.List;

public class BeanDfinitionTest {
    @Test
    public void testConstructorArgument(){
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");

        reader.loadBeanDeFinition(resource);
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        Assert.assertEquals("org.litespring.service.v3.PetStoreService",bd.getBeanName());

        ConstructorArgument argument = bd.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = argument.getArgumentValues();

        Assert.assertEquals(3,valueHolders.size());

        RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao",ref1.getBeanName());
        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao",ref2.getBeanName());

        TypeStringValue value = (TypeStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1",value.getValue());


    }
}
