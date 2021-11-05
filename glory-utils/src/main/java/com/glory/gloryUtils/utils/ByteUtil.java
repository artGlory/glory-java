package com.glory.gloryUtils.utils;

import java.io.IOException;

public class ByteUtil {

    public static void main(String[] args) throws IOException {
        byte[] bytes = {0x0F, 0x1F, 0x2F, 0x3F, 0x4F, 0x5F, 0x6F};
        String str = bytesToHex(bytes);
        System.err.println(str);
        String str2="ewogICJ0ZW1wZXJhdHVyZTIiOiAxLAogICJ0ZW1wZXJhdHVyZSI6IDEKfQ==";
        byte[] bytes1=Base64Tool.getBytesFromBase64Str(str2);
        System.err.println(AscllUtil.bytesToAscii(bytes1));
    }

    /**
     * byte[] 转换 为十六进制字符串
     *
     * @param bytes {0x0F, 0x1F, 0x2F, 0x3F, 0x4F, 0x5F, 0x6F}
     * @return 0f1f2f3f4f5f6f
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    /**
     * 十六进制字符串 转换为 byte[]
     *
     * @param str 0f1f2f3f4f5f6f
     * @return {0x0F, 0x1F, 0x2F, 0x3F, 0x4F, 0x5F, 0x6F};
     */
    public static byte[] hextoBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }
}
