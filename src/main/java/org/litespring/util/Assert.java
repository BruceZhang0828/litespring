package org.litespring.util;

public class Assert {
    public static void notNull(String message, Object o){
        if(o==null){
            throw new  AssertionError(message);
        }
    }
}
