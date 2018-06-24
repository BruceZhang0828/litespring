package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;


public class ApplicationContextTest {
    @Test
    public void testGetBean(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStoreService = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStoreService);

    }

    @Test
    public void testGetBeanFromFileSystemContext(){
        // 需要添加本地文件的额全路径
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("H:\\petstore-v1.xml");
        PetStoreService petStore = (PetStoreService) applicationContext.getBean("petStore");
        Assert.assertNotNull(petStore);

    }
}
