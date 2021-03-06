package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将相关 加载xml文件的功能 抽离出去 保证类的单一职责
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory,BeanDefinitionRegistry{
    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    private ClassLoader beanClassLoader;
    public Object getBean(String beanId) {
        BeanDefinition bd = this.getBeanDefinition(beanId);
        if(bd ==null){
            throw new BeanCreationException("bean Definition does not exist");
        }
        if(bd.isSingleton()){
            Object singleton = this.getSingleton(beanId);
            if(singleton==null){
                singleton = creatBean(bd);
            }
            return  singleton;
        }
        return creatBean(bd);
    }

    private Object creatBean(BeanDefinition bd){

        //创建实例
        Object bean = instantiateBean(bd);
        //设置属性
        populateBean(bd,bean);


        return bean;
    }

    /**
     * 创建实例的方法
     * @param bd
     * @return
     */
    private Object instantiateBean(BeanDefinition bd){
        if(bd.hasConstructorArgumentValus()){
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        }else{
            String beanClassName = bd.getBeanName();
            try {
                Class<?> claz = this.getBeanClassLoader().loadClass(beanClassName);
                return claz.newInstance();
            } catch (Exception e) {
                throw  new BeanCreationException("Create bean for "+beanClassName+"fail");
            }
        }

    }

    /**
     * 设置属性
     * @param bd
     * @param bean
     */
    protected void populateBean(BeanDefinition bd, Object bean) {
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        //如果没有属性
        if(propertyValues==null||propertyValues.isEmpty()){
            return;
        }
        //设置beanfactory
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            for (PropertyValue propertyValue:propertyValues) {
                String propertyname = propertyValue.getName();
                Object originalValue = propertyValue.getValue();//获取的ref=""的值
                Object resolvedValue = resolver.resolveValueIfNecessary(originalValue);//获取的ref相关的实例类
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());//反射一个bean的信息
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();//获取bean中 属性的数组
                for (PropertyDescriptor descriptor:propertyDescriptors) {
                    if(descriptor.getName().equals(propertyname)){
                        Object converterValue = converter.convertIfNecessaray(resolvedValue, descriptor.getPropertyType());
                        descriptor.getWriteMethod().invoke(bean,converterValue);
                        break;
                    }
                }


            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class ["+bd.getBeanName()+"]");
        }
    }

    public org.litespring.beans.BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public void registryBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanId,beanDefinition);
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader!=null?this.beanClassLoader:ClassUtils.getDefaultClassLoader());
    }
}
