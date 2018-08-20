package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * Created by deepbay on 2018/8/20.
 */
public interface AutowireCapableBeanFactory  extends BeanFactory{
    public Object resolveDependency(DependencyDescriptor descriptor);
}
