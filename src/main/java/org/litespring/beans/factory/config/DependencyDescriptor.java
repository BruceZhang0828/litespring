package org.litespring.beans.factory.config;

import org.litespring.util.Assert;

import java.lang.reflect.Field;

/**
 * Created by deepbay on 2018/8/20.
 */
public class DependencyDescriptor {

    private Field field;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull("Field must not be null",field);
        this.field = field;
        this.required = required;

    }
    public Class<?> getDependencyType(){
        if(this.field != null){
            return field.getType();
        }
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return this.required;
    }
}
