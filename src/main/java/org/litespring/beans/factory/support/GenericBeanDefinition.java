package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

public class GenericBeanDefinition implements BeanDefinition {
    private String id;

    private String beanClassName;

    private boolean singleton = true;
    private boolean prototype = false;

    private String scope = SCOPE_DEFAULT;
    private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

    private ConstructorArgument constructorArgument = new ConstructorArgument();




    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
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



    public boolean hasConstructorArgumentValus() {
        		return !this.constructorArgument.isEmpty();
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

}
