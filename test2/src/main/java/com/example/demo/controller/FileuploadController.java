package com.example.demo.controller;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.demo.dao.SqlCommon;
import com.example.demo.entity.Fileupload;
import com.example.demo.entity.ResultMsg;
import com.example.demo.entity.Vo.ListStructure;
import com.example.demo.service.FileuploadService;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文件表(Fileupload)表控制层
 *
 * @author makejava
 * @since 2021-07-08 15:15:23
 */
@Slf4j
@RestController
@RequestMapping("fileupload")
public class FileuploadController {
    /**
     * 服务对象
     */
    @Autowired
    private FileuploadService fileuploadService;

    @Value("${upload.ip}")
    public String uploadPath1;

//    @Autowired
//    private MinioClient minioClient;

    @Value(value = "${minio.bucketName}")
    public String bucketName;


    @Autowired
    private SqlCommon sqlCommon;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public List<Fileupload> selectOne(@RequestParam(name = "id",required = true) String id) {

        List<Fileupload> TEST=fileuploadService.queryById(id);

        return TEST;
    }

    @GetMapping("selectTwo")
    public List<Fileupload> selectTwo(@RequestParam(name = "id",required = true) String id) {
//             读取另一个数据库的表
        List<Fileupload> TEST=fileuploadService.queryById1(id);

        return TEST;
    }

    @PostMapping("add")
//    @DS("slave") 在controller层设置@ds无效
    public String add(@RequestBody Fileupload fileupload) {

        fileuploadService.queryById2(fileupload);
        return "添加成功";
    }

    @DeleteMapping("delete")
//    @DS("slave") 在controller层设置@ds无效
    public String delete(@RequestParam(value = "id",required = true) String id) {

        fileuploadService.removeById(id);
        return "删除成功";
    }


    @DeleteMapping("deleteBatch")
//    @DS("slave") 在controller层设置@ds无效
    public String deleteBatch(@RequestParam(value = "ids",required = true) String ids) {

       // fileuploadService.removeById(id);
        fileuploadService.removeByIds(Arrays.asList(ids.split(",")));
        return "批量删除成功";
    }

    @PutMapping("edit")
//    @DS("slave") 在controller层设置@ds无效
    public String edit(@RequestBody Fileupload fileupload) {

        fileuploadService.updateById(fileupload);
        return"编辑成功";
    }


    @PutMapping("edit1")
//    @DS("slave") 在controller层设置@ds无效
    public ResultMsg edit1(@RequestBody Fileupload fileupload) {

        fileuploadService.updateById(fileupload);
        return ResultMsg.ok("编辑成功");
    }


    @GetMapping("selectOne4")
    public ResultMsg selectOne4() {

        List<Fileupload> TEST=fileuploadService.queryList();

        return ResultMsg.ok(TEST);
    }
//文件上传
    @PostMapping("uploadfile")
    public ResultMsg uploadfile(@RequestParam("file") MultipartFile file,
                                @RequestParam(required = true) String tablename
                                , HttpServletRequest req) throws IOException {
        Fileupload fi=fileuploadService.uploadfile(file,tablename,req);
        return ResultMsg.ok(fi);
    }


    @PostMapping("/download")
    public void download(HttpServletResponse rep,String id){
        Fileupload fileupload=fileuploadService.getById(id);

    }


//    excel导入
    @PostMapping("/excelupload")
    public String upload(MultipartFile file,HttpServletRequest request,String DBTag,String tablename) throws IOException {
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

        DynamicDataSourceContextHolder.push(DBTag);
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
              String id=UUID.randomUUID().toString().replace("-","");
              map1.put("id",id);
            for(int cellindex=0;cellindex<hssfRow.getPhysicalNumberOfCells();cellindex++){

                HSSFCell titlecell=titlerow.getCell(cellindex);
                HSSFCell hsscell=hssfRow.getCell(cellindex);

                for(Map<String, Object> map33:filedlist){
                    if(map33.containsKey("COLUMN_COMMENT")){
                        if(map33.get("COLUMN_COMMENT").toString().equals(titlecell.toString())){
                            String name1=map33.get("COLUMN_NAME").toString();
                            String name2=map33.get("COLUMN_COMMENT").toString();
                            if(!fieldlist1.contains(name2)){
                                return "表字段"+name2+"不在数据库表描述上";
                            }
                            map1.put(name1,hsscell.toString());
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
        return "导入成功";
    }
}
