package com.ltsznh.common.utils.excel;

import com.ltsznh.common.exception.FamilyException;
import com.ltsznh.common.utils.DateUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.charts.XSSFDateAxis;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * Excel操作
 *
 * @author liutao
 * @email ltsznh@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class ExcelUtils {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private String filePath = "C:\\Users\\lt\\Desktop\\采购导入表.xlsx";

    private Workbook workbook;
    private CellStyle titleCellStyle;//设置标题行单元格格式
    private CellStyle dataCellStyle;// 设置数据内容单元格格式

    public ExcelUtils(SXSSFWorkbook workbook) {
        this.workbook = workbook;
        getTitleCellStyle();
        getDataCellStyle();
    }

    public ExcelUtils() {
    }

    public Workbook generateHSSFWorkbook(List<Map<String, Object>> list, String title) {
        try {
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            SXSSFSheet sheet = workbook.createSheet();

            //填充表头header
            SXSSFRow row = sheet.createRow(1);
            SXSSFCell cell = row.createCell(0);
            cell.setCellValue("test" + list.get(0).get(""));

            File file = new File("d:/sxssf.xlsx");
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.flush();
            out.close();

            return workbook;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Workbook getWorkbook(File file) {
        try {
            String fileExtension = FilenameUtils.getExtension(file.getName());
            if (fileExtension.equalsIgnoreCase("xls")) {//2000格式
                workbook = new HSSFWorkbook(new FileInputStream(file));
            } else {//2007格式
                workbook = new XSSFWorkbook(file);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        return workbook;
    }

    public CellStyle getDataCellStyle() {
        return getDataCellStyle(workbook);
    }

    public CellStyle getDataCellStyle(Workbook workbook) {
        dataCellStyle = workbook.createCellStyle();//设置数据内容单元格格式

        dataCellStyle.setWrapText(true);

        Font cellFont = workbook.createFont();
        cellFont.setFontHeightInPoints((short) 10);

        dataCellStyle.setFont(cellFont);
        return dataCellStyle;
    }

    public CellStyle getTitleCellStyle() {
        return getTitleCellStyle(workbook);
    }

    public CellStyle getTitleCellStyle(Workbook workbook) {
        titleCellStyle = workbook.createCellStyle();//设置标题行单元格格式

        titleCellStyle.setWrapText(true);


        Font cellFont = workbook.createFont();
        cellFont.setFontHeightInPoints((short) 11);

        titleCellStyle.setFont(cellFont);
        return titleCellStyle;
    }

    public void autoSetColumnWidth(Sheet sheet, SXSSFCell cell) {
        try {
            String str = cell.getStringCellValue();
            if (str != null) {
                // 自适应中英文列宽
                int strLength = str.getBytes("GBK").length > 50 ? 50 : str.getBytes("GBK").length;
                int with;
                if (cell.getRowIndex() == 0) {
                    with = strLength * 256;
                    cell.setCellStyle(dataCellStyle);
                } else {
                    with = strLength * 250;
                    cell.setCellStyle(titleCellStyle);
                }
                if (with > sheet.getColumnWidth(cell.getColumnIndex()))
                    sheet.setColumnWidth(cell.getColumnIndex(), with);
            }
        } catch (FamilyException e) {
            logger.error(this.getClass().toString(), e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error(this.getClass().toString(), e.getMessage());
        }
    }

    public String getStringCellValue(Row row, int col) {
        String value = null;
        Cell cell = row.getCell(col);
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellTypeEnum();
        if (cellType.equals(CellType.STRING)) {
            value = cell.getStringCellValue();
        } else if (cellType.equals(CellType.BOOLEAN)) {
            value = String.valueOf(cell.getBooleanCellValue());
        } else if (cellType.equals(CellType.FORMULA)) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                try {
                    if (cell.getDateCellValue() == null) return null;
                    value = DateUtils.format(cell.getDateCellValue(), DateUtils.DATE_TIME_PATTERN);
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e.getCause());
                }
            } else {
                value = String.valueOf(cell.getNumericCellValue());
                if (StringUtils.isBlank(value)) value = "0";
            }
        } else if (cellType.equals(CellType.ERROR)) {
            value = String.valueOf(cell.getErrorCellValue());
        } else if (cellType.equals(CellType.NUMERIC)) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                try {
                    if (cell.getDateCellValue() == null) return null;
                    value = DateUtils.format(cell.getDateCellValue(), DateUtils.DATE_TIME_PATTERN);
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage(), e.getCause());
                }
            } else {
                try {
                    value = String.valueOf(cell.getNumericCellValue());
                    if (StringUtils.isBlank(value)) value = "0";
                } catch (Exception e) {
                    value = String.valueOf(cell.getStringCellValue());
                }
            }

        } else if (cellType.equals(CellType.STRING)) {
            value = String.valueOf(cell.getRichStringCellValue());
        } else if (cellType.equals(CellType.BLANK)) {
            value = null;
        }
        return value;
    }

    public static void main(String[] args) {
        ExcelUtils excelUtils = new ExcelUtils();
        Workbook workbook = excelUtils.getWorkbook(new File(excelUtils.filePath));
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        row = sheet.getRow(2);
        System.out.println(excelUtils.getStringCellValue(row, 0));
    }
}
