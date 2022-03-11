package com.example.demo;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.demo.dao.SqlCommon;
import com.example.demo.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;


@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    SqlCommon sqlCommon;

    @Test
    void contextLoads() {
        //获取redis连接
//       RedisConnection conn= redisTemplate.getConnectionFactory().getConnection();
//       conn.flushDb();

//        redisTemplate.opsForValue().set("zwtest1","周伟11");
//        System.out.println(redisTemplate.opsForValue().get("zwtest1"));

//        String str = "01234567890123456789";
//        System.out.println(str.lastIndexOf('9',9));
//        redisUtil.select1(1);
        redisUtil.set("zwsss","周伟11212");

        System.out.println(redisUtil.get("zwsss"));



//        Map<String,Object> map=new HashMap<>();
//        map.put("1","11");
//        map.put("2","22");
//        redisUtil.hmset("zw11",map);
//        System.out.println(redisUtil.hmget("zw11"));
    }

    @Test
    void contextLoads1() {
        LinkedHashMap<String,Object> OBJ=new LinkedHashMap<>();
        DynamicDataSourceContextHolder.push("tz_hlq");
        String sql1="call UP_DSJ_JJFZ_RCFL";
        List<LinkedHashMap<String, Object>> result = sqlCommon.sqlLinked(sql1);
        DynamicDataSourceContextHolder.clear();
        List<String> xAxis = new ArrayList<>();
        for(LinkedHashMap<String, Object> map: result) {
             String key1= map.values().stream().findFirst().toString();
             xAxis.add(null == key1 ? "": key1);
        }
        OBJ.put("aaa",xAxis);
        System.out.println(OBJ);



    }


}
