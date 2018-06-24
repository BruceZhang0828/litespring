package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

    private DefaultBeanFactory factory = null;

    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);

    }

    @Override
    protected Resource getResourceByPath(String configFile) {
        Resource resource = new FileSystemResource(configFile);
        return resource;
    }


}
