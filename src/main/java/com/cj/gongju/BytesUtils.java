package com.cj.gongju;

/**
 * 功能: <br/>
 *
 * @author miracle
 */
public class BytesUtils {
    /**
     * 实现功能：字节数组转整形
     *          小端字节序
     * @author miracle
     */
    public static int bytesToInt(byte[] buf) {
        return (buf[0] & 0xFF) | ((buf[1] & 0xFF) << 8) | ((buf[2] & 0xFF) << 16) | ((buf[3] & 0xFF) << 24);
    }

    /**
     * 实现功能：字节数组转短整形
     *          小端字节序
     * @author miracle
     */
    public static int bytesToShort(byte[] buf) {
        return (buf[0] & 0xFF) | ((buf[1] & 0xFF) << 8);
    }

    /**
     * 实现功能：字节数组转整形
     *         数组前面是整形高位，与intToBytes2对应，大端字节序
     * @author miracle
     */
    public static int bytesToInt2(byte[] buf) {
        return (buf[3] & 0xFF) | ((buf[2] & 0xFF) << 8) | ((buf[1] & 0xFF) << 16) | ((buf[0] & 0xFF) << 24);
    }

    /**
     * 实现功能：整型转字节数组
     *      整形高位放数组尾
     * @author miracle
     */
    public static byte[] intToBytes(int i) {
        byte[] buf = new byte[4];
        buf[0] = (byte) (i & 0xFF);
        buf[1] = (byte) ((i >> 8) & 0xFF);
        buf[2] = (byte) ((i >> 16) & 0xFF);
        buf[3] = (byte) ((i >> 24) & 0xFF);
        return buf;
    }


    /**
     * 实现功能：整型转字节数组
     *       整形高位放数组头，与bytesToInt2对应
     * @author miracle
     */
    public static byte[] intToBytes2(int i) {
        byte[] buf = new byte[4];
        buf[3] = (byte) (i & 0xFF);
        buf[2] = (byte) ((i >> 8) & 0xFF);
        buf[1] = (byte) ((i >> 16) & 0xFF);
        buf[0] = (byte) ((i >> 24) & 0xFF);
        return buf;
    }
}
