package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class RedisSessionController {

    @GetMapping("/addsession")
    public Map<String,Object> addSession(HttpServletRequest request, HttpServletResponse response){
        String id = request.getSession().getId();
        String uri = request.getRequestURI()+":"+request.getServerPort();
        //像session存储数据 一会儿拿看是否能拿到
        request.getSession().setAttribute("test","这是一条测试是否能共享得数据。。。");
        request.getSession().setAttribute("user_1", "{uid:1,username:11@qq.com}");
        String token = UUID.randomUUID().toString();
        Cookie cookie=new Cookie("test",token);
        response.addCookie(cookie);
        Map<String,Object> map=new HashMap<>();
        map.put("sessionid",id);
        map.put("uri",uri);
        return map;
    }

    @GetMapping("/getsession")
    public Map<String,Object> getSession(HttpServletRequest request){
        String id = request.getSession().getId();
        String uri = request.getRequestURI()+":"+request.getServerPort();
        String test = (String)request.getSession().getAttribute("test");
        String user_1 = (String) request.getSession().getAttribute("user_1");

        Map<String,Object> map=new HashMap<>();
        map.put("sessionid",id);
        map.put("user_1",user_1);
        map.put("uri",uri);
        map.put("test",test);
        return map;
    }
}
