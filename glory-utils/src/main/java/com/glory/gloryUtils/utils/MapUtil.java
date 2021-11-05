package com.glory.gloryUtils.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    /**
     * @param map
     * @return 返回跟在url后面的请求参数 documentNumber=&documentType=&vendorNumber=&store=&taSlipNumber=&mailboxId=19189
     */
    public static String toRequestGetParam(Map map) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object key : map.keySet()) {
            if (stringBuffer.length() > 0) {
                stringBuffer.append("&");
            }
            stringBuffer.append(key);
            stringBuffer.append("=");
            Object value = map.get(key);
            if (value != null) {
                stringBuffer.append(value);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * @param str 订单编号：1287485	 执行情况：部分收货	核算方式：经销
     * @return
     */
    public static Map<String, String> formatToMap(String str) {
        Map<String, String> map = new HashMap<>();
        String[] strings = str.split("\t|\r|\n");
        for (String strTemp : strings) {
            if (strTemp != null && "".equals(strTemp.trim()) == false) {
                String key = strTemp.split(":")[0].trim();
                String value = "";
                if (strTemp.split(":").length > 1) {
                    value = strTemp.split(":")[1].trim();
                }
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * @param str N:否；Y:是；n:否；y:是；
     * @return
     */
    public static Map<String, String> formatToMap2(String str) {
        Map<String, String> map = new HashMap<>();
        String[] strings = str.split(";");
        for (String strTemp : strings) {
            if (strTemp != null && "".equals(strTemp.trim()) == false) {
                String key = strTemp.split(":")[0].trim();
                String value = "";
                if (strTemp.split(":").length > 1) {
                    value = strTemp.split(":")[1].trim();
                }
                map.put(key, value);
            }
        }
        return map;
    }
}
