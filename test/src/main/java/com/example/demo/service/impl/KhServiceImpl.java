package com.example.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.KhDao;
import com.example.demo.entity.KhKfmx;
import com.example.demo.entity.KhmxEntity;
import com.example.demo.service.KhService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KhServiceImpl extends ServiceImpl<KhDao,KhKfmx> implements KhService   {
    @Autowired
    private KhDao khDao;


    @DS("hdljfl")
    public List<Map<String,Object>> getkhlist(String table, String sqmc, String dwmc, String khrq){
        List<Map<String,String>> res=khDao.getkhList(table,sqmc,dwmc,khrq);
        List<Map<String,Object>> qq2= new ArrayList<>();
        for(Map<String,String> res1:res ){
            Map<String,Object> qq1= new HashMap<>();
           String mc= res1.get("dwmc");
           String time1= res1.get("khrq");
            String count1= res1.get("count");

           List<Map<String,String>> res2=khDao.getkhList2(table,sqmc,mc,time1);
            qq1.put("khdx",mc);
            qq1.put("khrq",time1);
            qq1.put("count",count1);
            qq1.put("data",res2);

          //  res1.put("data",res2);
            qq2.add(qq1);
        }
        return qq2;
    }


    @DS("hdljfl")
    @Transactional
    public String khdeit(List<KhmxEntity> khmxEntity){
//        List<Map<String,String>> res=khDao.getkhList(table);
//        List<Map<String,Object>> qq2= new ArrayList<>();
//        for(Map<String,String> res1:res ){
//            Map<String,Object> qq1= new HashMap<>();
//            String mc= res1.get("dwmc");
//            String time1= res1.get("khrq");
//            String count1= res1.get("count");
//
//            List<Map<String,String>> res2=khDao.getkhList2(table);
//            qq1.put("khdx",mc);
//            qq1.put("khrq",time1);
//            qq1.put("count",count1);
//            qq1.put("data",res2);
//
//            //  res1.put("data",res2);
//            qq2.add(qq1);
//        }

        List<KhKfmx> khKfmxList = new ArrayList<>();
        for (KhmxEntity khmxEntity1:khmxEntity )
        {
            KhKfmx khKfmx = new KhKfmx();
          //  khKfmx.setId(khmxEntity1.getId());

            BeanUtils.copyProperties(khmxEntity1,khKfmx);
            khKfmxList.add(khKfmx);
        }
        this.saveBatch(khKfmxList);
        return "x";
    }
}
