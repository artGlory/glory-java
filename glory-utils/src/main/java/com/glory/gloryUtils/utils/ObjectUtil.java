package com.glory.gloryUtils.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectUtil {
    /**
     * 获取对象序列化的二进制
     *
     * @param object
     * @return
     */
    public static byte[] serializeObj2bytes(Object object) throws IOException {
        if (object == null) return null;
        byte[] result = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw e;
        }
        return result;
    }

    /**
     * 根据序列化的字节数组 反序列化得到对象
     *
     * @param bytes
     * @return
     */
    public static Object bytes2deserializeObj(byte[] bytes) throws IOException, ClassNotFoundException {
        if (bytes == null) return null;
        Object object = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
        } catch (Exception e) {
            throw e;
        }
        return object;
    }

    /**
     * 获取对象系列化字节数组的16进制字符串
     *
     * @param object
     * @return
     */
    public static String serialize2Hex(Object object) throws IOException {
        return HexUtil.toHex(serializeObj2bytes(object));
    }

    /**
     * 根据对象序列化的16禁止字符串，反向得到对象
     *
     * @param hex
     * @return
     */
    public static Object hex2deserializeObj(String hex) throws IOException, ClassNotFoundException {
        byte[] bytes = HexUtil.fromHex(hex);
        if (bytes == null) return null;
        return bytes2deserializeObj(bytes);
    }

    public static String toString(Object object) {
        return String.valueOf(object);
    }

    public static Number toNumber(Object object) {
        if (object != null && object instanceof Number) {
            return (Number) object;
        }
        return 0;
    }

    public static double toDouble(Object object) {
        if (object != null && object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        return 0;
    }

    public static int toInt(Object object) {
        if (object != null && object instanceof Number) {
            return ((Number) object).intValue();
        }
        return 0;
    }

    public static long toLong(Object object) {
        if (object != null && object instanceof Number) {
            return ((Number) object).longValue();
        }
        return 0;
    }

    public static float toFloat(Object object) {
        if (object != null && object instanceof Number) {
            return ((Number) object).floatValue();
        }
        return 0;
    }

    public static short toShort(Object object) {
        if (object != null && object instanceof Number) {
            return ((Number) object).shortValue();
        }
        return 0;
    }

    public static <T> List<T> as(List<?> list, Class<T> clazz) {
        List<T> reslut = new ArrayList<T>();
        for (Object o : list) {
            if (clazz.isInstance(o)) {
                reslut.add(clazz.cast(o));
            }
        }
        return reslut;
    }

    public static <T> T cast(Object o, Class<T> clazz) {
        if (clazz.isInstance(o)) {
            return clazz.cast(o);
        }
        return null;
    }

    public static Integer[] toArray(List<Integer> list) {
        if (list != null) {
            int length = list.size();
            Integer[] result = new Integer[length];
            for (int i = 0; i < length; i++) {
                result[i] = list.get(i);
            }
            return result;
        }
        return null;
    }


}
