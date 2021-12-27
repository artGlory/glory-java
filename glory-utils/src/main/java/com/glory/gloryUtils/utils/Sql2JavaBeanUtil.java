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
                    } else if (keyTypeStr.startsWith("double")) {
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
        String str = " `HadTypeID` int NOT NULL AUTO_INCREMENT COMMENT '硬件分类ID',\n" +
                "  `EnterpriseID` int NOT NULL COMMENT '企业ID',\n" +
                "  `HadTypeCode` varchar(45) COLLATE utf8mb4_general_ci NOT NULL COMMENT '硬件类别编码',\n" +
                "  `HadTypeName` varchar(60) COLLATE utf8mb4_general_ci NOT NULL COMMENT '硬件类别名称',\n" +
                "  `SupervisionCycle` decimal(20,10) NOT NULL COMMENT '监管期',\n" +
                "  `IsEnabled` char(1) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'Y' COMMENT '是否可用',\n" +
                "  `Remark` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',\n" +
                "  `CreateUserID` int NOT NULL COMMENT '创建用户ID',\n" +
                "  `CreateUserName` varchar(45) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建用户名称',\n" +
                "  `CreateDateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "  `LastUpdateDateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',\n" +
                "  PRIMARY KEY (`HadTypeID`),";
         print2Console(str);
    }
}
