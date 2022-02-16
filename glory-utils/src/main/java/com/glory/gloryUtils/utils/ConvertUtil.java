package com.glory.gloryUtils.utils;

/**
 * @Description 转换工具
 * @Author hyy
 * @Date 2022-01-21 9:09
 **/
public class ConvertUtil {
    /**
     * @return void
     * @Description
     * @Param [paramStr]
     * @Author hyy
     * @Date 2022-01-21 9:10
     * <p>
     * params.orgID = orgId;
     * params.gatherLevel = $scope.search.level;
     * params.hardwareTypeID = $scope.search.hadTypeID;
     * params.hardwareID = $scope.search.hardwareID;
     **/
    public static void param2export(String paramStr) {
        String exportTemp = "if (!!$scope.search.XXX) {params += '&YYY=' + $scope.search.ZZZ;}";

        String[] paramArr = paramStr.split(";");
        for (String param : paramArr) {
            if (param.contains("params.") && param.contains("=")) {
                String exStr = param
                        .split("=")[0]
                        .replace("params.", "")
                        .trim();

                String paStr = param
                        .split("=")[1]
                        .replaceAll("\\$scope.search.", "")
                        .replace(";", "")
                        .trim();


                String result = exportTemp.replaceAll("YYY", exStr);
                if (param.contains("$scope.search.") == false) {
                    result = result.replaceAll("\\$scope.search.XXX", paStr);
                    result = result.replaceAll("\\$scope.search.ZZZ", paStr);
                } else {
                    result = result.replaceAll("XXX", paStr);
                    result = result.replaceAll("ZZZ", paStr);
                }
                System.err.println(result);
            } else {
                System.err.println("====wrong====" + param);
            }
        }
    }

    public static void main(String[] args) {
        String str = "" +
                "            params.orgID = orgId;\n" +
                "            params.gatherLevel = $scope.search.level;\n" +
                "            params.hardwareTypeID = $scope.search.hadTypeID;\n" +
                "            params.hardwareID = $scope.search.hardwareID;\n" +
                "            params.hardwareName = $scope.search.hardwareName;\n" +
                "            params.brandIDList = $scope.search.brandIDList;\n" +
                "            params.brandNames = $scope.search.brandNames;\n" +
                "            params.hardwareStatus = $scope.search.hardwareStatus;\n" +
                "            params.roleCode = $scope.search.roleCode;\n" +
                "            params.roleName = $scope.search.roleName;\n" +
                "            params.workStatusID = $scope.search.workStatusID;\n" +
                "            params.employeeNames = $scope.search.employeeName;\n" +
                "            params.employeeIDList = $scope.search.employeeId;\n" +
                "            params.currentTypeID = $scope.search.customerTypeId;\n" +
                "            params.currentTypeName = $scope.search.customerTypeName;\n" +
                "            params.bookStatus = $scope.search.bookStatus;\n" +
                "            params.deptID = $scope.search.deptID;\n" +
                "            params.currentUnitID = $scope.search.currentUnitID;\n" +
                "            params.bookInStartDate = $scope.search.startDate;\n" +
                "            params.bookInEndDate = $scope.search.endDate;\n" +
                "            params.tranStartDate = $scope.search.startDate_3;\n" +
                "            params.tranEndDate = $scope.search.endDate_3;\n" +
                "            params.checkStartDate = $scope.search.startDate_2;\n" +
                "            params.checkEndDate = $scope.search.endDate_2;";
        param2export(str);
    }
}
