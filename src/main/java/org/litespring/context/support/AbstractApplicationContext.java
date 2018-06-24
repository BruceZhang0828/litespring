package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;

/**
 * 为了解决 ClassPathXmlApplicationContext 和 FileSystemXmlApplicationContext中重复代码问题
 * 根据设计模式中模板方法 在接口和实现类中间创建一个中间的抽象类
 * 提取相同代码
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDeFinition(resource);
    }

    public Object getBean(String beanId) {
        return this.factory.getBean(beanId);
    }

    protected abstract Resource getResourceByPath(String configFile);

}
