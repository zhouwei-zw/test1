package com.example.demo.controller;


import com.example.demo.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/redisincre")
public class RedisIncreController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/incre")
    public String increment(@RequestParam(name = "prefix") Integer prefix,@RequestParam(name = "name") String name,@RequestParam(name = "date") Boolean date){
        String date2="";
        if(date==true){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        date2=dateFormat.format(new Date());
        }
        String name1="incre"+date2+name;
        boolean boolkey=redisUtil.hasKey(name1);
        Long ss=redisUtil.incr(name1,1);
        String ssq=String.valueOf(ss);
        Integer ss1=prefix-ssq.length();
        String prfix1="";
        for(int i=0;i<ss1;i++){
            prfix1+="0";
        }
        String ss2=name+date2+prfix1+ssq;
        return ss2;
    }
}
