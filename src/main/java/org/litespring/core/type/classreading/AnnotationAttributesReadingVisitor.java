package org.litespring.core.type.classreading;


import org.litespring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

import java.util.Map;

/**
 * Created by deepbay on 2018/8/16.
 */
final class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    private final String annotationType;

    private final Map<String, AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();


    public AnnotationAttributesReadingVisitor(
            String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(SpringAsmInfo.ASM_VERSION);

        this.annotationType = annotationType;
        this.attributesMap = attributesMap;

    }
    @Override
    public final void visitEnd(){
        this.attributesMap.put(this.annotationType, this.attributes);
    }

    /**
     * 读取注解中 属性 和 属性值
     * @param attributeName
     * @param attributeValue
     */
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }


}
