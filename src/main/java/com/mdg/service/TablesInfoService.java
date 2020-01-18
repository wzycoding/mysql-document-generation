package com.mdg.service;

import com.mdg.dao.TablesMapper;
import com.mdg.entity.TablesEntity;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class TablesInfoService {

    public static final int ROW_HEIGHT = (int) (15.625 * 32);
    public static final int COL_WIDTH = 12;
    @Resource
    private TablesMapper tablesMapper;

    public List<TablesEntity> listTables(String databaseName) {
        return tablesMapper.listTablesByDatabaseName(databaseName);
    }

    public List<Map<String,String>> listTableField(String databaseName, String tableName){
        return tablesMapper.listTableFieldByTableName(databaseName, tableName);
    }

    /**
     * 生成数据库设计excel文档
     */
    public String generateDocument(String databaseName) throws IOException {
        String[] title = {"字段名称", "字段含义", "字段类型", "字段编码", "是否为空", "是否为主键", "默认值", "额外信息"};
        //创建HSSF工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        HSSFCellStyle otherCellStyle = workbook.createCellStyle();
        otherCellStyle.setBorderLeft(BorderStyle.THIN);
        otherCellStyle.setBorderRight(BorderStyle.THIN);
        otherCellStyle.setBorderTop(BorderStyle.THIN);
        otherCellStyle.setBorderBottom(BorderStyle.THIN);
        //首先获取所有的表信息
        List<TablesEntity> tablesEntities = tablesMapper.listTablesByDatabaseName(databaseName);
        for (int j = 0; j < tablesEntities.size(); j++) {
            //创建一个Sheet页
            HSSFSheet sheet = workbook.createSheet();
            sheet.setDefaultColumnWidth((short) COL_WIDTH);
            workbook.setSheetName(j, tablesEntities.get(j).getTableName() + "表");
            //第一行表说明
            HSSFRow row0 = sheet.createRow(0);
            //创建第二行（一般是表头）
            HSSFRow row1 = sheet.createRow(1);
            row0.setHeight((short) ROW_HEIGHT);
            row1.setHeight((short) ROW_HEIGHT);
            HSSFCell tableInfoCell = null;
            tableInfoCell = row0.createCell(0);
            tableInfoCell.setCellValue("表" + (j + 1) + ": " + tablesEntities.get(j).getTableName() + "(" + tablesEntities.get(j).getTableComment() + ")");
            //创建列
            HSSFCell cell = null;
            //设置表头
            for (int i = 0; i < title.length; i++) {
                cell = row1.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(headerCellStyle);
            }
            //获取具体表格的数据
            String tableName = tablesEntities.get(j).getTableName();
            List<Map<String, String>> tableFieldList = tablesMapper.listTableFieldByTableName(databaseName, tableName);
            for ( int i = 0; i < tableFieldList.size(); i++) {

                String fieldId = tableFieldList.get(i).get("Field");
                String type = tableFieldList.get(i).get("Type");
                String collation = tableFieldList.get(i).get("Collation");
                String isNull = tableFieldList.get(i).get("Null");
                String key = tableFieldList.get(i).get("Key");
                String defaultValue = tableFieldList.get(i).get("Default");
                String extra = tableFieldList.get(i).get("Extra");
                String comment = tableFieldList.get(i).get("Comment");
                HSSFRow row = sheet.createRow(i + 2);
                //设置行高
                row.setHeight((short) ROW_HEIGHT);
                //创建列
                HSSFCell fieldCell0 = row.createCell(0);
                HSSFCell fieldCell1 = row.createCell(1);
                HSSFCell fieldCell2 = row.createCell(2);
                HSSFCell fieldCell3 = row.createCell(3);
                HSSFCell fieldCell4 = row.createCell(4);
                HSSFCell fieldCell5 = row.createCell(5);
                HSSFCell fieldCell6 = row.createCell(6);
                HSSFCell fieldCell7 = row.createCell(7);

                fieldCell0.setCellStyle(otherCellStyle);
                fieldCell1.setCellStyle(otherCellStyle);
                fieldCell2.setCellStyle(otherCellStyle);
                fieldCell3.setCellStyle(otherCellStyle);
                fieldCell4.setCellStyle(otherCellStyle);
                fieldCell5.setCellStyle(otherCellStyle);
                fieldCell6.setCellStyle(otherCellStyle);
                fieldCell7.setCellStyle(otherCellStyle);

                fieldCell0.setCellValue(fieldId);
                fieldCell1.setCellValue(comment);
                fieldCell2.setCellValue(type);
                fieldCell3.setCellValue(collation);
                fieldCell4.setCellValue(isNull);
                fieldCell5.setCellValue(key);
                fieldCell6.setCellValue(defaultValue);
                fieldCell7.setCellValue(extra);
            }
        }
        //保存到本地
        File file = new File("/Users/blusk123/seckill-sql-document.xls");
        FileOutputStream outputStream = new FileOutputStream(file);
        //将Excel写入输出流中
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        return "success";
    }

}
