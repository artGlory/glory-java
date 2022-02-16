package com.glory.gloryUtils.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 合并
 */
public class BeanMergeUtil {

    /**
     * @param typeFlag 1 无论targetField是否为空，都覆盖; 2 只有targetField为空时候，覆盖
     * @return java.lang.Object  targetBean
     * @Description 对象字段覆盖；如果一个字段在sourceBean和targetBean中都存在，则使用sourceBean的值覆盖targetBean的值
     * @Param [sourceBean, targetBean，typeFlag]
     * @Author hyy
     * @Date 2022-01-14 11:21
     **/
    public static void cover(Object sourceBean, Object targetBean, int typeFlag) {
        for (Field targetField : targetBean.getClass().getDeclaredFields()) {
            String targetFieldName = targetField.getName();
            Class targetFieldClass = targetField.getType();
            targetField.setAccessible(true);
            for (Field sourceField : sourceBean.getClass().getDeclaredFields()) {
                String sourceFieldName = sourceField.getName();
                sourceField.setAccessible(true);
                Class sourceFieldClass = sourceField.getType();
                if (targetFieldName.equals(sourceFieldName)
                        && targetFieldClass.equals(sourceFieldClass)
                ) {
                    try {
                        if (sourceField.get(sourceBean) != null) {
                            if (typeFlag == 1) {//无论targetField是否为空，都覆盖
                                targetField.set(targetBean, sourceField.get(sourceBean));
                            } else if (typeFlag == 2) {//无论targetField为空，覆盖
                                if (targetField.get(sourceBean) == null) {
                                    targetField.set(targetBean, sourceField.get(sourceBean));
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
                    }

                }
            }
        }
        return;
    }

    /**
     * @param typeFlag 同一字段sourceBean和targetBean都有值。 1 取sourceBean ；2 取targetBean
     * @return T  新值
     * @throws Exception sourceBean, targetBean的uniField不相等时，抛出异常
     * @Description 同一类型的两个对象，根据uniField字段是否相等合并，如果不相等排除异常；
     * @Param [sourceBean, targetBean, uniField, typeFlag]
     * @Author hyy
     * @Date 2022-01-14 13:38
     **/
    public static <T> T merge(T sourceBean, T targetBean, Field uniField, int typeFlag) throws Exception {
        Class sourceClass = sourceBean.getClass();
        Class targetClass = targetBean.getClass();
        T result = null;
        /*
        sourceBean,targetBean 的 uniField 是否相等
         */
        try {
            Field sourceUniField = sourceClass.getDeclaredField(uniField.getName());
            sourceUniField.setAccessible(true);
            Field targetUniField = targetClass.getDeclaredField(uniField.getName());
            targetUniField.setAccessible(true);
            if (sourceUniField != null && sourceUniField != null &&
                    sourceUniField.getName().equals(targetUniField.getName())//名字相同
                    &&
                    sourceUniField.getType().equals(targetUniField.getType())//类型相同
                    &&
                    sourceUniField.get(sourceBean).equals(targetUniField.get(targetBean))//值相同
            ) {
                result = (T) sourceClass.newInstance();//合并后的对象
            } else {
                throw new IllegalArgumentException("uniField不一致");
            }
        } catch (NoSuchFieldException e) {
            throw e;
        }
        /*
        合并
         */
        for (Field targetField : targetBean.getClass().getDeclaredFields()) {
            String targetFieldName = targetField.getName();
            Class targetFieldClass = targetField.getType();
            targetField.setAccessible(true);
            for (Field sourceField : sourceBean.getClass().getDeclaredFields()) {
                String sourceFieldName = sourceField.getName();
                sourceField.setAccessible(true);
                Class sourceFieldClass = sourceField.getType();
                if (targetFieldName.equals(sourceFieldName)
                        && targetFieldClass.equals(sourceFieldClass)
                ) {
                    try {
                        Object sourceVale = sourceField.get(sourceBean);
                        Object targetVale = targetField.get(targetBean);
                        if (sourceVale != null && targetVale == null) {
                            targetField.set(result, sourceVale);
                        } else if (sourceVale == null && targetVale != null) {
                            targetField.set(result, targetVale);
                        } else if (sourceVale != null && targetVale != null) {
//                            都不为空
                            switch (typeFlag) {
                                case 1://取source值
                                    targetField.set(result, sourceVale);
                                    break;
                                case 2://取target值
                                    targetField.set(result, targetVale);
                                    break;
                                default:
                                    break;
                            }
                        } else if (sourceVale == null && targetVale == null) {
                            //都为空
                        }
                    } catch (IllegalAccessException e) {
                        throw e;
                    }
                }
            }
        }
        return result;
    }

    /**
     * @return java.util.List<T>
     * @Description 根据
     * @Param [sourceList, targetList, uniField]
     * @Author hyy
     * @Date 2022-01-14 15:16
     **/
    public static <T> List<T> mergeList(List<T> sourceList, List<T> targetList, Field uniField) {
        List<T> resultList = new ArrayList<>();
        /*
        防止污染 targetList，深度copy
         */
        targetList.forEach(t -> {
            try {
                T t1 = (T) t.getClass().newInstance();
                BeanUtils.copyProperties(t, t1);//深度copt;;待验证
                resultList.add(t1);
            } catch (InstantiationException e) {
//                e.printStackTrace();
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
            }
        });
/*
合并
 */
        for (T sourceT : sourceList) {
            T temp = null;
            for (T targetT : resultList) {
                T mergeTemp = null;
                try {
                    mergeTemp = (T) merge(sourceT, targetT, uniField, 2);
                    if (mergeTemp != null) {//如果有相同key，赋值给resultList
                        cover(mergeTemp, targetT, 1);
                    }
                    temp = mergeTemp;
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }
            if (temp == null) {//没有相同key
                resultList.add(sourceT);
            } else {//有相同key，覆盖原值
//                已经处理过了
            }
        }
        return resultList;
    }

    public static void main(String[] args) {

    }
}
