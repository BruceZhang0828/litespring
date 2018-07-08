package org.litespring.beans;

import lombok.Data;

@Data
public class PropertyValue {

    private final String name;
    private final Object value;
    private boolean converted = false;
    private Object convertedValue;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
