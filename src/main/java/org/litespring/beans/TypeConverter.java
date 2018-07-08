package org.litespring.beans;

public interface TypeConverter {
    <T> T convertIfNecessaray(Object text,Class<T> requiredType) throws TypeMissmatchException;
}
