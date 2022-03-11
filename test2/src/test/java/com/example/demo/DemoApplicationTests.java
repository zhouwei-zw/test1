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
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;


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
    }

//    @Test
//    void contextLoads1() {
//        LinkedHashMap<String,Object> OBJ=new LinkedHashMap<>();
//        DynamicDataSourceContextHolder.push("tz_hlq");
//        String sql1="call UP_DSJ_JJFZ_RCFL";
//        List<LinkedHashMap<String, Object>> result = sqlCommon.sqlLinked(sql1);
//        DynamicDataSourceContextHolder.clear();
//        List<String> xAxis = new ArrayList<>();
//        for(LinkedHashMap<String, Object> map: result) {
//             String key1= map.values().stream().findFirst().toString();
//             xAxis.add(null == key1 ? "": key1);
//        }
//        OBJ.put("aaa",xAxis);
//        System.out.println(OBJ);
//
//
//
//    }
//    private static int count = 0;
//    private static Object lock = new Object();
////  多线程测试
//    @Test
//    void contextLoads2() throws InterruptedException {
//                Thread t1 = new Thread(() -> {
//                    for (int i = 0; i < 5000; i++) {
//                            count++;
//                    }
//                });
//        Thread t2 = new Thread(() -> {
//            for (int i = 0; i < 5000; i++) {
//                    count--;
//            }
//        });
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        System.out.println(count);
//    }


}
