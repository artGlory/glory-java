package com.glory.gloryUtils.utils.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestBean {
    @ExcelField(headTitle = "group", groupNum = {1, 2})
    private String group;

    @ExcelField(headTitle = {"复杂", "表头1"}, groupNum = {3, 2})
    private String cloumn;

    @ExcelField(headTitle = {"复杂", "表头2"}, dataFormat = "yyyy-MM-dd HH:mm:ss")
    private Date dateformat;

    @ExcelField(headTitle = "headTitle", dataFormat = "0.0000%")
    private double headTitle;

    @ExcelField(headTitle = {"commentText"}, commentText = "注解内容")
    private String commentText;

    @ExcelField(headTitle = {"commentText", "changeTo"}, changeTo = "N:否；Y:是；n:否；y:是；")
    private String changeTo;

    @ExcelField(headTitle = {"url"}, isUrlHyperLink = true, columnOrder = 0)
    private String url;


}
