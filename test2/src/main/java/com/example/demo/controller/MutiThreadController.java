package com.example.demo.controller;

import com.example.demo.entity.ResultMsg;
import com.example.demo.service.KhService;
import com.example.demo.service.XkVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("thread")
public class MutiThreadController {

    @Autowired
    XkVideoService xkVideoService;

//    @GetMapping("/test")
//    public ResultMsg delete() {
//        //错误示范
//        //CompletableFuture<List<Map<String,Object>>>不支持强转，xkVideoService.saveRiverpatrol()要求输出类型为CompletableFuture<List<Map<String,Object>>>
//        CompletableFuture<List<Map<String,Object>>> test1= (CompletableFuture<List<Map<String, Object>>>) xkVideoService.saveRiverpatrol();
//        CompletableFuture<List<Map<String,Object>>> test2= (CompletableFuture<List<Map<String, Object>>>) xkVideoService.saveRiverpatrol();
//        CompletableFuture<List<Map<String,Object>>> test3= (CompletableFuture<List<Map<String, Object>>>) xkVideoService.saveRiverpatrol();
//        CompletableFuture.allOf(test1,test2,test3).join();
//        String result =test1.toString()+test2.toString()+test3.toString();
//        return ResultMsg.ok(result);
//    }

    @GetMapping("/test1")
    public ResultMsg delete1() throws InterruptedException, ExecutionException {
//        正确示范
        CompletableFuture<String> test1=  xkVideoService.doSomething1("ZW1");
        CompletableFuture<String> test2= xkVideoService.doSomething2("ZW2");
        CompletableFuture<String> test3= xkVideoService.doSomething3("ZW3");
        CompletableFuture<Map<String,Object>> test4= xkVideoService.doSomething4("ZW4");
        CompletableFuture.allOf(test1,test2,test3,test4).join();
        String result =test1.get()+test2.get()+test3.get()+test4.get();
        return ResultMsg.ok(result);
    }

}
