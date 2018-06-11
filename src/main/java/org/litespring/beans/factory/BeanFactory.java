package org.litespring.beans.factory;

public interface BeanFactory {
    Object getBean(String petStore);

    org.litespring.beans.BeanDefinition getBeanDefinition(String petStore);
}
