package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * 因为与实现相关 所以放入support包中
 *
 * 获取 和 注册功能
 */
public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanId);
    void registryBeanDefinition(String beanId,BeanDefinition beanDefinition);

}
