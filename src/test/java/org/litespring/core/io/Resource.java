package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * spring 中的核心内容
 */
public interface Resource {
    /**
     * 获取读取流
     * @return
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException;

    /**
     *
     * @return
     */
    public String getDescription();
}
