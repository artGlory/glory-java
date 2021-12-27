package com.glory.gloryUtils.utils.excel;

import com.glory.gloryUtils.utils.PathUtil;
import com.glory.gloryUtils.utils.excel.poi.PoiExcelUtil;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description excel导出工具，支持复杂表头
 * @Author hyy
 * @Date 2021/7/8 15:53
 **/
@Data
public class ExcelMapUtil {
    /**
     * 表头
     * [
     * 第一行[
     * [列key，列名称，列宽（对应列宽，多少个字符，默认指30个字符;[0,255] <0 自动列宽）]，[列key，列名称，列宽（对应列宽，多少个字符，默认指30个字符;[0,255] <0 自动列宽）]
     * ]
     * 第二行,[
     * [列key，列名称，列宽（对应列宽，多少个字符，默认指30个字符;[0,255] <0 自动列宽）]，[列key，列名称，列宽（对应列宽，多少个字符，默认指30个字符;[0,255] <0 自动列宽）]
     * ]
     * ]
     */
    private List<List<Object[]>> headerInfoList = new ArrayList<List<Object[]>>();
    /**
     * 数据  [{列key,列Object},{列key,列Object}]
     */
    private List<Map<String, Object>> dataInfoList = new ArrayList<Map<String, Object>>();
    /**
     * 合并区域 [[ firstRow, lastRow, firstCol, lastCol],[ firstRow, lastRow, firstCol, lastCol]]
     */
    private List<Integer[]> mergeReginArr;

    /**
     * sheetName
     */
    private String sheetName = null;

    /**
     * @param headerInfoList headItem [列key，列名称，列宽（对应列宽，多少个字符，默认指30个字符;[0,255] <0 自动列宽）]
     * @param dataInfoList   据  [{列key,列Object},{列key,列Object}]
     * @param mergeReginArr  默认值为null  合并区域 [[ firstRow, lastRow, firstCol, lastCol],[ firstRow, lastRow, firstCol, lastCol]]
     * @param sheetName      默认值为null
     * @return
     * @Description
     * @Param [headerInfoList, dataInfoList, mergeReginArr, sheetName]
     * @Author hyy
     * @Date 2021-12-27 15:36
     **/
    public ExcelMapUtil(List<List<Object[]>> headerInfoList, List<Map<String, Object>> dataInfoList, List<Integer[]> mergeReginArr, String sheetName) {
        this.headerInfoList = headerInfoList;
        this.dataInfoList = dataInfoList;
        this.mergeReginArr = mergeReginArr;
        this.sheetName = sheetName;
    }

