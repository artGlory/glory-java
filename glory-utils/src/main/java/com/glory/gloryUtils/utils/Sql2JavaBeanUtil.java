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
        String str = "  `id` bigint unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `product_code` varchar(60) NOT NULL DEFAULT '' COMMENT '关联的产品编码',\n" +
                "  `alarm_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '警告名称',\n" +
                "  `alarm_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '警告编码',\n" +
                "  `trigger` varchar(5120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '触发器列表',\n" +
                "  `behavior` varchar(5120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '执行动作列表',\n" +
                "  `alarm_status` int NOT NULL DEFAULT '0' COMMENT '0：停用；1：启动',\n" +
                "  `del_flag` int NOT NULL DEFAULT '0',\n" +
                "  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,\n";
         print2Console(str);
    }
}
