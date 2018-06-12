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


public class DefaultBeanFactory implements BeanFactory {
    public static final String  ID_ATTRIBUTE= "id";
    public static final String  CLASS_ATTRIBUTE= "class";
    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    public DefaultBeanFactory(String config) {
        loadBeanDeFinition(config);
    }

    private void loadBeanDeFinition(String config) {
        InputStream resourceAsStream = null;
        try {
            ClassLoader cl = ClassUtils.getDefaultClassLoader();//classUtils Spring中定义的工具类 这里用来获取类加载器
            resourceAsStream = cl.getResourceAsStream(config);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement(); //获取根部标签
            Iterator<Element> iterator = rootElement.elementIterator();
            while (iterator.hasNext()){
                Element ele = iterator.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                this.beanDefinitionMap.put(id,bd);

            }
        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document");
        }
        finally {
            if(resourceAsStream !=null) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

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
}