    public static void main(String[] args) {
        List<List<Object[]>> headerInfoList = new ArrayList<>();
        List<Map<String, Object>> dataInfoList = new ArrayList<>();
        List<Integer[]> mergeReginArr = new ArrayList<>();

        int colNumber = 10;
        List<Object[]> headerInfoListRow01 = new ArrayList<Object[]>();
        for (int i = 0; i < colNumber; i++) {
            headerInfoListRow01.add(new Object[]{"col_" + i, "列_" + i, 30});
        }
        headerInfoList.add(headerInfoListRow01);

        for (int row = 0; row < 200; row++) {
            Map<String, Object> dataInfo = new HashMap<>();
            for (int i = 0; i < colNumber; i++) {
                dataInfo.put("col_" + i, row + " " + i);
            }
            dataInfoList.add(dataInfo);
        }
        mergeReginArr.add(new Integer[]{5, 7, 1, 3});
        try {
            new ExcelMapUtil(headerInfoList, dataInfoList, mergeReginArr, null)
                    .exportToStream(new FileOutputStream(PathUtil.getRandomDesktopFilePath(".xlsx")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void exportToStream(OutputStream outputStream) {
        Workbook workbook;
        if ("xlsx".equals(workbook_outType)) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        int rowSize = workbook_sheet_rowSize;//单个sheet最大多少条数据
        int maxSheetPageNumber = dataInfoList.size() / rowSize + 1;
        if (dataInfoList.size() != 0 && dataInfoList.size() % rowSize == 0) maxSheetPageNumber--;
        for (int i = 0; i < maxSheetPageNumber; i++) {
            Sheet sheet = sheetName == null ? PoiExcelUtil.createSheet(workbook) : PoiExcelUtil.createSheet(workbook, sheetName);
            int fromIndex = i * rowSize;
            int toIndex = (i + 1) * rowSize;
            toIndex = toIndex > dataInfoList.size() ? dataInfoList.size() : toIndex;
            List<Map<String, Object>> sheetList = dataInfoList.subList(fromIndex, toIndex);
            /*
             表头
            */
            for (int rowNum = 0; rowNum < headerInfoList.size(); rowNum++) {
                Row row = sheet.createRow(rowNum);
                for (int colNum = 0; colNum < headerInfoList.get(rowNum).size(); colNum++) {
                    Object[] headerInfo = headerInfoList.get(rowNum).get(colNum);
                    CellStyle headCellStyle = workbook.createCellStyle();
                    PoiExcelUtil.setCellFontAlignment(headCellStyle, headFontHorizontalAlignment, headFontVerticalAlignment);//水平竖直位置
                    PoiExcelUtil.setCellFont(workbook, headCellStyle, headFontBold, headFontName, headFontColor);//字体
                    PoiExcelUtil.setCellBackgroundColor(headCellStyle, headBgColor);//背景颜色
                    PoiExcelUtil.setCellBorder(headCellStyle,
                            headBorder[0], headBorder[1], headBorder[2], headBorder[3],
                            headBorderColor[0], headBorderColor[1], headBorderColor[2], headBorderColor[3]
                    );//边框
                    //setCellComment 注解
                    PoiExcelUtil.setCellDataFormatStyle(workbook, headCellStyle, "@");//数据格式
                    PoiExcelUtil.setCellWordWrap(headCellStyle, isWordWrap);//设置自动换行
                    //setCellHyperLink 设置cell链接
                    PoiExcelUtil.setColumnWidth(sheet, colNum, headerInfo[2] == null ? columnWidth : (int) headerInfo[2]);//列宽
                    //设置内容
                    Cell cell = row.createCell(colNum);
                    cell.setCellStyle(headCellStyle);
                    cell.setCellValue((String) headerInfo[1]);
                }
            }
            /*
            表头合并单元格
             */
            if (mergeReginArr != null) {
                for (Integer[] mergeRegin : mergeReginArr) {
                    PoiExcelUtil.createMergeRegion(sheet, mergeRegin[0], mergeRegin[1], mergeRegin[2], mergeRegin[3]);
                }
            }
             /*
            表数据
             */
            Map<Integer, CellStyle> colCellStyleMap = new HashMap<>();
            int maxDeep = headerInfoList.size();
            for (int rowNum = maxDeep; rowNum < maxDeep + sheetList.size(); rowNum++) {
                Row row = sheet.createRow(rowNum);
                Map<String, Object> dataObject = sheetList.get(rowNum - maxDeep);

                for (int colNum = 0; colNum < headerInfoList.get(headerInfoList.size() - 1).size(); colNum++) {
                    Object[] headerInfo = headerInfoList.get(headerInfoList.size() - 1).get(colNum);
                    Cell cell = row.createCell(colNum);
                    CellStyle bodyCellStyle = colCellStyleMap.get(colNum);
                    if (bodyCellStyle == null) {
                        bodyCellStyle = workbook.createCellStyle();
                        PoiExcelUtil.setCellFontAlignment(bodyCellStyle, bodyFontHorizontalAlignment, bodyFontVerticalAlignment);//水平竖直位置
                        PoiExcelUtil.setCellFont(workbook, bodyCellStyle, bodyFontBold, bodyFontName, bodyFontColor);//字体
                        PoiExcelUtil.setCellBackgroundColor(bodyCellStyle, bodyBgColor);//背景颜色
                        PoiExcelUtil.setCellBorder(bodyCellStyle,
                                bodyBorder[0], bodyBorder[1], bodyBorder[2], bodyBorder[3],
                                bodyBorderColor[0], bodyBorderColor[1], bodyBorderColor[2], bodyBorderColor[3]
                        );//边框
                        PoiExcelUtil.setCellWordWrap(bodyCellStyle, isWordWrap);//设置自动换行
                        PoiExcelUtil.setCellDataFormatStyle(workbook, bodyCellStyle, dataFormat);//数据格式
                        PoiExcelUtil.setColumnWidth(sheet, colNum, columnWidth);//列宽
                        colCellStyleMap.put(colNum, bodyCellStyle);
                    }
                    //设置内容
                    cell.setCellStyle(bodyCellStyle);
                    Object value = dataObject.get(headerInfo[0]);
                    cell.setCellValue(String.valueOf(value));
                }
            }

        }

        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String workbook_outType = "xls";//导出文件类型; xls ,xlsx
    private int workbook_sheet_rowSize = 5000;//单个sheet最大多少条数据

    /**
     * @Title columnWidth
     * @Description 对应列宽，多少个字符，默认指30个字符;[0,255] <0 自动列宽
     */
    private int columnWidth = 30;

    /**
     * @Title dateformat
     * @Description 百分比：0.0000% ； 保留2位小数：0.0000 ； 日期：yyyy-MM-dd HH:mm:ss ； 文本格式：@ ；
     */
    private String dataFormat = "@";

    /**
     * @Title isWordWrap 是否自动换行
     * @Description
     */
    boolean isWordWrap = false;

    //===============================head========start==============================

    /**
     * @Title headBorder 表头边框
     * @Description
     */
    private BorderStyle[] headBorder = {BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN};

    /**
     * @Title headBorder 表头边框颜色
     * @Description
     */
    private IndexedColors[] headBorderColor = {IndexedColors.BLACK, IndexedColors.BLACK, IndexedColors.BLACK, IndexedColors.BLACK};

    /**
     * @Title headBgColor 表头单元格,背景颜色
     * @Description
     */
    private IndexedColors headBgColor = IndexedColors.PALE_BLUE;

    /**
     * @Title headFontBold 表头单元格,字体加粗
     * @Description
     */
    private boolean headFontBold = true;

    /**
     * @Title headFontName 表头单元格,字体
     * @Description
     */
    private String headFontName = "微软雅黑";

    /**
     * @Title headFontName 表头单元格,字体颜色
     * @Description
     */
    private IndexedColors headFontColor = IndexedColors.BLACK;

    /**
     * @Title headFontHorizontalAlignment 表头单元格,水平标准
     * @Description
     */
    private HorizontalAlignment headFontHorizontalAlignment = HorizontalAlignment.LEFT;

    /**
     * @Title headFontHorizontalAlignment 表头单元格,垂直标准
     * @Description
     */
    private VerticalAlignment headFontVerticalAlignment = VerticalAlignment.CENTER;
    //===============================head========end==============================

    //===============================body============start==========================

    /**
     * @Title bodyBorder 表体边框
     * @Description
     */
    private BorderStyle[] bodyBorder = {BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN};


    /**
     * @Title bodyBorderColor 表体边框颜色
     * @Description
     */
    private IndexedColors[] bodyBorderColor = {IndexedColors.BLACK, IndexedColors.BLACK, IndexedColors.BLACK, IndexedColors.BLACK};

    /**
     * @Title bodyBgColor 表体单元格,背景颜色
     * @Description
     */
    private IndexedColors bodyBgColor = IndexedColors.WHITE1;

    /**
     * @Title bodyFontBold 表体单元格,字体加粗
     * @Description
     */
    private boolean bodyFontBold = false;

    /**
     * @Title bodyFontName 表体单元格,字体
     * @Description
     */
    private String bodyFontName = "微软雅黑";

    /**
     * @Title headFontName 表体单元格,字体颜色
     * @Description
     */
    private IndexedColors bodyFontColor = IndexedColors.BLACK;

    /**
     * @Title bodyFontHorizontalAlignment 表体单元格,水平标准
     * @Description
     */
    private HorizontalAlignment bodyFontHorizontalAlignment = HorizontalAlignment.LEFT;

    /**
     * @Title bodyFontVerticalAlignment 表体单元格,垂直标准
     * @Description
     */
    private VerticalAlignment bodyFontVerticalAlignment = VerticalAlignment.CENTER;

    //===============================body============end==========================

}

