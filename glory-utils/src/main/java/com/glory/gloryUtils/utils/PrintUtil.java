package com.glory.gloryUtils.utils;

import java.io.*;

public class PrintUtil {
    /**
     * 返回异常栈
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * 返回异常信息
     * @param t
     * @return
     */
    public static String getMessge(Throwable t) {
        return t.getMessage();
    }

    /**
     * 输出到文件
     * @param printStr
     * @param targetFilePath
     * @throws IOException
     */
    public static void printFileTo(String printStr,String targetFilePath) throws IOException {
        FileWriter fileWriter=new FileWriter(new File(targetFilePath));
        fileWriter.write(printStr);
        fileWriter.flush();
        fileWriter.close();
    }
}
