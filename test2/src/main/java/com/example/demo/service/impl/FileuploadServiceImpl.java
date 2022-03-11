package com.example.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.config.UploadConfig;
import com.example.demo.entity.Fileupload;
import com.example.demo.dao.FileuploadDao;
import com.example.demo.service.FileuploadService;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件表(Fileupload)表服务实现类
 *
 * @author makejava
 * @since 2021-07-08 15:15:22
 */
@Service
@DS("master")
public class FileuploadServiceImpl extends ServiceImpl<FileuploadDao,Fileupload> implements FileuploadService {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    public List queryById(String id1){
//        return jdbcTemplate.queryForList("select * from fileupload where id=id1");
//    }
    @Autowired
    private UploadConfig uploadConfig;
//    private static String uploadPath1 = UploadConfig;
    @Autowired
    FileuploadDao fileuploadDao;

    @Value("${upload.ip}")
    public String uploadPath1;

    public List<Fileupload> queryById(String id1){
        List<Fileupload> qq11=fileuploadDao.getfileByid(id1);
        return qq11;
    }

//    切换数据源，换为附属数据库
    @DS("slave")
    public List<Fileupload> queryById1(String id1){
        QueryWrapper<Fileupload> qw=new QueryWrapper<>();
        qw.eq("id",id1);
        List<Fileupload> list1=this.list(qw);
        System.out.println(uploadPath1);
        return list1;
    }

    @DS("slave")
    public void queryById2(Fileupload fileUpload){

        this.save(fileUpload);
    }


    public List<Fileupload> queryList(){
        List<Fileupload> qlist=fileuploadDao.getList();
        return  qlist;
    }

    public Fileupload uploadfile(MultipartFile file, String tablename, HttpServletRequest req) throws IOException {
        if(file.isEmpty())
        {
            System.out.println("文件为空");
        }
        String fileName1 = file.getOriginalFilename();
        String suffixName=fileName1.substring(fileName1.lastIndexOf("."));
        String date1=new SimpleDateFormat("/yyyy/").format(new Date());
        //String filePath="E://photo//"+tablename;
//        String uploadPath1=uploadConfig.;
        String filePath=uploadPath1+tablename+date1;
        File files=new File(filePath+fileName1);
       // String realpath=req.getServletContext().getRealPath("")+tablename+fileName1;
      //  File file11=new File(filePath);
        if(!files.getParentFile().exists()){
            files.getParentFile().mkdirs();
        }
        file.transferTo(files);
//        Map<String,Object>  map=new HashMap<>();
        Fileupload fileupload111=new Fileupload();
        fileupload111.setUrl(filePath);
        fileupload111.setType(suffixName);
        fileupload111.setFilename(fileName1);

        this.save(fileupload111);
        fileupload111.setId(fileupload111.getId());
        return fileupload111;
    }
}
