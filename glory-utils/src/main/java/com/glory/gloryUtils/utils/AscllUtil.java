package com.glory.gloryUtils.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;

public class AscllUtil {

    public static String bytesToAscii(byte[] bytes) {
        try {
            return new String(bytes, "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Administrator\\Desktop\\1556418930.max");
        File file2 = new File("C:\\Users\\Administrator\\Desktop\\1556418930.max.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileWriter fileWriter = new FileWriter(file2);
            byte[] bytes = new byte[100];
            int flag = fileInputStream.read(bytes);
            while (flag != -1) {
                fileWriter.append(bytesToAscii(bytes));
                System.err.println(bytesToAscii(bytes));
                flag = fileInputStream.read(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}