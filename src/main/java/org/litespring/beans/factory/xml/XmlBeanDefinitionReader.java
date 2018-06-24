package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * 用来保证 DefaultBeanFactory的单一职责
 */
public class XmlBeanDefinitionReader {
    public static final String  ID_ATTRIBUTE= "id";
    public static final String  CLASS_ATTRIBUTE= "class";
    BeanDefinitionRegistry registry;
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDeFinition(Resource resource) {
        InputStream resourceAsStream = null;
        try {
            //ClassLoader cl = ClassUtils.getDefaultClassLoader();//classUtils Spring中定义的工具类 这里用来获取类加载器
            resourceAsStream =resource.getInputStream();
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement(); //获取根部标签
            Iterator<Element> iterator = rootElement.elementIterator();
            while (iterator.hasNext()){
                Element ele = iterator.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                /*this.beanDefinitionMap.put(id,bd);*/
                this.registry.registryBeanDefinition(id,bd);

            }
        } catch (IOException e){
            throw new BeanDefinitionStoreException("IOException parsing XML document");
        }catch (DocumentException e) {
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
}
