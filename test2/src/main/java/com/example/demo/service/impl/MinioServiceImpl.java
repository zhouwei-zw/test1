package com.example.demo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.FileuploadDao;
import com.example.demo.dao.MinioDao;
import com.example.demo.entity.Fileupload;
import com.example.demo.service.FileuploadService;
import com.example.demo.service.MinioService;
import com.example.demo.utils.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
@Service
public class MinioServiceImpl extends ServiceImpl<MinioDao,Fileupload> implements MinioService {
    public Fileupload uploadfile(MultipartFile file, String appid, String filename, String tablename,String id){
//        Fileupload fileupload=new Fileupload();
//        String uuid= UUID.randomUUID().toString();
//        String[] filenames=filename.split("\\.");
//        String name=filenames[0];
//        String type=filenames[filenames.length-1];
//        fileupload.setId(uuid);
//        fileupload.setPid(id);
//        fileupload.setFilename(filename);
//        String realpath=appid+tablename+id+filename;
        Fileupload fileupload= MinioUtil.minioupload(file,appid,filename,tablename,id);
        DynamicDataSourceContextHolder.push(appid.toUpperCase());
        this.save(fileupload);
        DynamicDataSourceContextHolder.clear();
        return fileupload;
    }

    public boolean deleteByFileId(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        Fileupload file = this.getById(id);
        this.removeById(id);
        String path=file.getPath().toString();
        String res=MinioUtil.deletefile(path);
        if(res.contains("失败")){
            return false;
        }
        return true;
    }
}
