package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.demo.dao.SqlCommon;
import com.example.demo.utils.ExportWordUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/export")
public class WordExportController {

    @Autowired
    private SqlCommon sqlCommon;

    @RequestMapping("/wordexport")
    public void export(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map1=new HashMap<>();
        map1.put("name","周伟");
        map1.put("sex","男");
        ExportWordUtils.exportWord("E://test//export.docx","E:/test","aaa.docx",map1,request,response);
    }

    @RequestMapping("/wordexport1")
    public void export1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tmpurl="E:\\test\\test.doc";
        String expurl="E:\\test\\aaa.doc";
        Map<String,Object> map1=new HashMap<>();
        map1.put("name","周伟");
        map1.put("sex","男");

        InputStream in=new FileInputStream(tmpurl);
        HWPFDocument doc=new HWPFDocument(in);

        FileOutputStream fos = new FileOutputStream(expurl);
        doc.write(fos);
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + "aaa.doc");
        OutputStream out=response.getOutputStream();
        doc.write(out);
        in.close();
        out.close();
    }


    //广西钦州资产管理导出功能
    @RequestMapping("/wordexport3")
    public void export3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tmpurl="E:\\test\\gxqz_zcgl\\幸福小镇住房租赁合同模板.docx";
        String expurl="E:\\test\\gxqz_zcgl\\aaa321.docx";
        String filename="";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        java.util.Date date=new java.util.Date();
        String date1=sdf.format(date);

        DynamicDataSourceContextHolder.push("GXQZ_ZCGL");
        String ids=request.getParameter("id");
        String sqls="select * from jy_ht_sp where id='"+ids+"'";
        List<Map<String, Object>> res=sqlCommon.sql(sqls);
        Map<String,String> map2=new HashMap<>();
        for (Map<String,Object> mapss:res){
            String htlx=mapss.get("itemtype").toString();
            for(Map.Entry<String,Object> entry:mapss.entrySet()){
                if(entry.getValue() instanceof Date){
                    map2.put(entry.getKey(),entry.getValue().toString());
                }else if(entry.getValue() instanceof BigDecimal){
                    map2.put(entry.getKey(),entry.getValue().toString());
                }else if(entry.getValue() instanceof Integer){
                    map2.put(entry.getKey(),entry.getValue().toString());
                }else {
                    map2.put(entry.getKey(),(String)entry.getValue());
                }
            }
            if(htlx.equals("商铺租赁")){
                filename="商铺租赁"+date1+".doc";
                tmpurl="E:\\test\\gxqz_zcgl\\中马钦州产业园区商铺租赁协议.docx";
                expurl="E:\\test\\gxqz_zcgl\\aaa321商铺租赁.docx";
                String ss=(String) mapss.get("leaseterm");
                Integer leaseterm=ss==null?0:Integer.parseInt(ss)/12;
                map2.put("leaseterm/12",leaseterm.toString());

            }
            if(htlx.equals("住宅租赁")){
                tmpurl="E:\\test\\gxqz_zcgl\\幸福小镇住房租赁合同模板.docx";
                expurl="E:\\test\\gxqz_zcgl\\aaa321住宅租赁.docx";
                String ss=(String) mapss.get("monthrent");
                Double monthrent=ss==null?0:Double.parseDouble(ss)*12;
                map2.put("monthrent*12",monthrent.toString());
            }
        }

        DynamicDataSourceContextHolder.clear();

        InputStream inputStream = new FileInputStream(tmpurl);
        FileOutputStream outputStream = new FileOutputStream(expurl);
        ExportWordUtils.replacetextzx(inputStream,outputStream,map2,response,filename);
        inputStream.close();
        outputStream.close();
    }

   //参数DbTag、sql、id、filename，适用于通用版单表导入。word格式
    @RequestMapping("/wordexportzx")
    public void export4(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tmpurl="";
        String filename="";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        java.util.Date date=new java.util.Date();
        String date1=sdf.format(date);
        String dbtag=request.getParameter("DbTag");
        String sql1=request.getParameter("sql");
        String ids=request.getParameter("id");
        String file1=request.getParameter("filename");
        DynamicDataSourceContextHolder.push(dbtag.toUpperCase());
        String sqls=sql1+" WHERE ID='"+ids+"'";
        List<Map<String, Object>> res=sqlCommon.sql(sqls);
        Map<String,String> map2=new HashMap<>();
        for (Map<String,Object> mapss:res){
            //   String htlx=mapss.get("itemtype").toString();
            for(Map.Entry<String,Object> entry:mapss.entrySet()){
                if(entry.getValue() instanceof Date){
                    map2.put(entry.getKey(),entry.getValue().toString());
                }else if(entry.getValue() instanceof BigDecimal){
                    map2.put(entry.getKey(),entry.getValue().toString());
                }else if(entry.getValue() instanceof Integer){
                    map2.put(entry.getKey(),entry.getValue().toString());
                }else {
                    map2.put(entry.getKey(),(String)entry.getValue());
                }
            }
            filename=file1+date1+".doc";
            tmpurl="D:\\java\\gxqz_zcgl\\template\\"+file1+".docx";
        }

        DynamicDataSourceContextHolder.clear();

        InputStream inputStream = new FileInputStream(tmpurl);

        ExportWordUtils.replacetextzx1(inputStream,map2,response,filename);
        inputStream.close();
    }

}
