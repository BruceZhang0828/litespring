package org.litespring.beans.factory.config;

import org.litespring.beans.BeansException;

/**
 * Created by deepbay on 2018/8/20.
 */
public interface BeanPostProcessor {
    Object beforeInitialization(Object bean, String beanName) throws BeansException;


    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
