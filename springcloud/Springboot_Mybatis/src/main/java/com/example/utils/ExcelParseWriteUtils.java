package com.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelParseWriteUtils {
    /**
     * EXCEL文件读取并组装对象<br>
     *
     * @param localFilePath
     * @return List<Map < String , Object>>
     */
    public static List<Map<String, Object>> parse(InputStream in, String localFilePath) throws Exception {
        List<Map<String, Object>> sourceDataList = new ArrayList<Map<String, Object>>();
        //FileInputStream in = null;
        Workbook workBook = null;
        try {
           //in = new FileInputStream(localFilePath);
            boolean isExcel2003 = true;
            if (isExcel2007(localFilePath)) {
                isExcel2003 = false;
            }
            if (isExcel2003) {
                workBook = new HSSFWorkbook(in);
            } else {
                workBook = new XSSFWorkbook(in);
            }
            Map<Integer, String> columnOrderNameMap = new HashMap<Integer, String>();
            Sheet sheet = workBook.getSheetAt(0);
            Row head = sheet.getRow(0);
            // 获取到Excel文件中的所有的列
            int headCellCount = head.getPhysicalNumberOfCells();
            for (int j = 0; j < headCellCount; j++) {
                // 获取到列的值
                Cell cell = head.getCell(j);
                columnOrderNameMap.put(j, cell.getStringCellValue());
            }
            // 获取到Excel文件中的所有行数
            int rowCount = sheet.getPhysicalNumberOfRows();
            // 遍历行
            for (int i = 1; i < rowCount; i++) {
                // 读取左上端单元格
                Row row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    Map<String, Object> columnDataMap = new HashMap<String, Object>();
                    // 获取到Excel文件中的所有的列
                    //int cellCount = row.getPhysicalNumberOfCells();
                    // 遍历列
                    for (int j = 0; j < headCellCount; j++) {
                        Object value = null;
                        String key = columnOrderNameMap.get(j);
                        // 获取到列的值
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                // 公式
                                case Cell.CELL_TYPE_FORMULA:
                                    value = "";
                                    break;
                                // 数据
                                case Cell.CELL_TYPE_NUMERIC:
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                        // 如果是date类型则 ，获取该cell的date值
                                        value = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                                    } else {
                                        // excel格式不正确的情况下会出现null
                                        if (StringUtils.isEmpty(key))
                                            continue;
                                        value = cell.getNumericCellValue();
                                    }
                                    break;
                                // 字符串
                                case Cell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue().trim();
                                    break;
                                // 空值
                                case Cell.CELL_TYPE_BLANK:
                                    value = "";
                                    break;
                                // 故障
                                case HSSFCell.CELL_TYPE_ERROR:
                                    value = "非法字符";
                                    break;
                                // Boolean
                                case HSSFCell.CELL_TYPE_BOOLEAN:
                                    value = cell.getBooleanCellValue();
                                    break;
                                default:
                                    value = "未知类型";
                                    break;
                            }
                        }
                        // excel格式不正确的情况下会出现null
                        if (StringUtils.isEmpty(key))
                            continue;

                        columnDataMap.put(key, value);
                    }
                    sourceDataList.add(columnDataMap);
                }
            }
        } catch (Exception e) {
            log.error("读取Excel失败", e);
            throw new Exception("读取解析Excel失败", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("读取解析异常", e);
            }
        }

        return sourceDataList;
    }



    /**
     * 是否是2007的excel，返回true是2007
     *
     * @param filePath
     * @return boolean
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
}
