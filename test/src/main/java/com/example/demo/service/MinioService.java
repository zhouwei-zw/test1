package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Fileupload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface MinioService extends IService<Fileupload> {
    Fileupload uploadfile(MultipartFile file, String appid, String filename, String tablename,String id);

    boolean deleteByFileId(String fileId);
}
