package org.litespring.beans.factory.xml;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypeStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * 用来保证 DefaultBeanFactory的单一职责
 */
public class XmlBeanDefinitionReader {
    public static final String  ID_ATTRIBUTE= "id";
    public static final String  CLASS_ATTRIBUTE= "class";
    public static final String  SCOPE_ATTRIBUTE= "scope";
    public static final String  PROPERTY_ELEMENT= "property";
    public static final String  REF_ATTRIBUTE= "ref";
    public static final String  VALUE_ATTRIBUTE= "value";
    public static final String  NAME_ATTRIBUTE= "name";
    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    public static final String TYPE_ATTRIBUTE = "type";

    protected final Log logger = LogFactory.getLog(getClass());
    BeanDefinitionRegistry registry;
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 加载xml文件
     * @param resource
     */
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
                if(ele.attributeValue(SCOPE_ATTRIBUTE)!=null){
                    bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                }
                /*this.beanDefinitionMap.put(id,bd);*/
                //进行解析属性
                parsePropertyElement(ele,bd);
                //解析构造注入的属性
                parseConstructorArgElements(ele,bd);
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

    /**
     *
     * @param ele
     * @param bd
     */
    private void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
        Iterator iterator = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iterator.hasNext()) {
            Element ele = (Element) iterator.next();
            parseConstructorArgElement(ele, bd);
        }
    }

    private void parseConstructorArgElement(Element ele, BeanDefinition bd) {
        String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
        Object value = parsePropertyValue(ele, bd, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);

        if(StringUtils.hasLenth(typeAttr)){
            valueHolder.setType(typeAttr);
        }
        if(StringUtils.hasLenth(nameAttr)){
            valueHolder.setName(nameAttr);
        }
        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }

    /**
     *
     */
    public void parsePropertyElement(Element beanelement,BeanDefinition bd){
        Iterator iterator = beanelement.elementIterator(PROPERTY_ELEMENT);
        while(iterator.hasNext()){
            Element proElement = (Element)iterator.next();
            String proPertyName = proElement.attributeValue(NAME_ATTRIBUTE);
            if(StringUtils.hasLenth(proPertyName)){
                logger.fatal("Tab 'property' must have 'name' attribute");
            }
            Object  val = parsePropertyValue(proElement,bd,proPertyName);
            PropertyValue propertyValue = new PropertyValue(proPertyName,val);
            //
            bd.getPropertyValues().add(propertyValue);
        }
    }

    /**
     * 返回解析中的ref 和 value属性
     * @param proElement
     * @param bd
     * @param proPertyName
     * @return
     */
    private Object parsePropertyValue(Element proElement, BeanDefinition bd, String proPertyName) {
        String elementName  = (proPertyName!=null)?
                "<property> element for property '"+proPertyName+"'" :
                "<constructor-arg> element";
        boolean hasRefAttribute = (proElement.attribute(REF_ATTRIBUTE)!=null);
        boolean hasValueAttribute = (proElement.attribute(VALUE_ATTRIBUTE)!=null);
        if(hasRefAttribute){
            String refName = proElement.attributeValue(REF_ATTRIBUTE);
            if(!StringUtils.hasText(refName)){
                logger.error(elementName+"contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        }else if(hasValueAttribute){
            TypeStringValue typeStringValue = new TypeStringValue(proElement.attributeValue(VALUE_ATTRIBUTE));
            return typeStringValue;
        }else{
            //目前只支持两种 ref 和 value 其他的属性暂不支持
            throw new RuntimeException(elementName+"must specify a ref or value");
        }
    }
}
