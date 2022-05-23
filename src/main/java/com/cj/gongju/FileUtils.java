package com.cj.gongju;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 功能: <br/>
 *
 * @author miracle
 */
public class FileUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 实现功能：尝试解析wav文件头
     *          字节数组中，字节数组的末尾是整形的高位，即采用byteToInt
     * @author miracle
     */
    public static byte[] readFile(File wavFile) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(wavFile);
            baos = new ByteArrayOutputStream();

            int i;
            byte[] buf = new byte[1024];
            while ((i = fis.read(buf)) != -1) {
                baos.write(buf, 0, i);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            LOG.error("read file exception:{}.", e.getMessage(), e);
            return new byte[0];
        } finally {
            StreamUtils.closeStream(baos);
            StreamUtils.closeStream(fis);
        }
    }
}
