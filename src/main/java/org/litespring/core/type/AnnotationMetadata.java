package org.litespring.core.type;

import org.litespring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * Created by deepbay on 2018/8/17.
 */

/**
 * 这个接口是用来封装 注解中的属性 and 值
 */
public interface AnnotationMetadata extends ClassMetadata{
    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    public AnnotationAttributes getAnnotationAttributes(String annotationType);
}
