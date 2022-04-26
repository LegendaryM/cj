package com.cj.gongju;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能: 可关闭的工具类<br/>
 * 调用方式：
 * <pre>{@code
 *       InputStream inputStream = ...
 *       StreamUtils.closeStream(inputStream);
 *  }</pre>
 * @author miracle
 */
public class StreamUtils {
    private static final Logger LOG = LoggerFactory.getLogger(StreamUtils.class);

    /**
     * 关闭流
     * @param autoCloseable 关闭对象
     * @param throwException 是否继续抛异常 true:抛出异常， false: 吞掉异常
     * @throws RuntimeException
     */
    public static void closeStream(AutoCloseable autoCloseable, boolean throwException) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                LOG.error("Stream close exception:{}!", e.getMessage(), e);
                if (throwException) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
