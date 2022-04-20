package com.glory.gloryUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2 {
    public static void main(String[] args) throws Exception {
      String str="{\"code\":0,\"message\":null,\"token\":null,\"data\":{\"problemDetailNum\":0,\"visitNum\":1,\"problemBNum\":0,\"problemAStoreNum\":0,\"problemStoreNum\":0,\"problemBStoreNum\":0,\"page\":{\"offset\":0,\"limit\":10,\"pageNumber\":1,\"pageSize\":10,\"rows\":[{\"createEmployeeName\":\"管理员\",\"createUserID\":92,\"data\":{\"cs100BStoreNum\":0,\"cs100ProblemNum\":0,\"cs100AProblemNum\":0,\"cs100AStoreNum\":0,\"cs100StoreNum\":0,\"cs100BProblemNum\":0},\"orgDeptID\":29,\"createEmployeeID\":194,\"problemTypeName\":\"堆头陈列资源少\",\"problemTypeCode\":\"q001\",\"enterpriseID\":2,\"problemID\":5,\"problemTypeSummary\":\"堆头陈列资源少\",\"status\":\"Y\",\"createDateTime\":\"2020-11-01 20:13:48\"},{\"createEmployeeName\":\"管理员\",\"createUserID\":92,\"data\":{\"cs100BStoreNum\":0,\"cs100ProblemNum\":0,\"cs100AProblemNum\":0,\"cs100AStoreNum\":0,\"cs100StoreNum\":0,\"cs100BProblemNum\":0},\"orgDeptID\":29,\"createEmployeeID\":194,\"problemTypeName\":\"进店品项少\",\"problemTypeCode\":\"q002\",\"enterpriseID\":2,\"problemID\":6,\"problemTypeSummary\":\"进店品项少\",\"status\":\"Y\",\"createDateTime\":\"2020-11-01 20:24:12\"},{\"createEmployeeName\":\"管理员\",\"createUserID\":92,\"data\":{\"cs100BStoreNum\":0,\"cs100ProblemNum\":0,\"cs100AProblemNum\":0,\"cs100AStoreNum\":0,\"cs100StoreNum\":0,\"cs100BProblemNum\":0},\"orgDeptID\":29,\"createEmployeeID\":194,\"problemTypeName\":\"产品价格虚高\",\"problemTypeCode\":\"q003\",\"enterpriseID\":2,\"problemID\":4,\"problemTypeSummary\":\"产品价格虚高\",\"status\":\"Y\",\"createDateTime\":\"2020-11-01 20:12:03\"},{\"createEmployeeName\":\"管理员\",\"createUserID\":6,\"data\":{\"cs100BStoreNum\":0,\"cs100ProblemNum\":0,\"cs100AProblemNum\":0,\"cs100AStoreNum\":0,\"cs100StoreNum\":0,\"cs100BProblemNum\":0},\"orgDeptID\":29,\"createEmployeeID\":124,\"problemTypeName\":\"特价数量少\",\"problemTypeCode\":\"q004\",\"enterpriseID\":2,\"problemID\":3,\"problemTypeSummary\":\"特价数量少\",\"status\":\"Y\",\"createDateTime\":\"2020-10-31 12:13:46\"},{\"createEmployeeName\":\"周伟涛\",\"createUserID\":77,\"data\":{\"cs100BStoreNum\":0,\"cs100ProblemNum\":0,\"cs100AProblemNum\":0,\"cs100AStoreNum\":0,\"cs100StoreNum\":0,\"cs100BProblemNum\":0},\"orgDeptID\":29,\"createEmployeeID\":196,\"problemTypeName\":\"存在缺断货情况\",\"problemTypeCode\":\"q005\",\"enterpriseID\":2,\"problemID\":7,\"problemTypeSummary\":\"存在缺断货情况\",\"status\":\"Y\",\"createDateTime\":\"2020-11-02 09:10:08\"},{\"createEmployeeName\":\"周伟涛\",\"createUserID\":77,\"data\":{\"cs100BStoreNum\":0,\"cs100ProblemNum\":0,\"cs100AProblemNum\":0,\"cs100AStoreNum\":0,\"cs100StoreNum\":0,\"cs100BProblemNum\":0},\"orgDeptID\":29,\"createEmployeeID\":196,\"problemTypeName\":\"存在老日期情况\",\"problemTypeCode\":\"q006\",\"enterpriseID\":2,\"problemID\":8,\"problemTypeSummary\":\"存在老日期情况\",\"status\":\"Y\",\"createDateTime\":\"2020-11-02 09:10:30\"}],\"total\":6,\"totalPages\":1,\"totalMap\":null,\"first\":1},\"storeNum\":1,\"problemBillNum\":0,\"problemANum\":0}}";
        Pattern pattern = Pattern.compile("((.*?)\"data\":(.*))");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find() && matcher.groupCount() == 3) {
            String group2 = matcher.group(2);
            String group3 = matcher.group(3);
            System.err.println(group2);
            System.err.println(group3);
        }
//        for (int i=0;i<=matcher.groupCount();i++){
//            System.err.println(i);
//            System.err.println(matcher.group(i));
//        }
    }

    public static void main2(String[] args) {
        String str = "{\"code\":0,\"message\":null,\"token\":null,\"data\":{\"aesAnywhere\":{\"salt\":\"1beda09e-8448-4297-b190-40470ecbd24f\"}}}";
        Pattern pattern = Pattern.compile("((.*)\"data\":(.*))");
        Matcher matcher = pattern.matcher(str);
        matcher.find();

        for (int i = 0; i <= matcher.groupCount(); i++) {
            System.err.println(matcher.group(i));
        }
    }
}
