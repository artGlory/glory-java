package com.glory.gloryUtils.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * json转换工具
 */
public class JacksonUtil {
    private volatile static ObjectMapper objectMapper;//线程安全对象

    /**
     * 获取ObjectMapper；单例模式；双重验证
     *
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            synchronized (JacksonUtil.class) {
                if (objectMapper == null)
                    objectMapper = new ObjectMapper();
            }
        }
        return objectMapper;
    }

    /**
     * 字符串转对象;包含（普通java对象,Map）
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonStrToObject(String jsonStr, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转字符串；包含（普通java对象,Map）
     *
     * @param object
     * @return
     */
    public static String objectToJsonStr(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return getObjectMapper().getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 转换为list
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonStrToList(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || jsonStr.trim().equals(""))
            return null;
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> lst = null;
        try {
            lst = (List<T>) getObjectMapper().readValue(jsonStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lst;
    }

    public static void main(String[] args) throws Exception {
//        JavaType javaType = getCollectionType(ArrayList.class, YourBean.class);
//        List<YourBean> lst = (List<YourBean>) mapper.readValue(jsonString, javaType);


        String str = "{\"status\":1}";
        JsonNode jsonNode = getObjectMapper().readTree(str);
        System.out.println(jsonNode.get("status"));
        String str1 = "[{\"infoCode\":{\"sdf\":\"jjlj\"},\"operateSignal\":\"\",\"operateValue\":\"\",\"paramCode\":\"\",\"typeCode\":\"sheBeiChuFa\"}]";
        jsonNode = getObjectMapper().readTree(str1);
        System.err.println(jsonNode.has("infoCode"));
    }

}
