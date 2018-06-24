package org.litespring.core.io;

import org.litespring.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements Resource {
    private String path;
    private File file;

    /**
     * 构造方法 创建一个
     * @param path
     */
    public FileSystemResource(String path) {
        Assert.notNull("path must not be null",path);
        this.path = path;
        this.file = new File(path);
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {

        return new FileInputStream(this.file);
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return "file ["+this.path+"]";
    }


}
