package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypeStringValue;

public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 获取属性中 setter的 bean(dao等等...)
     * @param value
     * @return
     */
    public Object resolveValueIfNecessary(Object value) {//spring中命名方法

        if(value instanceof RuntimeBeanReference){
            RuntimeBeanReference reference = (RuntimeBeanReference)value;
            String beanName = reference.getBeanName();
            Object bean = this.beanFactory.getBean(beanName);
            return bean;
        }else if(value instanceof TypeStringValue){
            return ((TypeStringValue) value).getValue();
        }else{
            //todo 这里需要添加新的类的解析
            throw new RuntimeException("the value "+value+"has not implemented ");
        }


    }
}
