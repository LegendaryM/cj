package com.cj.gongju;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：<br/>
 *
 * @author miracle
 */
public class ThreadUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadUtils.class);

    /**
     * 功能描述: <br>
     * @param timeout 单位: ms
     * @author miracle
     */
    public static void threadSleep(long timeout) {
        threadSleep(timeout, false);
    }

    /**
     * 功能描述: <br>
     * @param timeout 单位: ms
     * @param throwException 是否继续抛异常 true:抛出异常， false: 吞掉异常
     * @author miracle
     */
    public static void threadSleep(long timeout, boolean throwException) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            LOG.error("Thread sleep exception:{}", e.getMessage(), e);
            if (throwException) {
                throw new RuntimeException(e);
            }
        }
    }
}
