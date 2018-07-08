package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {
    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader xmlBeanDefinitionReader = null;

    @Before//运行每一个测试用例之前 完成一些配置性代码  每一个测试用例使用的对象都是全新的互不影响
    public void setUp(){
         factory = new DefaultBeanFactory();
         xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);

    }
    //目标给定给一个bean的配置文件
    @Test
    public void testGetBean(){
        //根据 xml配置文件 创建bean工厂
        //BeanFactory factory = new DefaultBeanFactory();
        // 根据bean的id创建
        Resource resource = new ClassPathResource("petstore-v1.xml");
        xmlBeanDefinitionReader.loadBeanDeFinition(resource);
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        Assert.assertTrue(bd.isSingleton());
        Assert.assertFalse(bd.isPrototype());
        Assert.assertEquals(BeanDefinition.SCOPE_DEFAULT,bd.getScope());
        Assert.assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanName());
        //获取bean的内容
        PetStoreService perStoreService = (PetStoreService)factory.getBean("petStore");

        Assert.assertNotNull(perStoreService);
    }

    @Test
    public void testInvlidBean(){

        try {
            //xmlBeanDefinitionReader.loadBeanDeFinition("petstore-v2.xml");
            Resource resource = new ClassPathResource("petstore-v1.xml");
            xmlBeanDefinitionReader.loadBeanDeFinition(resource);
            factory.getBean("invlidBean");
        } catch (Exception e) {
            return;
        }
        Assert.fail("expect BeanCreationException");
    }
    @Test
    public void testInvlidXml(){


        try {
            //new DefaultBeanFactory("xxxxx.xml");
            //xmlBeanDefinitionReader.loadBeanDeFinition("xxxxx.xml");
            Resource resource = new ClassPathResource("xxxx.xml");
            xmlBeanDefinitionReader.loadBeanDeFinition(resource);
        } catch (Exception e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException");
    }
}
