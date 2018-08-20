package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {
    private String id;

    private String beanClassName;
    private Class<?> beanClass;

    private boolean singleton = true;
    private boolean prototype = false;

    private String scope = SCOPE_DEFAULT;
    private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

    private ConstructorArgument constructorArgument = new ConstructorArgument();




    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public GenericBeanDefinition() {

    }

    /**
     *
     * @return
     */
    public boolean isSingleton() {
        return this.singleton;
    }

    /**
     * 返回 property的list
     * @return
     */
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }

    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }

    public String getID() {
        		return this.id;
        	}
    public void setId(String id) {
        this.id = id;
    }



    public boolean hasConstructorArgumentValus() {
        		return !this.constructorArgument.isEmpty();
        	}

    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        String className = getBeanName();
        if (className == null) {
            return null;
        }
        Class<?> resolvedClass = classLoader.loadClass(className);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    public Class<?> getBeanClass() throws IllegalStateException {
        if(this.beanClassName == null){
            throw new IllegalStateException(
                    "Bean class name [" + this.getBeanName() + "] has not been resolved into an actual Class");
        }
        return this.beanClass;

    }

    public boolean hasBeanClass() {
        return this.beanClass != null;
    }

    /**
     * 是否为 多列
     * @return
     */
    public boolean isPrototype() {
        return this.prototype;
    }

    /**
     * 获取 scope
     * @return
     */
    public String getScope() {
        return this.scope;
    }

    /**
     * 设置 scope
     * @param scope
     */
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope)||SCOPE_DEFAULT.equals(scope);
        this.singleton = SCOPE_PROTOTYPE.equals(scope);

    }

    /**
     * 获取beanname
     * @return
     */
    public String getBeanName() {
        return this.beanClassName;
    }

    /**
     * set beanClassName
     * @param beanClassName
     */
    public void setBeanName(String beanClassName){
        this.beanClassName = beanClassName;
    }


}
