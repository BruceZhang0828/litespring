package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {
    //目标给定给一个bean的配置文件
    @Test
    public void testGetBean(){
        //根据 xml配置文件 创建bean工厂
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        // 根据bean的id创建
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        Assert.assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanName());
        //获取bean的内容
        PetStoreService perStoreService = (PetStoreService)factory.getBean("petStore");

        Assert.assertNotNull(perStoreService);
    }

    @Test
    public void testInvlidBean(){

        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            factory.getBean("invlidBean");
        } catch (Exception e) {
            return;
        }
        Assert.fail("expect BeanCreationException");
    }
    @Test
    public void testInvlidXml(){
        try {
            new DefaultBeanFactory("xxxxx.xml");
        } catch (Exception e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException");
    }
}
