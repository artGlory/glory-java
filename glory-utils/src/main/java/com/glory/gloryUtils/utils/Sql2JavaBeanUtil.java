package com.glory.gloryUtils.utils;


public class Sql2JavaBeanUtil {
    public static void print2Console(String str) {
        String[] sqlLines = str.split(",\n");
        for (String line : sqlLines) {
            if (line != null && !"".equals(line.trim())) {
                String lineTemp = line.trim()
                        .replaceAll("`", "")
                        .replaceAll("'", "")
                        .replaceAll("\\n", "");
                String keyStr = lineTemp.split("\\s")[0];
                {
                    /*
                    首字母小写
                     */
                    keyStr = keyStr.substring(0, 1).toLowerCase() + keyStr.substring(1);
                }

                String keyTypeStr = lineTemp.split("\\s")[1];
                {
                    if (keyTypeStr.startsWith("int")) {
                        keyTypeStr = "Integer";
                    } else if (keyTypeStr.startsWith("long")) {
                        keyTypeStr = "Long";
                    } else if (keyTypeStr.startsWith("double")||keyTypeStr.startsWith("decimal")) {
                        keyTypeStr = "Double";
                    } else if (keyTypeStr.startsWith("varchar")) {
                        keyTypeStr = "String";
                    } else if (keyTypeStr.startsWith("char")) {
                        keyTypeStr = "String";
                    } else if (keyTypeStr.startsWith("datetime")) {
                        keyTypeStr = "Date";
                    }else if (keyTypeStr.startsWith("bigint")) {
                        keyTypeStr = "Integer";
                    }else if (keyTypeStr.startsWith("date")) {
                        keyTypeStr = "Date";
                    }
                }

                String commentStr = "";
                {
                    if (lineTemp.contains("COMMENT")) {
                        commentStr = lineTemp.split("COMMENT")[1];
                    }
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("    /**\n");
                stringBuffer.append("     *" + commentStr + "\n");
                stringBuffer.append("     */\n");
                stringBuffer.append("     private" + " " + keyTypeStr + " " + keyStr + ";");
                System.err.println(stringBuffer.toString());
            }
        }
    }

    public static void main(String[] args) {
        String str = "  `PlanCstID` int NOT NULL AUTO_INCREMENT COMMENT '支持客户ID',\n" +
                "  `EnterpriseID` int NOT NULL COMMENT '企业ID',\n" +
                "  `CurrentUnitID` int NOT NULL COMMENT '往来单位ID',\n" +
                "  `PlanID` int NOT NULL COMMENT '方案ID',\n" +
                "  `SuppStatus` varchar(45) COLLATE utf8mb4_general_ci NOT NULL COMMENT '支持状态  B=启用 E=停用',\n" +
                "  `StartAccountPeriod` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '期间',\n" +
                "  `EndAccountPeriod` char(6) COLLATE utf8mb4_general_ci NOT NULL COMMENT '结束期间',\n" +
                "  `Remark` varchar(120) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',\n" +
                "  `CreateDateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `CreateEmployeeID` int NOT NULL COMMENT '创建员工ID',\n" +
                "  `CreateUserID` int NOT NULL COMMENT '创建用户ID',\n" +
                "  `CreateUserName` varchar(45) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建用户名称',\n" +
                "  `LastUpdateDateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',\n" +
                "  `LastUpdateEmployeeID` int NOT NULL COMMENT '最后更新员工ID',\n" +
                "  `LastUpdateEmployeeName` varchar(60) COLLATE utf8mb4_general_ci NOT NULL COMMENT '最后更新用户',\n" +
                "  `LastUpdateUserID` int NOT NULL COMMENT '最后更新用户ID',\n" +
                "  `LastUpdateUserName` varchar(60) COLLATE utf8mb4_general_ci NOT NULL COMMENT '最后更新用户名称',\n";
         print2Console(str);
    }
}
