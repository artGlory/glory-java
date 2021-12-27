package com.glory.gloryUtils.utils.excel;

import com.glory.gloryUtils.utils.PathUtil;
import com.glory.gloryUtils.utils.excel.poi.PoiExcelUtil;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description excel导入导出工具，支持复杂表头
 * @Author hyy
 * @Date 2021/7/8 15:53
 **/
public class ExcelClassUtil {
    private static final Properties excel_properties;

    static {
        excel_properties = new Properties();
        excel_properties.put("workbook.outType", "xlsx");//导出文件类型; xls ,xlsx
        excel_properties.put("workbook.sheet.rowSize", 5000);//单个sheet最大多少条数据
    }

    public static void main(String[] args) {
        int flag = 2;//1导出；2导入
        if (flag == 1) {
            List<TestBean> testBeanList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                testBeanList.add(TestBean.builder()
                        .group("组" + i)
                        .cloumn("列")
                        .dateformat(new Date())
                        .headTitle(i)
                        .commentText("模拟批注")
                        .changeTo("N")
                        .url("https://poi.apache.org/")
                        .build());
            }
            String outFilePath = PathUtil.getRandomDesktopFilePath(".xlsx");
            try {
                exportToStream(TestBean.class, testBeanList, null, null, new FileOutputStream(outFilePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == 2) {

            String fileName = PathUtil.getDesktopPathAndSeparatorChar() + "6370ec9a-a8b8-477c-adaa-5e81efc98ed2.xls";
            fileName = PathUtil.getDesktopPathAndSeparatorChar() + "b5c7b649-7314-448e-a924-22fc4f27962a.xlsx";

            List<List<Cell>> listList = null;
            try {
                listList = importFromStream2List(new FileInputStream(fileName), 2, 6);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (List<Cell> cellList : listList) {
                for (Cell cell : cellList) {
                    System.err.println(cell.getCellType() + " " + cell);
//                    System.err.println(cell);
                }
            }
        }
    }

    /**
     * @param inputStream
     * @param headRowNum  表头行数 1.2.3.4...
     * @param colNum      总列数  1.2.3.4...
     * @return Map<sheetName, Map < 行, Map < 列, Cell>>>
     * @Description
     * @Param [inputStream, headRowNum, colNum]
     * @Author hyy
     * @Date 2021-12-27 14:10
     **/
    private static Map<String, Map<Integer, Map<Integer, Cell>>> importFromStream2Map(InputStream inputStream, int headRowNum, int colNum) {
        Map<String, Map<Integer, Map<Integer, Cell>>> resultMap = new HashMap<>();
        /*
        获取workbook
         */
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);//2007+
        } catch (Exception e) {
            try {
                workbook = new HSSFWorkbook(inputStream);  //2003-
            } catch (Exception ex) {
            }
        }
        if (workbook == null) throw new IllegalArgumentException("xls文件解析错误 ， xlsx文件解析错误， 请确认文件");
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheetAt(sheetIndex);//获取sheet
            Map<Integer, Map<Integer, Cell>> sheetDataMap = new HashMap<>();
            for (int dataRow = headRowNum; dataRow <= sheet.getLastRowNum(); dataRow++) {
                Row row = sheet.getRow(dataRow);
                if (row == null) continue;
                Map<Integer, Cell> rowDataMap = new HashMap<>();
                for (int col = 0; col < colNum; col++) {
                    Cell cell = row.getCell(col);
                    rowDataMap.put(col, cell);
                    sheetDataMap.put(dataRow, rowDataMap);
                }
            }
            resultMap.put(sheet.getSheetName(), sheetDataMap);
        }
        return resultMap;
    }

