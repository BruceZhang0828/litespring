package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

import java.util.List;

/**
 * 用来 将 classloader进行处理
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    void setBeanClassLoader(ClassLoader classLoader);
    ClassLoader getBeanClassLoader();

    /**
     * 对autoweried 进行注入
     * @param postProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor postProcessor);
    List<BeanPostProcessor> getBeanPostProcessors();
}
