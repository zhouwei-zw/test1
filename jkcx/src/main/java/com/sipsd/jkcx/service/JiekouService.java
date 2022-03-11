package com.sipsd.jkcx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sipsd.jkcx.model.JiekouEntity;
import com.sipsd.jkcx.util.JsonResult;
import com.sipsd.jkcx.vo.JiekouVo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface JiekouService extends IService<JiekouEntity> {
    void getAndSave(JiekouEntity jiekouEntity);

    JsonResult paging(JiekouVo jiekouVo);

    List<JiekouEntity> queryByMap(Map<String, Object> map);

    String uploadFileBufferToLocal(MultipartFile file, JiekouEntity jiekouEntity, String base64String);

    String fileDownload(@RequestParam String id, @RequestParam String type,HttpServletResponse response) throws UnsupportedEncodingException;
}