    /**
     * @param headRowNum 表头行数
     * @param colNum     列行数
     * @return java.util.List<java.util.List < org.apache.poi.ss.usermodel.Cell>>
     * @Description
     * @Param [inputStream, headRowNum, colNum]
     * @Author hyy
     * @Date 2021-12-27 11:57
     **/
    public static List<List<Cell>> importFromStream2List(InputStream inputStream, int headRowNum, int colNum) {
        Map<String, Map<Integer, Map<Integer, Cell>>> resultMap = importFromStream2Map(inputStream, headRowNum, colNum);
        List<List<Cell>> listList = new LinkedList<>();
        for (String sheetName : resultMap.keySet()) {
            Map<Integer, Map<Integer, Cell>> sheetMap = resultMap.get(sheetName);
            for (Integer rowNum : sheetMap.keySet()) {
                Map<Integer, Cell> rowMap = new TreeMap<>(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o1 - o2;
                    }
                });
                rowMap.putAll(sheetMap.get(rowNum));
                List<Cell> cellList = new ArrayList<>();
                for (Integer col : rowMap.keySet()) {
                    Cell cell = rowMap.get(col);
                    cellList.add(cell);
                }
                listList.add(cellList);
            }
        }
        return listList;
    }

    /**
     * @param groupNums 显示特定组；groupNums={1,2}显示1组和2组；groupNums=null显示所有组
     * @return void
     * @Description 导出到流
     * @Param [glass, list, groupNums, sheetName, outputStream]
     * @Author hyy
     * @Date 2021/7/7 16:14
     **/
    public static void exportToStream(Class glass, List list, Integer[] groupNums, String sheetName, OutputStream outputStream) {
        List<ExcelField> excelFieldList = new ArrayList<>();
        /*
        显示特定组；groupNums={1,2}显示1组和2组；groupNums=null显示所有组
         */
        Field[] fields = glass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelField.class)) {
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                Integer[] groupNum = Arrays.stream(excelField.groupNum()).boxed().toArray(Integer[]::new);
                if (groupNums == null) {  //全部显示
                    excelFieldList.add(excelField);
                } else {//显示特定组
                    boolean noSimilar = Collections.disjoint(Arrays.asList(groupNum), Arrays.asList(groupNums));
                    if (noSimilar == false) {//没有相同的元素==false
                        excelFieldList.add(excelField);
                    } else {
                        //不显示的组
                    }
                }
            }
        }
        Collections.sort(excelFieldList, new Comparator<ExcelField>() {
            @Override
            public int compare(ExcelField o1, ExcelField o2) {
                return o1.columnOrder() - o2.columnOrder();
            }
        });
        /*
        动态计算出表头的合并参数
         */
        int maxDeep = 1; //复杂表头最大深度
        for (int i = 0; i < excelFieldList.size(); i++) {
            int length = excelFieldList.get(i).headTitle().length;
            if (length > maxDeep) {
                maxDeep = length;
            }
        }
        /**
         * a
         * b	c
         * d	e	f
         * d
         */
        String[][] headTitleInfo = new String[excelFieldList.size()][maxDeep];
        for (int i = 0; i < excelFieldList.size(); i++) {
            for (int j = 0; j < maxDeep; j++) {
                if (j < excelFieldList.get(i).headTitle().length)
                    headTitleInfo[i][j] = excelFieldList.get(i).headTitle()[j];
            }
        }
        /**
         * a			 =>  	a	a	a
         * b	c		 =>  	b	c	c
         * d	e	f	 =>  	d	e	f
         * d			 =>  	d	d	d
         */
        for (int i = 0; i < excelFieldList.size(); i++) {
            for (int j = 0; j < maxDeep; j++) {
                if (headTitleInfo[i][j] == null) {
                    headTitleInfo[i][j] = headTitleInfo[i][j - 1];
                }
            }
        }
        Workbook workbook;
        if ("xlsx".equals(excel_properties.get("workbook.outType"))) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        int rowSize = (int) excel_properties.get("workbook.sheet.rowSize");//单个sheet最大多少条数据
        int maxSheetPageNumber = list.size() / rowSize + 1;
        if (list.size() != 0 && list.size() % rowSize == 0) maxSheetPageNumber--;
        for (int i = 0; i < maxSheetPageNumber; i++) {
            Sheet sheet = sheetName == null ? PoiExcelUtil.createSheet(workbook) : PoiExcelUtil.createSheet(workbook, sheetName);
            int fromIndex = i * rowSize;
            int toIndex = (i + 1) * rowSize;
            toIndex = toIndex > list.size() ? list.size() : toIndex;
            List sheetList = list.subList(fromIndex, toIndex);
            /*
             表头
            */
            for (int rowNum = 0; rowNum < maxDeep; rowNum++) {
                Row row = sheet.createRow(rowNum);
                for (int colNum = 0; colNum < excelFieldList.size(); colNum++) {
                    ExcelField excelField = excelFieldList.get(colNum);
                    CellStyle headCellStyle = workbook.createCellStyle();
                    PoiExcelUtil.setCellFontAlignment(headCellStyle, excelField.headFontHorizontalAlignment(), excelField.headFontVerticalAlignment());//水平竖直位置
                    PoiExcelUtil.setCellFont(workbook, headCellStyle, excelField.headFontBold(), excelField.headFontName(), excelField.headFontColor());//字体
                    PoiExcelUtil.setCellBackgroundColor(headCellStyle, excelField.headBgColor());//背景颜色
                    PoiExcelUtil.setCellBorder(headCellStyle,
                            excelField.headBorder()[0], excelField.headBorder()[1], excelField.headBorder()[2], excelField.headBorder()[3],
                            excelField.headBorderColor()[0], excelField.headBorderColor()[1], excelField.headBorderColor()[2], excelField.headBorderColor()[3]
                    );//边框
                    //setCellComment 注解
                    PoiExcelUtil.setCellDataFormatStyle(workbook, headCellStyle, "@");//数据格式
                    PoiExcelUtil.setCellWordWrap(headCellStyle, excelField.isWordWrap());//设置自动换行
                    //setCellHyperLink 设置cell链接
                    PoiExcelUtil.setColumnWidth(sheet, colNum, excelField.columnWidth());//列宽
                    //设置内容
                    Cell cell = row.createCell(colNum);
                    cell.setCellStyle(headCellStyle);
                    cell.setCellValue(headTitleInfo[colNum][rowNum]);
                }
            }
            /*
            表头合并单元格 - 水平合并
             */
            for (int rowNum = 0; rowNum < maxDeep; rowNum++) {
                for (int colNum = 0; colNum < excelFieldList.size(); colNum = colNum) {
                    Row row = sheet.getRow(rowNum);
                    int sameContentCol = colNum;
                    Cell cell1 = row.getCell(colNum);
                    Cell cell2 = row.getCell(sameContentCol);
                    while (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
                        sameContentCol++;
                        cell2 = row.getCell(sameContentCol);
                        if (cell2 == null) break;
                    }
                    if (colNum != sameContentCol - 1) {
                        PoiExcelUtil.createMergeRegion(sheet, rowNum, rowNum, cell1.getColumnIndex(), sameContentCol - 1);
                        CellStyle cellStyle = cell1.getCellStyle();
                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                        cell1.setCellStyle(cellStyle);
                    }
                    colNum = sameContentCol;
                }
            }
            /*
            表头合并单元格 - 竖直合并
             */
            for (int colNum = 0; colNum < excelFieldList.size(); colNum++) {
                for (int rowNum = 0; rowNum < maxDeep; rowNum = rowNum) {
                    int sameContentRow = rowNum;
                    Cell cell1 = sheet.getRow(rowNum).getCell(colNum);
                    if (PoiExcelUtil.isMergedRegion(sheet, cell1)) {
                        rowNum++;
                        continue;//如果是合并单元格，则进行下一行
                    }
                    Cell cell2 = sheet.getRow(sameContentRow).getCell(colNum);
                    if (cell2 == null) break;
                    while (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
                        sameContentRow++;
                        if (sheet.getRow(sameContentRow) == null) break;
                        cell2 = sheet.getRow(sameContentRow).getCell(colNum);
                        if (cell2 == null) break;
                    }
                    if (rowNum != sameContentRow - 1) {
                        PoiExcelUtil.createMergeRegion(sheet, cell1.getRowIndex(), sameContentRow - 1, colNum, colNum);
                        CellStyle cellStyle = cell1.getCellStyle();
                        cellStyle.setAlignment(HorizontalAlignment.CENTER);
                        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                        cell1.setCellStyle(cellStyle);
                    }
                    rowNum = sameContentRow;
                }
            }
             /*
            表数据
             */
            Map<Integer, CellStyle> colCellStyleMap = new HashMap<>();
            for (int rowNum = maxDeep; rowNum < maxDeep + sheetList.size(); rowNum++) {
                Row row = sheet.createRow(rowNum);
                Object dataObject = sheetList.get(rowNum - maxDeep);
                Field[] dataFields = dataObject.getClass().getDeclaredFields();

                for (int colNum = 0; colNum < excelFieldList.size(); colNum++) {
                    ExcelField excelField = excelFieldList.get(colNum);
                    Field dataColField = null;
                    for (Field field : dataFields) {
                        if (field.isAnnotationPresent(ExcelField.class)) {
                            ExcelField excelFieldTemp = field.getAnnotation(ExcelField.class);
                            if (excelFieldTemp.equals(excelField)) {
                                dataColField = field;
                            }
                        }
                    }
                    Cell cell = row.createCell(colNum);
                    CellStyle bodyCellStyle = colCellStyleMap.get(colNum);
                    if (bodyCellStyle == null) {
                        bodyCellStyle = workbook.createCellStyle();
                        PoiExcelUtil.setCellFontAlignment(bodyCellStyle, excelField.bodyFontHorizontalAlignment(), excelField.bodyFontVerticalAlignment());//水平竖直位置
                        PoiExcelUtil.setCellFont(workbook, bodyCellStyle, excelField.bodyFontBold(), excelField.bodyFontName(), excelField.bodyFontColor());//字体
                        PoiExcelUtil.setCellBackgroundColor(bodyCellStyle, excelField.bodyBgColor());//背景颜色
                        PoiExcelUtil.setCellBorder(bodyCellStyle,
                                excelField.bodyBorder()[0], excelField.bodyBorder()[1], excelField.bodyBorder()[2], excelField.bodyBorder()[3],
                                excelField.bodyBorderColor()[0], excelField.bodyBorderColor()[1], excelField.bodyBorderColor()[2], excelField.bodyBorderColor()[3]
                        );//边框
                        PoiExcelUtil.setCellWordWrap(bodyCellStyle, excelField.isWordWrap());//设置自动换行
                        PoiExcelUtil.setCellDataFormatStyle(workbook, bodyCellStyle, excelField.dataFormat());//数据格式
                        PoiExcelUtil.setColumnWidth(sheet, colNum, excelField.columnWidth());//列宽
                        colCellStyleMap.put(colNum, bodyCellStyle);
                    }
                    if ("".equals(excelField.commentText()) == false) {
                        PoiExcelUtil.setCellComment(workbook, sheet, cell, excelField.commentText(), excelField.isCommonVisible());//注解
                    }
                    if (excelField.isUrlHyperLink()) {//设置cell链接
                        dataColField.setAccessible(true);
                        try {
                            PoiExcelUtil.setCellHyperLink(workbook, cell, HyperlinkType.URL, dataColField.get(dataObject).toString());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } finally {
                            dataColField.setAccessible(false);
                        }
                    }
                    //设置内容
                    cell.setCellStyle(bodyCellStyle);
                    try {
                        dataColField.setAccessible(true);
                        Object val = dataColField.get(dataObject);
                        if ("".equals(excelField.changeTo().trim()) == false) {//changeTo = "N:否；Y:是；n:否；y:是；"
                            Map<String, String> map = formatToMap2(excelField.changeTo().trim());
                            val = map.get(val.toString());
                            cell.setCellValue((String) val);
                        } else {
                            switch (dataColField.getType().getTypeName()) {
                                case "short":
                                case "java.lang.Short":
                                case "int":
                                case "java.lang.Integer":
                                case "long":
                                case "java.lang.Long":
                                case "float":
                                case "java.lang.Float":
                                case "double":
                                case "java.lang.Double":
                                    cell.setCellValue((double) val);
                                    break;
                                case "java.util.Date":
                                    cell.setCellValue((Date) val);
                                    break;
                                case "java.time.LocalDateTime":
                                    cell.setCellValue((LocalDateTime) val);
                                    break;
                                case "java.time.LocalDate":
                                    cell.setCellValue((LocalDate) val);
                                    break;
                                case "java.util.Calendar":
                                    cell.setCellValue((Calendar) val);
                                    break;
                                case "java.lang.String":
                                    cell.setCellValue((String) val);
                                    break;
                                case "boolean":
                                case "java.lang.Boolean":
                                    cell.setCellValue((Boolean) val);
                                    break;
                                default:
                                    cell.setCellValue(val.toString());
                                    break;
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } finally {
                        dataColField.setAccessible(false);
                    }
                }
            }

        }

        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return void
     * @Description 导出到web
     * @Param [httpServletResponse, filename, glass, list, groupNums, sheetName]
     * @Author hyy
     * @Date 2021/7/7 16:13
     **/
    public static void exportToWeb(HttpServletResponse httpServletResponse, String filename,
                                   Class glass, List list, Integer[] groupNums, String sheetName) throws IllegalArgumentException {
        try {
            filename = filename.split("\\.")[0] + "." + excel_properties.get("workbook.outType");
            httpServletResponse.setContentType("application/vnd.ms-excel");// 告诉浏览器用什么软件可以打开此文件
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8")); // 下载文件的默认名称
            OutputStream outputStream = httpServletResponse.getOutputStream();
            exportToStream(glass, list, groupNums, sheetName, httpServletResponse.getOutputStream());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("》》》导出文件失败《《《" + e.getMessage());
        }
    }

    /**
     * @param str N:否；Y:是；n:否；y:是；
     * @return
     */
    private static Map<String, String> formatToMap2(String str) {
        Map<String, String> map = new HashMap<>();
        String[] strings = str.split(";|；");
        for (String strTemp : strings) {
            if (strTemp != null && "".equals(strTemp.trim()) == false) {
                String key = strTemp.split(":|：")[0].trim();
                String value = "";
                if (strTemp.split(":|：").length > 1) {
                    value = strTemp.split(":|：")[1].trim();
                }
                map.put(key, value);
            }
        }
        return map;
    }
}
