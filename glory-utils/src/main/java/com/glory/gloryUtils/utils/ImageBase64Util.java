package com.glory.gloryUtils.utils;

import sun.misc.BASE64Encoder;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * base64图片工具
 */
public class ImageBase64Util {
    /**
     * 从输入流获取
     *
     * @param inputStream
     * @return 异常返回NULL
     */
    public static String getFromInputStream(InputStream inputStream) {
        try {
            byte[] imgBytes = IOUtils.readFully(inputStream, -1, false);
            String imgBase64Str = "data:image/jpeg;base64," + new BASE64Encoder().encode(imgBytes).replaceAll("[\\t\\r\\n]", "");
            return imgBase64Str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
