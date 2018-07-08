package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;
import org.litespring.service.v2.PetStoreService;


public class ApplicationContextTest {
    @Test
    public void getTestBeanProperty(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");
        AccountDao accountDao = petStore.getAccountDao();
        ItemDao itemDao = petStore.getItemDao();
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

        Assert.assertTrue(accountDao instanceof AccountDao);
        Assert.assertTrue(itemDao instanceof ItemDao);

        Assert.assertEquals("zhy",petStore.getOwner());
        Assert.assertEquals(2,petStore.getVersion());
    }

}
