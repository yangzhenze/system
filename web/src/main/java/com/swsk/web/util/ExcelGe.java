package com.swsk.web.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;
import java.util.List;

/**
 * @author zzy
 * @Date 2020-02-26 10:38
 */
public class ExcelGe {


    // 文档对象
    private Workbook wb;
    // 表单对象
    private Sheet sheet;

    // 头样式
    private CellStyle headStyle;
    // 数据样式
    private CellStyle dataStyle;
    // 字体
    private Font font;

    public ExcelGe(){
        this.wb = new XSSFWorkbook();
        this.sheet = wb.createSheet();
    }

    public ExcelGe(Workbook wb){
        this.wb = wb;
        this.sheet = wb.createSheet();
    }

    public ExcelGe(Workbook wb,String sheetName){
        this.wb = wb;
        this.sheet = wb.createSheet(sheetName);
    }


    private void setDefaultStyle(){
        //设置字体
        Font font = this.wb.createFont();
        font.setFontHeightInPoints((short)14);
        font.setFontName("宋体");
        font.setItalic(false);
        font.setStrikeout(false);
        this.font = font;

        //设置头部单元格样式
        CellStyle headStyle = this.wb.createCellStyle();
        headStyle.setBorderBottom(BorderStyle.THICK);  //设置单元格线条
        headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());   //设置单元格颜色
        headStyle.setBorderLeft(BorderStyle.THICK);
        headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderRight(BorderStyle.THICK);
        headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBorderTop(BorderStyle.THICK);
        headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setAlignment(HorizontalAlignment.CENTER);    //设置水平对齐方式
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
        headStyle.setShrinkToFit(true);  //自动伸缩
        headStyle.setFont(font);  //设置字体
        this.headStyle = headStyle;


        /*设置数据单元格格式*/
        CellStyle dataStyle = this.wb.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);  //设置单元格线条
        dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());   //设置单元格颜色
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        dataStyle.setAlignment(HorizontalAlignment.LEFT);    //设置水平对齐方式
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);  //设置垂直对齐方式
        dataStyle.setShrinkToFit(true);  //自动伸缩
        this.dataStyle = dataStyle;
    }

    /**
     * 添加行数据
     * @param cellStyle
     * @param rowIndex
     * @param datas
     * @return
     */
    public void addRowData(CellStyle cellStyle,int rowIndex,String... datas){
        Row row = this.sheet.createRow(rowIndex);
        for (int i = 0; i < datas.length; i++) {
            Cell cityCell = row.createCell(i);

            if(null != cityCell){
                cityCell.setCellStyle(cellStyle);
            }

            cityCell.setCellValue(datas[i]);
        }
    }

    /**
     * 添加行数据
     * @param cellStyle
     * @param rowIndex
     * @param datas
     * @return
     */
    public Row addRowData(CellStyle cellStyle,int rowIndex,List<Object> datas){
        Row row = this.sheet.createRow(rowIndex);
        for (int i = 0; i < datas.size(); i++) {
            Cell cityCell = row.createCell(i);

            if(null != cityCell){
                cityCell.setCellStyle(cellStyle);
            }

            if(datas.get(i) instanceof String){
                cityCell.setCellValue(datas.get(i).toString());
            }

            if(datas.get(i) instanceof Date){
                cityCell.setCellValue((Date)datas.get(i));
            }
            if(datas.get(i) instanceof Boolean){
                cityCell.setCellValue((boolean)datas.get(i));
            }
            if(datas.get(i) instanceof String){
                cityCell.setCellValue((String)datas.get(i));
            }

            if(datas.get(i) instanceof Number){
                cityCell.setCellValue(((Number) datas.get(i)).doubleValue());
            }


        }
        return row;
    }

    /**
     * 添加行数据
     * @param cellStyle
     * @param rowIndex
     * @param datas
     * @return
     */
    public Row addRowData(CellStyle cellStyle,int rowIndex,Object... datas){
        Row row = this.sheet.createRow(rowIndex);
        for (int i = 0; i < datas.length; i++) {
            Cell cityCell = row.createCell(i);

            if(null != cityCell){
                cityCell.setCellStyle(cellStyle);
            }



            if(null == datas[i]){
                cityCell.setCellValue("");
            }

            if(datas[i] instanceof String){
                cityCell.setCellValue(datas[i].toString());
            }

            if(datas[i] instanceof Date){
                cityCell.setCellValue((Date)datas[i]);
            }
            if(datas[i] instanceof Boolean){
                cityCell.setCellValue((boolean)datas[i]);
            }
            if(datas[i] instanceof String){
                cityCell.setCellValue((String)datas[i]);
            }

            if(datas[i] instanceof Number){
                cityCell.setCellValue(((Number) datas[i]).doubleValue());
            }


        }
        return row;
    }



    /**
     * 合并单元格
     * @param mergeRows
     * @param firstCol
     * @param lastCol
     */
    public void mergeRow(List<Integer[]> mergeRows,int firstCol,int lastCol){

        for (Integer[] mergeRow : mergeRows) {
            if(mergeRow == null){
                continue;
            }

            if(mergeRow[0] == null || mergeRow[1] == null){
                continue;
            }

            if(mergeRow[0].equals(mergeRow[1])){
                continue;
            }
            CellRangeAddress region = new CellRangeAddress(mergeRow[0], mergeRow[1], firstCol, lastCol);
            this.sheet.addMergedRegion(region);
        }
    }

    public Workbook getWb() {
        return wb;
    }

    public void setWb(Workbook wb) {
        this.wb = wb;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public CellStyle getHeadStyle() {
        return headStyle;
    }

    public void setHeadStyle(CellStyle headStyle) {
        this.headStyle = headStyle;
    }

    public CellStyle getDataStyle() {
        return dataStyle;
    }

    public void setDataStyle(CellStyle dataStyle) {
        this.dataStyle = dataStyle;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
