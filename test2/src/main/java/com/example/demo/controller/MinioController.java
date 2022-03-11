package com.example.demo.controller;

import com.baomidou.dynamic.datasource.toolkit.StringUtils;
import com.example.demo.entity.Fileupload;
import com.example.demo.entity.ResultMsg;
import com.example.demo.service.MinioService;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("minio")
public class MinioController {
    @Autowired
    private MinioService minioService;
    @PostMapping("uploadfile")
    public ResultMsg uploadfile(@RequestParam("file")MultipartFile file,
                                @RequestParam String appid,
                                @RequestParam(required = false) String filename,
                                @RequestParam String tablename,
                                @RequestParam String id){
        if(StringUtils.isBlank(filename)) {
            filename=file.getOriginalFilename();
        }
        Fileupload fileupload=minioService.uploadfile(file,appid,filename,tablename,id);

        return ResultMsg.ok(fileupload);
    }

    @GetMapping("/delete")
    public ResultMsg delete( @RequestParam String id) {
        minioService.deleteByFileId(id);
        return ResultMsg.ok("1");
    }
}
