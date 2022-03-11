package com.example.demo.controller;


import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.SqlCommon;
import com.example.demo.entity.ResultMsg;
import com.example.demo.entity.Testbm;
import com.example.demo.mapper.TestbmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/testbm")
public class TestbmController {

    @Autowired
    private SqlCommon sqlCommon;

    @Autowired
    private TestbmMapper testbmMapper;

    @GetMapping("testdate")
    public ResultMsg testdate(){
        String SQL1="SELECT * FROM testbm";
        List<Map<String, Object>> res=sqlCommon.sql(SQL1);
        return ResultMsg.ok(res);
    }

    @GetMapping("testdate1")
    public ResultMsg testdate1(){

        List<Testbm> res=testbmMapper.getList();
        return ResultMsg.ok(res);
    }

    @GetMapping("test2")
    public LinkedHashMap<String,Object> test2(){

        LinkedHashMap<String,Object> OBJ=new LinkedHashMap<>();
        DynamicDataSourceContextHolder.push("tz_hlq");
        String sql1="call UP_DSJ_JJFZ_RCFL";
        List<LinkedHashMap<String, Object>> result = sqlCommon.sqlLinked(sql1);
        DynamicDataSourceContextHolder.clear();
        List<String> xAxis = new ArrayList<>();
        for(LinkedHashMap<String, Object> map: result) {
            String key1= map.values().stream().findFirst().toString();
            String key2= map.keySet().stream().findFirst().toString();
            String key3= map.entrySet().stream().findFirst().toString();
           // String key4= map.get(0).toString();
            int i=0;
            for(String ada:map.keySet()){
                if (i==0){
                    xAxis.add(null==map.get(ada)?"":map.get(ada).toString());
                }
                i++;
            }
         //   xAxis.add(null == key1 ? "": key1);
        }
        OBJ.put("aaa",xAxis);
        return OBJ;
    }

}

