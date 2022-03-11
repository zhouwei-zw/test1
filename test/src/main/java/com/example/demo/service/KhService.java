package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Fileupload;
import com.example.demo.entity.KhKfmx;
import com.example.demo.entity.KhmxEntity;

import java.util.List;
import java.util.Map;

public interface KhService  extends IService<KhKfmx> {

    List<Map<String,Object>>  getkhlist(String table, String sqmc, String dwmc,String khrq);


    String  khdeit(List<KhmxEntity> khmxEntity);



}
