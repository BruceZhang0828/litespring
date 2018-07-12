package org.litespring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.ConstructorResolver;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.service.v3.PetStoreService;

public class ConstructorResolverTest {

    @Test
    public void testAutowireConstructor(){
        //创建工厂类
        DefaultBeanFactory factory = new DefaultBeanFactory();
        //读取xml文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        //加载配置文件
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDeFinition(resource);//xml读取文件类 加载配置文件的资源

        BeanDefinition bd = factory.getBeanDefinition("petStore");
        //构造注入 解析器
        ConstructorResolver resolver = new ConstructorResolver(factory);
        PetStoreService petStoreService = (PetStoreService) resolver.autowireConstructor(bd);
        //为了验证 根据配置文件 使用正确的构造函数...
        Assert.assertEquals(1,petStoreService.getVersion());

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}
