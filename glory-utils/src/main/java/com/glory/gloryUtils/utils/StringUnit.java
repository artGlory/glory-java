package com.glory.gloryUtils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUnit {
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 是否包含小写英文字母
     *
     * @param str
     * @return
     */
    public static boolean isContainEnglishSmallLetter(String str) {
        byte[] section = {0x61, 0x7a};
        char c[] = str.toCharArray();
        for (char c1 : c) {
            if (c1 >= section[0] && c1 <= section[1]) {
                return true;
            }
        }
        return false;
    }


    /**
     * 是否包含大写英文字母
     *
     * @param str
     * @return
     */
    public static boolean isContainEnglishUpperCaseLetter(String str) {
        byte[] section = {0x41, 0x5a};
        char c[] = str.toCharArray();
        for (char c1 : c) {
            if (c1 >= section[0] && c1 <= section[1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否包含 数字
     *
     * @param str
     * @return
     */
    public static boolean isContainNumber(String str) {
        byte[] section = {0x30, 0x39};
        char c[] = str.toCharArray();
        for (char c1 : c) {
            if (c1 >= section[0] && c1 <= section[1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * 分割字符串 ；从第一个中文处分割字符
     *
     * @param str
     * @return
     */
    public static String[] splitByFirstChinese(String str) {
        String[] strings = new String[2];
        int fitstChineseIndex = str.length();
        for (int i = 0; i < str.length(); i++) {
            boolean flag = isContainChinese("" + str.charAt(i));
            if (flag) {
                fitstChineseIndex = i;
                break;
            }
        }
        strings[0] = str.substring(0, fitstChineseIndex);
        strings[1] = str.substring(fitstChineseIndex, str.length());
        return strings;
    }

    public static void main(String[] args) {
        for (char c = 0x41; c <= 0x5a; c++) {
            String str="all_data.put(\"D test\", \"D检验\");";
            str=str.replaceAll("D",""+c);
            System.err.println(str);
        }
    }
}
