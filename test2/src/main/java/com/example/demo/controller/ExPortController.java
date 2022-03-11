package com.example.demo.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.demo.dao.SqlCommon;
import com.example.demo.entity.Vo.ListStructure;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ExPort")
public class ExPortController {

    @Autowired
    private SqlCommon sqlCommon;

    @SneakyThrows
    @GetMapping("v1")
    //excel导出
    public void ExcelPort(String tablename, String DbTag, String[] fieldname, HttpServletResponse response) throws UnsupportedEncodingException {
        DynamicDataSourceContextHolder.push(DbTag);
        //获取根据数据库和表名表描述
        String sql1="SELECT\n" +
                "    A.TABLE_SCHEMA,\n" +
                "    A.TABLE_NAME,\n" +
                "    A.COLUMN_NAME,\n" +
                "    (case when A.COLUMN_COMMENT is null or A.COLUMN_COMMENT='' then A.COLUMN_NAME ELSE A.COLUMN_COMMENT end) as COLUMN_COMMENT \n" +
                "FROM INFORMATION_SCHEMA.COLUMNS A\n" +
                "WHERE A.TABLE_SCHEMA='"+DbTag+"' and TABLE_NAME='"+tablename+"'\n" +
                "ORDER BY A.TABLE_SCHEMA,A.TABLE_NAME,A.ORDINAL_POSITION ";

        List<Map<String, Object>> filedtype=sqlCommon.sql(sql1);
        DynamicDataSourceContextHolder.clear();
        List<ListStructure> listStructure= JSONObject.parseArray(JSONArray.toJSONString(filedtype).toString(),ListStructure.class);
        List<String> filedlist=listStructure.stream().map(ListStructure::getCOLUMN_NAME).collect(Collectors.toList());
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        SimpleDateFormat sdf=new SimpleDateFormat("YYYYMMddHHmmss");

        String filename=tablename+sdf.format(new Date())+".xlsx";
        response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
        List<String> fname= Arrays.asList(fieldname);
        String dielname1="";
        for(String name1:fname){
            if (!filedlist.contains(name1)){
                throw new Exception("该表"+tablename+"没有相关字段");
            }
            dielname1+=name1+",";
        }
        dielname1=dielname1.substring(0,dielname1.length()-1);
        DynamicDataSourceContextHolder.push(DbTag);
        //可以加上where条件参数
        String sql2="select "+dielname1+" from "+tablename;
        List<LinkedHashMap<String, Object>> res1=sqlCommon.sqlLinked(sql2);
        DynamicDataSourceContextHolder.clear();

        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(tablename);
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCell cell = null;
        for(int i=0;i<fieldname.length;i++){
            cell = row.createCell(i);
            String rowname="";
            for(ListStructure sh:listStructure){
                if(sh.getCOLUMN_NAME().equals(fieldname[i])){
                     rowname=sh.getCOLUMN_COMMENT();
                }
            }
            cell.setCellValue(rowname);
            cell.setCellStyle(style);
        }
        for (int i = 0; i < res1.size(); i++) {
            row=sheet.createRow(i+1);
            LinkedHashMap<String, Object> mm = res1.get(i);
            List<String> values = new ArrayList<>();
            for(String key: mm.keySet()) {
                if(mm.get(key)==null){
                    values.add("NULL");
                }else {
                    values.add(mm.get(key).toString());
                }
//                values.add(mm.get(key).toString());
            }
            for(int j=0;j<res1.get(i).size();j++){
                    row.createCell(j).setCellValue(values.get(j));
            }
        }
        try {
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
