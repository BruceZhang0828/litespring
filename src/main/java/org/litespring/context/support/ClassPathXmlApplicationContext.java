package org.litespring.context.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.NoSuchBeanDefinitionException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    private DefaultBeanFactory factory = null;


    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }



    @Override
    protected Resource getResourceByPath(String configFile) {
        Resource resource = new ClassPathResource(configFile);
        return resource;
    }

    public Class<?> getType(String beanName){
        return this.factory.getType(beanName);
    }
}
