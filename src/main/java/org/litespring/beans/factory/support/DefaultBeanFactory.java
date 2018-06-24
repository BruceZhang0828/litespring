package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将相关 加载xml文件的功能 抽离出去 保证类的单一职责
 */
public class DefaultBeanFactory implements BeanFactory,BeanDefinitionRegistry{
    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    public Object getBean(String beanId) {
        BeanDefinition bd = this.getBeanDefinition(beanId);
        if(bd ==null){
            throw new BeanCreationException("bean Definition does not exist");
        }
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanName();
        try {
            Class<?> claz = cl.loadClass(beanClassName);
                return claz.newInstance();
            } catch (Exception e) {
                throw  new BeanCreationException("Create bean for "+beanClassName+"fail");
            }
    }

    public org.litespring.beans.BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public void registryBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanId,beanDefinition);
    }
}
