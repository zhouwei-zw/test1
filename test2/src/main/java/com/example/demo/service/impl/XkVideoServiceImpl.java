package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.XkVideoService;
import com.example.demo.utils.HttpClientHelper;
import com.example.demo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class XkVideoServiceImpl  implements XkVideoService {

    @Autowired
    RedisUtil redisUtil;

    private static List<Map<String, Object>> listmap1 = new ArrayList<>();

    private static JSONObject listmap12 ;

//    @Async("multithreadingExecutor")
    public List<Map<String,Object>> saveRiverpatrol() {
//        List<Map<String, Object>> listmap1 = new ArrayList<>();
        try{
            boolean flag=redisUtil.hasKey("redis1");
            if(flag==true){
               listmap1= (List<Map<String, Object>>)redisUtil.get("redis1");
               return listmap1;
            }
            else {
                long start = System.currentTimeMillis();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date data1 = new Date(System.currentTimeMillis());

                //获取前一天时间
                Date date2 = new Date();
                Calendar ca = Calendar.getInstance();
                ca.setTime(date2);
                //设置为前一天
                ca.add(Calendar.DAY_OF_MONTH, -1);
                date2 = ca.getTime();
                String sdate = formatter.format(date2);
                String edate = formatter.format(data1);
                System.out.println("定时执行:" + edate);
                String url = "https://m.appd9.com/wzqsl/api/open/login";
                String url1 = "https://m.appd9.com/wzqsl/api/open/getorderlist";
                JSONObject map1 = new JSONObject();
                map1.put("username", "user999");
                map1.put("password", "c61f93d9da98b19e314f2e363aaec55f");
                map1.put("t", String.valueOf(start));
                String res1 = HttpClientHelper.httpPost(url, map1);
                String jobj1 = JSONObject.parseObject(res1).getJSONObject("info").get("token").toString();
                JSONObject map2 = new JSONObject();
                map2.put("pagecount", "100");
                map2.put("pageno", "1");
                map2.put("startdate", sdate);
                map2.put("enddate", edate);
                map2.put("token", jobj1);
                String res2 = HttpClientHelper.httpPost(url1, map2);
                JSONArray jarr = JSONObject.parseObject(res2).getJSONObject("data").getJSONArray("detail");
//                List<Map<String, Object>> listmap1 = new ArrayList<>();
                jarr.stream().forEach(ele -> {
                    Map<String, Object> map = (JSONObject) ele;
                    listmap1.add(map);
                });
                redisUtil.set("redis1", listmap1, 20);
                return listmap1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return listmap1;
    }


    public JSONObject geojson() {
//        List<Map<String, Object>> listmap1 = new ArrayList<>();
        try{
            boolean flag=redisUtil.hasKey("redis3");
            if(flag==true){
                listmap12= (JSONObject)redisUtil.get("redis3");
                return listmap12;
            }
            else {
              String result= HttpClientHelper.httpMapGet("http://139.224.65.41:8080/geoserver/WZ_ZHXK/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=WZ_ZHXK:VM_Map_SPJK&outputFormat=application/json");
              JSONObject res1=JSONObject.parseObject(result);
              JSONArray ja1=res1.getJSONArray("features");
              for(Object oj:ja1){
                  JSONObject js2=(JSONObject)oj;
                  JSONObject js3=js2.getJSONObject("properties");
                  String daaress= js3.get("rtmp链接地址").toString();
                  String[] strs=daaress.split("@");
                  String rtmp1=strs[0].toString();
                  int len=rtmp1.length();
                  String rtmp2=rtmp1.substring(rtmp1.lastIndexOf("/")+1);
                  String url4="http://ai.jsquanou.com:1985/api/v1/gb28181?action=sip_query_session&id="+rtmp2;
                  String JG4=HttpClientHelper.httpMapGet(url4);
                  JSONObject oJG4 = JSONObject.parseObject(JG4);
                  String oJG41=oJG4.get("code").toString();
                  String dhia="";
                  if(oJG41.equals("0")){
                      JSONObject obj21=(JSONObject)oJG4.get("data");
                      JSONArray obj211=obj21.getJSONArray("sessions");
                      String obj2111=obj211.getJSONObject(0).getJSONArray("devices").getJSONObject(0).get("device_status").toString();
                      if(obj2111.equalsIgnoreCase("ON")){
                          dhia="在线";

                      }
                  }
                  dhia="离线";
                  js3.put("状态", dhia);
              }
              JSONArray JQQ1=ja1;
                redisUtil.set("redis3", res1, 600);
                return res1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return listmap12;
    }

    @Async("multithreadingExecutor")
    public CompletableFuture<String> doSomething1(String message) throws InterruptedException {
        log.info("do something1: {}", message);
        Thread.sleep(1000);
        return CompletableFuture.completedFuture("do something1: " + message);
    }

    @Async("multithreadingExecutor")
    public CompletableFuture<String> doSomething2(String message) throws InterruptedException {
        log.info("do something2: {}", message);
        Thread.sleep(1000);
        return CompletableFuture.completedFuture("; do something2: " + message);
    }

    @Async("multithreadingExecutor")
    public CompletableFuture<String> doSomething3(String message) throws InterruptedException {
        log.info("do something3: {}", message);
        Thread.sleep(1000);
        return CompletableFuture.completedFuture("; do something3: " + message);
    }

    @Async("multithreadingExecutor")
    public CompletableFuture<Map<String,Object>> doSomething4(String message) throws InterruptedException {
        log.info("do something4: {}", message);
        Thread.sleep(1000);
        Map<String,Object> map1=new HashMap<>();
        map1.put("z","w");
        map1.put("z1","w1");
        return CompletableFuture.completedFuture(map1);
    }
}
