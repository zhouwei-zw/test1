package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.demo.dao.SqlCommon;
import com.example.demo.entity.ResultMsg;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("excelimport")
public class ExcelImportController {

    @Autowired
    private SqlCommon sqlCommon;

    //excel导入
    @PostMapping("/excelupload")
    public ResultMsg upload(MultipartFile file, HttpServletRequest request, String DBTag, String tablename) throws IOException {
        //导入的文件流
        InputStream in=null;
        try {
            in=file.getInputStream();
        }catch (IOException e){
            e.printStackTrace();
        }
        //xls格式的excel
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook(in);
        HSSFSheet sheet1=hssfWorkbook.getSheetAt(0);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        HSSFRow titlerow=sheet1.getRow(0);
        //表头集合
        List<String> title1=new ArrayList<>();

        DynamicDataSourceContextHolder.push(DBTag.toUpperCase());
        //SQL语句判断表结构
        String sql1="SELECT\n" +
                "    A.TABLE_SCHEMA,\n" +
                "    A.TABLE_NAME,\n" +
                "    A.COLUMN_NAME,\n" +
                "    (case when A.COLUMN_COMMENT is null or A.COLUMN_COMMENT='' then A.COLUMN_NAME ELSE A.COLUMN_COMMENT end) as COLUMN_COMMENT \n" +
                "FROM INFORMATION_SCHEMA.COLUMNS A\n" +
                "WHERE A.TABLE_SCHEMA='"+DBTag+"' and TABLE_NAME='"+tablename+"'\n" +
                "ORDER BY A.TABLE_SCHEMA,A.TABLE_NAME,A.ORDINAL_POSITION ";
        List<Map<String, Object>> filedlist=sqlCommon.sql(sql1);
        String fieldlist1="";
        //表字段值集合
        for(Map<String, Object> map33:filedlist){
            if(map33.containsKey("COLUMN_COMMENT")){
                fieldlist1+=map33.get("COLUMN_COMMENT").toString()+",";
            }
        }
        //excel生成list对象
        for(int rowindex=1;rowindex<sheet1.getPhysicalNumberOfRows();rowindex++){
            HSSFRow hssfRow=sheet1.getRow(rowindex);
            if(hssfRow == null){
                continue;
            }

            Map<String,Object> map1=new LinkedHashMap<String,Object>();
            String id= UUID.randomUUID().toString().replace("-","");
            map1.put("id",id);
            for(int cellindex=0;cellindex<hssfRow.getPhysicalNumberOfCells();cellindex++){

                HSSFCell titlecell=titlerow.getCell(cellindex);
                HSSFCell hsscell=hssfRow.getCell(cellindex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                if("联系电话".equals(titlecell.toString())) {
                    System.out.println(hsscell.toString());
                }
                if(!fieldlist1.contains(titlecell.toString())){
                    return ResultMsg.serverError("excel表字段"+titlecell.toString()+"不在数据库表描述上");
                }
                for(Map<String, Object> map33:filedlist){
                    if(map33.containsKey("COLUMN_COMMENT")){
                        if(map33.get("COLUMN_COMMENT").toString().equals(titlecell.toString())){
                            String name1=map33.get("COLUMN_NAME").toString();
                            String name2=map33.get("COLUMN_COMMENT").toString();
                            if(!fieldlist1.contains(name2)){
                                return ResultMsg.serverError("表字段"+name2+"不在数据库表描述上");
                            }
                            if(StrUtil.hasEmpty(hsscell.toString())){
                            }
                            else {
                                map1.put(name1, hsscell.toString());
                            }
                        }
                    }
                }
                title1.add(titlecell.toString());
            }
            list.add(map1);
        }
        //list拆分为map对象入库

        for (Map<String, Object> ms:list){
            sqlCommon.insertByMap(tablename,ms);
        }
        DynamicDataSourceContextHolder.clear();
        return ResultMsg.ok("导入成功");
    }

}
