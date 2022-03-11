package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Fileupload;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文件表(Fileupload)表服务接口
 *
 * @author makejava
 * @since 2021-07-08 15:15:21
 */
public interface FileuploadService extends IService<Fileupload> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<Fileupload> queryById(String id);

    List<Fileupload> queryById1(String id);

    void queryById2(Fileupload fileUpload);

    List<Fileupload> queryList();

    Fileupload uploadfile(MultipartFile file,String tablename, HttpServletRequest req) throws IOException;
}
