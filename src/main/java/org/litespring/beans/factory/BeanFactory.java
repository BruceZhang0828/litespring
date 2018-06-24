package org.litespring.beans.factory;

public interface BeanFactory {
    Object getBean(String petStore);
    //被拆分出去了
    //org.litespring.beans.BeanDefinition getBeanDefinition(String petStore);
}
