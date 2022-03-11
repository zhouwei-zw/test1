package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.RedisQEntity;
import com.example.demo.entity.ResultMsg;
import com.example.demo.service.XkVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("EditList")
@Api(description = "redis管理")
public class EditController {

    @Autowired
    XkVideoService xkVideoService;

    @GetMapping("redis")
    @ApiOperation(value = "河道巡查",notes = "对接河道巡查接口数据展示")
    public ResultMsg saveRiverpatrol(){
        List<Map<String,Object>> zzjg=xkVideoService.saveRiverpatrol();
        return ResultMsg.ok(zzjg);
    }

    @GetMapping("geojson")
    public JSONObject geojson(){
        JSONObject zzjg=xkVideoService.geojson();
        return zzjg;
    }

    @GetMapping("redisheader")
    @ApiOperation(value = "河道巡查",notes = "对接河道巡查接口数据展示")
    public ResultMsg saveRiverpatrol1(HttpServletRequest request){
//        if(request.getHeader("zw") == null) {
//           return ResultMsg.ok("缺少请求头");
//        }
//        String zhi = request.getHeader("zw");
//        if(!zhi.equals("zw123")){
//            return  ResultMsg.ok("密码错误");
//        }

        List<Map<String,Object>> zzjg=xkVideoService.saveRiverpatrol();
        return ResultMsg.ok(zzjg);
    }

    @GetMapping("redisQ")
//    @ApiOperation(value = "河道巡查",notes = "对接河道巡查接口数据展示")
    public ResultMsg redisQ(HttpServletRequest request){
        RedisQEntity redisQEntity=new RedisQEntity();
        Enumeration<String> paraNames=request.getParameterNames();
        Map<String,Object> map1=new HashMap<>();
        while (paraNames.hasMoreElements()){
            String key=paraNames.nextElement()+"";
            map1.put(key,request.getParameter(key));
            if("dbTag".equalsIgnoreCase(key)){
                redisQEntity.setDbTag(request.getParameter(key));
                continue;
            }
            if("keyname".equalsIgnoreCase(key)){
                redisQEntity.setKeyname(request.getParameter(key));
                continue;
            }
            if("dbTag".equalsIgnoreCase(key)){
                redisQEntity.setDbTag(request.getParameter(key));
                continue;
            }
            if("dbTag".equalsIgnoreCase(key)){
                redisQEntity.setDbTag(request.getParameter(key));
                continue;
            }

        }

        List<Map<String,Object>> zzjg=xkVideoService.saveRiverpatrol();
        return ResultMsg.ok(zzjg);
    }

}
