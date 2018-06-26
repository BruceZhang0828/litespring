package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String,Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);
    public void registerSingleton(String beanId, Object singletonObject) {
        Assert.notNull("'beanId' must to be null",beanId);
        Object oldObject = this.singletonObjects.get(beanId);
        if(oldObject!=null){
            throw new IllegalStateException("could not register object");
        }
        this.singletonObjects.put(beanId,singletonObject);
    }

    public Object getSingleton(String beanId) {
        return this.singletonObjects.get(beanId);
    }
}
