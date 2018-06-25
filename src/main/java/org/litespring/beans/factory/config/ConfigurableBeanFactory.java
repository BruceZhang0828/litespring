package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * 用来 将 classloader进行处理
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void setBeanClassLoader(ClassLoader classLoader);
    ClassLoader getBeanClassLoader();
}
