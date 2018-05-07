package com.ltsznh.common.utils.word;

import com.ltsznh.common.exception.FamilyException;
import freemarker.ext.beans.HashAdapter;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

/**
 * Excel操作
 *
 * @author liutao
 * @email ltsznh@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class WordUtils {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private final String replacementChar = "替";

    private String filePath = "C:\\Users\\lt\\Desktop\\test.docx";
    private String outFilePath = "C:\\Users\\lt\\Desktop\\test11.docx";
    private XWPFDocument document;

    public WordUtils() {
        try {
            //解析docx模板并获取document对象
            document = new XWPFDocument(POIXMLDocument.openPackage(filePath));
            //获取整个文本对象
            List<XWPFParagraph> allParagraph = document.getParagraphs();
            //获取整个表格对象
            List<XWPFTable> allTable = document.getTables();
            //获取图片对象
            XWPFPictureData pic = document.getPictureDataByID("PICId");

//测试
//            //获取XWPFRun对象输出整个文本内容
//            StringBuffer tempText = new StringBuffer();
//            for (XWPFParagraph xwpfParagraph : allParagraph) {
//                List<XWPFRun> runList = xwpfParagraph.getRuns();
//                for (XWPFRun xwpfRun : runList) {
//                    tempText.append(xwpfRun.toString());
//                }
//            }
//            System.out.println(tempText.toString());

//            //获取word中表格信息，并显示出来
//            StringBuffer tableText = new StringBuffer();
//            for (XWPFTable xwpfTable : allTable) {
//                //获取表格行数据
//                List<XWPFTableRow> rows = xwpfTable.getRows();
//                for (XWPFTableRow xwpfTableRow : rows) {
//                    //获取表格单元格数据
//                    List<XWPFTableCell> cells = xwpfTableRow.getTableCells();
//                    for (XWPFTableCell xwpfTableCell : cells) {
//                        List<XWPFParagraph> paragraphs = xwpfTableCell.getParagraphs();
//                        for (XWPFParagraph xwpfParagraph : paragraphs) {
//                            List<XWPFRun> runs = xwpfParagraph.getRuns();
//                            for(int i = 0; i < runs.size();i++){
//                                XWPFRun run = runs.get(i);
//                                tableText.append(run.toString());
//                            }
//                        }
//                    }
//                }
//            }
//            System.out.println(tableText.toString());

//            //获取首行run对象
//            XWPFRun run = allParagraph.get(0).getRuns().get(0);
//            //设置文本内容
//            run.setText("修改了的word");
//            //生成新的word
//            File file = new File(outFilePath);
//
//            FileOutputStream stream = new FileOutputStream(file);
//            document.write(stream);
//            stream.close();
            Map<String, String> textMap = new HashMap<>();
            textMap.put("订单号", "test20180327");
            textMap.put("客户名称", "漯河双汇");
            textMap.put("一", "已换项目1");
            textMap.put("二", "已换项目1");
            textMap.put("三", "已换项目1");
            textMap.put("四", "已换项目1");

            for (XWPFParagraph paragraph : allParagraph) {
                //判断此段落时候需要进行替换
                String text = paragraph.getText();
                if (isContainReplacement(text)) {
                    List<XWPFRun> runs = paragraph.getRuns();
                    for (XWPFRun run : runs) {
                        //替换模板原来位置
                        run.setText(changeReplacementValue(run.toString(), textMap), 0);
                    }
                }
            }


            List<String[]> tableList = new ArrayList<>();
            tableList.add(new String[]{"1", "2", "3", "4", "5"});
            tableList.add(new String[]{"6", "7", "8", "9", "10"});
            tableList.add(new String[]{"7", "7", "8", "9", "10"});
            tableList.add(new String[]{"8", "7", "8", "9", "10"});
            tableList.add(new String[]{"9", "7", "8", "9", "10"});

            for (int i = 0; i < allTable.size(); i++) {
                //只处理行数大于等于2的表格，且不循环表头
                XWPFTable table = allTable.get(i);
                if (table.getRows().size() > 1) {
                    //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                    if (isContainReplacement(table.getText())) {
                        List<XWPFTableRow> rows = table.getRows();
                        //遍历表格,并替换模板
                        changeReplacementValueEachRow(rows, textMap);
                    } else {
//                  System.out.println("插入"+table.getText());
                        insertTableRow(table, tableList);
                    }
                }
            }


            //生成新的word
            File file = new File(outFilePath);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();





        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void insertTableRow(XWPFTable table, List<String[]> tableList) {
        //创建行,根据需要插入的数据添加新行，不处理表头
        for (int i = table.getRows().size() - 1; i < tableList.size(); i++) {
            XWPFTableRow row = table.createRow();
        }
        //遍历表格插入数据
        List<XWPFTableRow> rows = table.getRows();
        for (int i = 1; i < rows.size() && i <= tableList.size(); i++) {
            XWPFTableRow newRow = table.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for (int j = 0; j < cells.size() && j < tableList.get(0).length; j++) {
                XWPFTableCell cell = cells.get(j);
                cell.setText(tableList.get(i - 1)[j]);
            }
        }

    }

    /**
     * 逐个表格内容替换
     *
     * @param rows
     * @param textMap
     */
    public void changeReplacementValueEachRow(List<XWPFTableRow> rows, Map<String, String> textMap) {
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if (isContainReplacement(cell.getText())) {
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            run.setText(changeReplacementValue(run.toString(), textMap), 0);
                        }
                    }
                }
            }
        }
    }

    public String changeReplacementValue(String value, Map<String, String> textMap) {
        Set<Entry<String, String>> textSets = textMap.entrySet();
        for (Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
//            String key = "${" + textSet.getKey() + "}";
            String key = replacementChar + textSet.getKey();
            if (value.indexOf(key) != -1) {
                value = textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
//        if (isContainReplacement(value)) {
//            value = "";
//        }
        return value;
    }


    private boolean isContainReplacement(String text) {
//        return true;
        if (text != null && text.indexOf(replacementChar) >= 0) return true;
        else return false;
    }

    public static void main(String[] args) {
        WordUtils word = new WordUtils();
    }

}
