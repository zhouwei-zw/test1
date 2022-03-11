package com.example.demo.utils;

import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportWordUtils {

    public static void exportWord(String templatePath, String temDir, String fileName, Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){
        Assert.notNull(templatePath,"模板路径不能为空");
        Assert.notNull(temDir,"临时文件路径不能为空");
        Assert.notNull(fileName,"导出文件名不能为空");
        Assert.isTrue(fileName.endsWith(".docx"),"word导出请使用docx格式");
        if (!temDir.endsWith("/")){
            temDir+= File.separator;
        }
        File dir=new File(temDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            String userAgent=request.getHeader("user-agent").toLowerCase();
            if(userAgent.contains("msie") || userAgent.contains("like gecko")){
                fileName= URLEncoder.encode(fileName,"UTF-8");
            }
            else {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            }
//            XWPFDocument doc= WordExportUtil.exportWord07(templatePath, params);
            FileInputStream in=new FileInputStream(templatePath);
            HWPFDocument doc=new HWPFDocument(in);
            String tmpPath = temDir + fileName;
            FileOutputStream fos = new FileOutputStream(tmpPath);
            doc.write(fos);
            response.setContentType("application/force-download");

            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out=response.getOutputStream();
            doc.write(out);
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            delFileWord(temDir,fileName);
        }
    }
    public static void delFileWord(String filePath, String fileName){
        File file =new File(filePath+fileName);
        File file1 =new File(filePath);
        file.delete();
        file1.delete();
    }

    public static void replacetext(InputStream in,OutputStream out,Map<String,String> map){
        try {
            XWPFDocument document=new XWPFDocument(in);

            //1. 替换段落中的指定文字（本人模板中 对应的编号）
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            String text;
            Set<String> set;
            XWPFParagraph paragraph;
            List<XWPFRun> run;
            String key;
            while (itPara.hasNext()) {
                paragraph = itPara.next();
                set = map.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    run = paragraph.getRuns();
                    for (int i = 0, runSie = run.size(); i < runSie; i++) {
                        text = run.get(i).getText(run.get(i).getTextPosition());
                        if (text != null && text.equals(key)) {
                            run.get(i).setText(map.get(key), 0);
                        }
                    }
                }
            }
            //2. 替换表格中的指定文字（本人模板中 对应的姓名、性别等）
            Iterator<XWPFTable> itTable=document.getTablesIterator();
            XWPFTable table;
            int rowscount;
            while(itTable.hasNext()){
                XWPFParagraph p = document.createParagraph();
                XWPFRun headRun0 = p.createRun();
                table = itTable.next();
                rowscount = table.getNumberOfRows();
                for(int i=0;i<rowscount;i++){
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for(XWPFTableCell cell:cells){
                        for (Map.Entry<String, String> e : map.entrySet()){
                            if (cell.getText().equals(e.getKey())){
                                cell.removeParagraph(0);
                                XWPFParagraph xwpfParagraph = cell.addParagraph();
                                XWPFRun run1 = xwpfParagraph.createRun();
                                run1.setFontSize(11);
                                run1.setText(e.getValue());
                                CTTc cttc = cell.getCTTc();
                                CTTcPr ctPr = cttc.addNewTcPr();
                                ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                            }
                        }
                    }
                }
            }

            //  response.setContentType("application/force-download");
            //  response.addHeader("Content-Disposition", "attachment;fileName=" + "aaa.doc");
            //  OutputStream out=response.getOutputStream();
            document.write(out);
            System.out.println("shuchule");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void replacetextzx(InputStream in,OutputStream out,Map<String,String> map,HttpServletResponse response,String filename){
        try {
            XWPFDocument document=new XWPFDocument(in);

            //1. 替换段落中的指定文字（本人模板中 对应的编号）
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            String text;
            Set<String> set;
            XWPFParagraph paragraph;
            List<XWPFRun> run;
            String key;
            while (itPara.hasNext()) {
                paragraph = itPara.next();
                set = map.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    run = paragraph.getRuns();
                    for (int i = 0, runSie = run.size(); i < runSie; i++) {
                        text = run.get(i).getText(run.get(i).getTextPosition());
                        if (text != null && text.equals(key)) {
                            run.get(i).setText(map.get(key), 0);
                        }
                    }
                }
            }
            //2. 替换表格中的指定文字（本人模板中 对应的姓名、性别等）
            Iterator<XWPFTable> itTable=document.getTablesIterator();
            XWPFTable table;
            int rowscount;
            while(itTable.hasNext()){
                XWPFParagraph p = document.createParagraph();
                XWPFRun headRun0 = p.createRun();
                table = itTable.next();
                rowscount = table.getNumberOfRows();
                for(int i=0;i<rowscount;i++){
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for(XWPFTableCell cell:cells){
                        for (Map.Entry<String, String> e : map.entrySet()){
                            if (cell.getText().equals(e.getKey())){
                                cell.removeParagraph(0);
                                XWPFParagraph xwpfParagraph = cell.addParagraph();
                                XWPFRun run1 = xwpfParagraph.createRun();
                                run1.setFontSize(11);
                                run1.setText(e.getValue());
                                CTTc cttc = cell.getCTTc();
                                CTTcPr ctPr = cttc.addNewTcPr();
                                ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                            }
                        }
                    }
                }
            }
            filename=URLEncoder.encode(filename,"UTF-8");
              response.setContentType("application/force-download");
              response.addHeader("Content-Disposition", "attachment;fileName=" + filename);
              OutputStream out1=response.getOutputStream();
              document.write(out1);
           // document.write(out);
            System.out.println("word导出成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void replacetextzx1(InputStream in, Map<String,String> map, HttpServletResponse response, String filename){
        try {
            XWPFDocument document=new XWPFDocument(in);

            //1. 替换段落中的指定文字（本人模板中 对应的编号）
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            String text;
            Set<String> set;
            XWPFParagraph paragraph;
            List<XWPFRun> run;
            String key;
            while (itPara.hasNext()) {
                paragraph = itPara.next();
                set = map.keySet();
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    run = paragraph.getRuns();
                    for (int i = 0, runSie = run.size(); i < runSie; i++) {
                        text = run.get(i).getText(run.get(i).getTextPosition());
                        if (text != null && text.equals(key)) {
                            run.get(i).setText(map.get(key), 0);
                        }
                    }
                }
            }
            //2. 替换表格中的指定文字（本人模板中 对应的姓名、性别等）
            Iterator<XWPFTable> itTable=document.getTablesIterator();
            XWPFTable table;
            int rowscount;
            while(itTable.hasNext()){
                XWPFParagraph p = document.createParagraph();
                XWPFRun headRun0 = p.createRun();
                table = itTable.next();
                rowscount = table.getNumberOfRows();
                for(int i=0;i<rowscount;i++){
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for(XWPFTableCell cell:cells){
                        for (Map.Entry<String, String> e : map.entrySet()){
                            if (cell.getText().equals(e.getKey())){
                                cell.removeParagraph(0);
                                XWPFParagraph xwpfParagraph = cell.addParagraph();
                                XWPFRun run1 = xwpfParagraph.createRun();
                                run1.setFontSize(11);
                                run1.setText(e.getValue());
                                CTTc cttc = cell.getCTTc();
                                CTTcPr ctPr = cttc.addNewTcPr();
                                ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                            }
                        }
                    }
                }
            }
            filename= URLEncoder.encode(filename,"UTF-8");
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + filename);
            OutputStream out1=response.getOutputStream();
            document.write(out1);
            // document.write(out);
            System.out.println("shuchule");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
