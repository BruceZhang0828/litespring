package org.litespring.core.io;

import org.litespring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {
    private String path;//
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
            this(path,(ClassLoader) null);
    }

    public ClassPathResource(String path,ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader !=null ?classLoader: ClassUtils.getDefaultClassLoader());
    }


    public InputStream getInputStream() throws IOException {
        InputStream inputStream  = this.classLoader.getResourceAsStream(this.path);
        if(inputStream==null){
            throw new FileNotFoundException(path+"cannot be open");
        }
        return inputStream;
    }

    public String getDescription() {
        return "file:"+path;
    }


}
