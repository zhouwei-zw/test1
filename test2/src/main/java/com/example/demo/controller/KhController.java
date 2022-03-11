package com.example.demo.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.demo.entity.Fileupload;
import com.example.demo.entity.KhmxEntity;
import com.example.demo.entity.ResultMsg;
import com.example.demo.service.KhService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("Kh")
public class KhController {

    @Autowired
    private  KhService khService;

    @GetMapping("Qkh")
    @DS("hdljfl")
    public ResultMsg Qkh(@RequestParam(required = true) String table,
                                @RequestParam(required = false) String sqmc,
                                @RequestParam(required = false) String dwmc,
                                @RequestParam(required = false) String khrq
            , HttpServletRequest req) throws IOException {
       // Fileupload fi=fileuploadService.uploadfile(file,tablename,req);
       // if()
        List<Map<String,Object>> khres=khService.getkhlist(table,sqmc,dwmc,khrq);
        return ResultMsg.ok(khres);
    }


    @PostMapping("khedit")
    @DS("hdljfl")
    public ResultMsg khedit(@RequestParam(required = false) String table,
                            @RequestBody List<KhmxEntity> khmxEntity
            , HttpServletRequest req) throws IOException {

        String khres=khService.khdeit(khmxEntity);
        System.out.println(khmxEntity);

        return ResultMsg.ok("khres");
    }
}
