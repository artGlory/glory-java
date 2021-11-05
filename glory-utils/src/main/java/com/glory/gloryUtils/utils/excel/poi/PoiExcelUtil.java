package com.glory.gloryUtils.utils.excel.poi;

import com.glory.gloryUtils.utils.PathUtil;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class PoiExcelUtil {

    public static void main(String[] args) throws IOException {
        {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = createSheet(workbook);
            CellStyle cellStyle = workbook.createCellStyle();
            Row row;
            Cell cell;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    row = sheet.getRow(i) == null ? sheet.createRow(i) : sheet.getRow(i);
                    cell = row.getCell(j) == null ? row.createCell(j) : row.getCell(j);
                    cell.setCellValue(i + " " + j);
                }
            }
            createMergeRegion(sheet,0,2,0,2);

            workbook.write(new FileOutputStream(new File(PathUtil.getRandomDesktopFilePath(".xls"))));
        }

    }

    /**
     * @return void
     * @Description 设置cell文字水平、垂直方向标准
     * @Param [cellStyle, horizontal, vertical]
     * @Author hyy
     * @Date 2021/6/26 11:09
     **/
    public static void setCellFontAlignment(CellStyle cellStyle, HorizontalAlignment horizontal, VerticalAlignment vertical) {
        cellStyle.setAlignment(horizontal);
        cellStyle.setVerticalAlignment(vertical);
    }

    /**
     * @return void
     * @Description 设置cell字体
     * @Param [workbook, cellStyle, bold, fontName, color]
     * @Author hyy
     * @Date 2021/6/26 12:33
     **/
    public static void setCellFont(Workbook workbook, CellStyle cellStyle, Boolean bold, String fontName, IndexedColors color) {
        Font font = workbook.createFont();
        font.setBold(bold);//是否加粗
        font.setFontName(fontName);
        font.setColor(color.getIndex());
        cellStyle.setFont(font);
    }

    /**
     * @return void
     * @Description 设置cell背景颜色
     * @Param [cellStyle, indexedColors]
     * @Author hyy
     * @Date 2021/6/26 10:37
     **/
    public static void setCellBackgroundColor(CellStyle cellStyle, IndexedColors indexedColors) {
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(indexedColors.getIndex());
    }

    /**
     * @return void
     * @Description 设置cell边框和边框颜色
     * @Param [cellStyle, top, right, bottom, left, topColor, rightColor, bottomColor, leftColor]
     * @Author hyy
     * @Date 2021/6/26 14:05
     **/
    public static void setCellBorder(CellStyle cellStyle
            , BorderStyle top, BorderStyle right, BorderStyle bottom, BorderStyle left
            , IndexedColors topColor, IndexedColors rightColor, IndexedColors bottomColor, IndexedColors leftColor
    ) {
        cellStyle.setBorderTop(top);
        cellStyle.setBorderRight(right);
        cellStyle.setBorderBottom(bottom);
        cellStyle.setBorderLeft(left);
        cellStyle.setTopBorderColor(topColor.getIndex());
        cellStyle.setRightBorderColor(rightColor.getIndex());
        cellStyle.setBottomBorderColor(bottomColor.getIndex());
        cellStyle.setLeftBorderColor(leftColor.getIndex());
    }

    /**
     * @return void
     * @Description 设置cell批注
     * @Param [workbook, sheet, cell, message, author, defaultShow]
     * @Author hyy
     * @Date 2021/6/26 14:42
     **/
    public static void setCellComment(Workbook workbook, Sheet sheet, Cell cell
            , String message, boolean defaultShow) {
        CreationHelper createHelper = workbook.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor clientAnchor = drawing.createAnchor(0, 0, 0, 0, 0, 0, 0, 0);
        Comment comment = drawing.createCellComment(clientAnchor);
        comment.setString(createHelper.createRichTextString(message));
        comment.setVisible(defaultShow);
        cell.setCellComment(comment);
    }

    /**
     * @Description 设置cell数据格式
     * @Param [workbook, dataFormat(百分比：0.00% ； 保留2位小数：0.00 ； 日期：yyyy-MM-dd HH:mm:ss ； 文本格式：@ ；) ]
     * @Author hyy
     * @Date 2021/6/26 9:57
     **/
    public static void setCellDataFormatStyle(Workbook workbook, CellStyle cellStyle, String dataFormat) {
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(dataFormat));
    }

    /**
     * @return void
     * @Description 设置cell文本内容是否自动换行
     * @Param [cellStyle, flag]
     * @Author hyy
     * @Date 2021/6/26 11:26
     **/
    public static void setCellWordWrap(CellStyle cellStyle, boolean flag) {
        cellStyle.setWrapText(flag);//设置自动换行
    }

    /**
     * @Description 设置cell链接
     * @Param [workbook, hyperlinkType, toWhere]
     * @Author hyy
     * @Date 2021/6/26 9:23
     **/
    public static void setCellHyperLink(Workbook workbook, Cell cell, HyperlinkType hyperlinkType, String toWhere) {
        CreationHelper creationHelper = workbook.getCreationHelper();
        Hyperlink hyperlink = creationHelper.createHyperlink(hyperlinkType);
        hyperlink.setAddress(toWhere);
        cell.setHyperlink(hyperlink);
    }

    /**
     * @return void
     * @Description 设置列宽，
     * columnWidthMap key - the column to set (0-based)
     * columnWidthMap value - the  characters size  ; 如果为<0，则为自动列宽
     * @Param [sheet, columnWidthMap（）]
     * @Author hyy
     * @Date 2021/6/26 11:17
     **/
    public static void setColumnWidth(Sheet sheet, Map<Integer, Integer> columnWidthMap) {
        for (int column : columnWidthMap.keySet()) {
            int width = columnWidthMap.get(column);
            if (width > 255) width = 255;//the maximum column width in Excel is 255 characters
            if (width < 0) {
                sheet.autoSizeColumn(column);
            } else if (width == 0) {
                sheet.setColumnWidth(column, width);
            } else
                sheet.setColumnWidth(column, (int) ((width + 0.72) * 256));
        }
    }

    /**
     * @return void
     * @Description 设置单列列宽
     * @Param [sheet, column, width]
     * @Author hyy
     * @Date 2021/6/26 18:25
     **/
    public static void setColumnWidth(Sheet sheet, Integer column, Integer width) {
        if (width > 255) width = 255;//the maximum column width in Excel is 255 characters
        if (width < 0) {
            sheet.autoSizeColumn(column);
        } else
            sheet.setColumnWidth(column, (int) ((width + 0.72) * 256));
    }


    /**
     * @return java.lang.String
     * @Description 获取安全的sheet名字
     * @Param [sheetName]
     * @Author hyy
     * @Date 2021/6/26 8:19
     **/
    public static String getSafeSheetName(String sheetName) {
        WorkbookUtil.validateSheetName(sheetName);
        String safeName = WorkbookUtil.createSafeSheetName(sheetName);
        return safeName;
    }

    /**
     * @return void
     * @Description 创建合并区域 ;
     * 合并后的单元格，默认值为firstRow，firstCol的值；
     * 单元格合并不影响行数和列数，取消合并单元格，原单元格上的数据都会显示出来
     * @Param [sheet, firstRow, lastRow, firstCol, lastCol] 从0开始
     * @Author hyy
     * @Date 2021/6/26 14:48
     **/
    public static void createMergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(cellRangeAddress);
    }

    /**
     * @return boolean
     * @Description 单元格是否是合并单元格
     * @Param [sheet, row, column]
     * @Author hyy
     * @Date 2021/7/7 10:49
     **/
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return boolean
     * @Description 单元格是否是合并单元格
     * @Param [sheet, cell]
     * @Author hyy
     * @Date 2021/7/7 10:51
     **/
    public static boolean isMergedRegion(Sheet sheet, Cell cell) {
        return isMergedRegion(sheet, cell.getRowIndex(), cell.getColumnIndex());
    }

    /**
     * @return org.apache.poi.ss.usermodel.Workbook
     * @Description
     * @Param [filePath]
     * @Author hyy
     * @Date 2021/6/26 15:54
     **/
    public static Workbook createWorkbook(String filePath) {
        Workbook workbook;
        if (filePath.endsWith(".xls")) {
            workbook = new HSSFWorkbook();
        } else if (filePath.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook();
        } else {
            throw new IllegalArgumentException("fileName must end with .xls or .xlsx");
        }
        return workbook;
    }

    /**
     * @return org.apache.poi.ss.usermodel.Sheet
     * @Description
     * @Param [workbook, sheetName]
     * @Author hyy
     * @Date 2021/6/26 15:55
     **/
    public static Sheet createSheet(Workbook workbook, String sheetName) {
        return workbook.createSheet(getSafeSheetName(sheetName));
    }

    /**
     * @return org.apache.poi.ss.usermodel.Sheet
     * @Description
     * @Param [workbook]
     * @Author hyy
     * @Date 2021/6/26 15:55
     **/
    public static Sheet createSheet(Workbook workbook) {
        int sheetNumber = workbook.getNumberOfSheets() + 1;
        return createSheet(workbook, "Sheet" + sheetNumber);
    }
}
