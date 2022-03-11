package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.DateConvertUtil;
import com.example.demo.utils.HttpClientHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("weather")
public class WeatherController {
    @GetMapping("standred")
    public Map<String, Object> weather(@RequestParam(name="province",required=false)String province,
                                       @RequestParam(name="city",required=false)String city,
                                       @RequestParam(name="county",required=false)String county){
        String url="https://wis.qq.com/weather/common?source=pc&weather_type=observe|forecast_24h|air&province="+province+"&city="+city+"&county="+county;
        url=url.replace("|","%7C");
        String result= HttpClientHelper.httpGet2(url);
        JSONObject jso=JSONObject.parseObject(result);
        Map<String,Object> map1=new HashMap<>();
        JSONObject data=JSONObject.parseObject(jso.getString("data"));
        String qulity=JSONObject.parseObject(data.getString("air")).getString("aqi");
        String tem=JSONObject.parseObject(data.getString("observe")).getString("degree");
        String wea=JSONObject.parseObject(data.getString("observe")).getString("weather");
        map1.put("qulity",qulity);
        map1.put("tem",tem);
        map1.put("wea",wea);
        String forecast1=JSONObject.parseObject(data.getString("forecast_24h")).getString("1");
        List<Map<String,Object>> list1=new ArrayList<>();
        //   Map<String,Object>  map2=new HashMap<>();
        for(int i=1;i<6;i++){
            Map<String,Object>  map2=new HashMap<>();
            String forecast2=JSONObject.parseObject(data.getString("forecast_24h")).getString(String.valueOf(i));
            String name1=JSONObject.parseObject(forecast2).getString("time");
            String name= DateConvertUtil.dateToWeek(name1);
            String maxdegree=JSONObject.parseObject(forecast2).getString("max_degree");
            String mindegree=JSONObject.parseObject(forecast2).getString("min_degree");
            String minmax=mindegree+"℃～"+maxdegree+"℃";
            String xType=JSONObject.parseObject(forecast2).getString("day_weather");
            map2.put("name",name);
            map2.put("minmax",minmax);
            map2.put("xType",xType);
            list1.add(map2);
        }
        map1.put("data",list1);
        return map1;
    }
}
