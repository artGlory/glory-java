package com.glory.gloryUtils.utils.excel;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExcelField {

    /**
     * @Title cloumn
     * @Description 对应组；用于导出的时候指显示特定编号的组
     */
    int[] groupNum() default {};

    /**
     * @Title columnNum
     * @Description 对应类顺利，值越小越靠前
     */
    int columnOrder() default 2147483647;

    /**
     * @Title columnWidth
     * @Description 对应列宽，多少个字符，默认指30个字符;[0,255] <0 自动列宽
     */
    int columnWidth() default 30;

    /**
     * @Title dateformat
     * @Description 百分比：0.0000% ； 保留2位小数：0.0000 ； 日期：yyyy-MM-dd HH:mm:ss ； 文本格式：@ ；
     */
    String dataFormat() default "@";

    /**
     * @Title headTitle
     * @Description 导出时显示的列名
     */
    String[] headTitle();

    /**
     * @Title commentText
     * @Description 导出时显示的批注
     */
    String commentText() default "";

    /**
     * @Title isCommonVisible 批注默认不显示
     * @Description 导出时显示的批注
     */
    boolean isCommonVisible() default false;

    /**
     * @Title changeTo
     * @Description 导出时, 特殊指转化 ;例如： "N:否；Y:是；n:否；y:是；"
     */
    String changeTo() default "";

    /**
     * @Title isUrlHyperLink 是否是超链接
     * @Description
     */
    boolean isUrlHyperLink() default false;

    /**
     * @Title isWordWrap 是否自动换行
     * @Description
     */
    boolean isWordWrap() default false;

    //===============================head========start==============================

    /**
     * @Title headBorder 表头边框
     * @Description
     */
    BorderStyle[] headBorder() default {BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN};

    /**
     * @Title headBorder 表头边框颜色
     * @Description
     */
    IndexedColors[] headBorderColor() default {IndexedColors.BLACK, IndexedColors.BLACK, IndexedColors.BLACK, IndexedColors.BLACK};

    /**
     * @Title headBgColor 表头单元格,背景颜色
     * @Description
     */
    IndexedColors headBgColor() default IndexedColors.PALE_BLUE;

    /**
     * @Title headFontBold 表头单元格,字体加粗
     * @Description
     */
    boolean headFontBold() default true;

    /**
     * @Title headFontName 表头单元格,字体
     * @Description
     */
    String headFontName() default "微软雅黑";

    /**
     * @Title headFontName 表头单元格,字体颜色
     * @Description
     */
    IndexedColors headFontColor() default IndexedColors.BLACK;

    /**
     * @Title headFontHorizontalAlignment 表头单元格,水平标准
     * @Description
     */
    HorizontalAlignment headFontHorizontalAlignment() default HorizontalAlignment.LEFT;

    /**
     * @Title headFontHorizontalAlignment 表头单元格,垂直标准
     * @Description
     */
    VerticalAlignment headFontVerticalAlignment() default VerticalAlignment.CENTER;
    //===============================head========end==============================

    //===============================body============start==========================

    /**
     * @Title bodyBorder 表体边框
     * @Description
     */
    BorderStyle[] bodyBorder() default {BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN};


    /**
     * @Title bodyBorderColor 表体边框颜色
     * @Description
     */
    IndexedColors[] bodyBorderColor() default {IndexedColors.BLACK, IndexedColors.BLACK, IndexedColors.BLACK, IndexedColors.BLACK};

    /**
     * @Title bodyBgColor 表体单元格,背景颜色
     * @Description
     */
    IndexedColors bodyBgColor() default IndexedColors.WHITE1;

    /**
     * @Title bodyFontBold 表体单元格,字体加粗
     * @Description
     */
    boolean bodyFontBold() default false;

    /**
     * @Title bodyFontName 表体单元格,字体
     * @Description
     */
    String bodyFontName() default "微软雅黑";

    /**
     * @Title headFontName 表体单元格,字体颜色
     * @Description
     */
    IndexedColors bodyFontColor() default IndexedColors.BLACK;

    /**
     * @Title bodyFontHorizontalAlignment 表体单元格,水平标准
     * @Description
     */
    HorizontalAlignment bodyFontHorizontalAlignment() default HorizontalAlignment.LEFT;

    /**
     * @Title bodyFontVerticalAlignment 表体单元格,垂直标准
     * @Description
     */
    VerticalAlignment bodyFontVerticalAlignment() default VerticalAlignment.CENTER;

    //===============================body============end==========================

}
