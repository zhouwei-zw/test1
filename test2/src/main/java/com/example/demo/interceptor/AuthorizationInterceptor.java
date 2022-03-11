package com.example.demo.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.demo.constant.AuthToken;
import com.example.demo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    private String httpHeaderName = "token";
    private String unauthorizedErrorMessage = "401 unauthorized";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        // 如果打上了AuthToken注解则需要验证token
        if(method.getAnnotation(AuthToken.class)!=null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null){
            String token=request.getHeader(httpHeaderName);
            System.out.println("进入token管理");
            String userid="";
            if(!ObjectUtil.isEmpty(token)){
                userid=(String) redisUtil.get(token);
            }else {
                Map<String,Object> map=new HashMap<>();
                map.put("code","407");
                map.put("message",unauthorizedErrorMessage);
                return false;
            }
            if(!StrUtil.hasEmpty(userid)){
                Long tokeBirthTime = Long.valueOf(redisUtil.get(token+userid).toString());
                Long diff = (System.currentTimeMillis()/1000) - tokeBirthTime;
                if(diff>1800){
                    redisUtil.set(token,userid,1800);
                    redisUtil.set(userid,token,1800);
                    Long currentTime1 = System.currentTimeMillis()/1000;
                    redisUtil.set(token+userid,currentTime1.toString(),1800);
                }
                return true;
            }else {
                Map<String,Object> map=new HashMap<>();
                map.put("code","407");
                map.put("message",unauthorizedErrorMessage);
                return false;
            }
        }
        return true;

    }

//    public Map<String,Object> preHandle1(HttpServletRequest request, HttpServletResponse response, Object handler){
//        Map<String,Object> map=new HashMap<>();
//        if(!(handler instanceof HandlerMethod)){
//            map.put("code","200");
//            map.put("message","成功");
//            return map;
//        }
//        HandlerMethod handlerMethod=(HandlerMethod)handler;
//        Method method=handlerMethod.getMethod();
//        // 如果打上了AuthToken注解则需要验证token
//        if(method.getAnnotation(AuthToken.class)!=null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null){
//            String token=request.getHeader(httpHeaderName);
//            System.out.println("进入token管理");
//            String userid="";
//            if(!ObjectUtil.isEmpty(token)){
//                userid=(String) redisUtil.get(token);
//            }else {
//
//                map.put("code","407");
//                map.put("message",unauthorizedErrorMessage);
//                return map;
//            }
//            if(!StrUtil.hasEmpty(userid)){
//                Long tokeBirthTime = Long.valueOf(redisUtil.get(token+userid).toString());
//                Long diff = (System.currentTimeMillis()/1000) - tokeBirthTime;
//                if(diff>1800){
//                    redisUtil.set(token,userid,1800);
//                    redisUtil.set(userid,token,1800);
//                    Long currentTime1 = System.currentTimeMillis()/1000;
//                    redisUtil.set(token+userid,currentTime1.toString(),1800);
//                }
//                map.put("code","200");
//                map.put("message","成功");
//                return map;
//            }else {
//                map.put("code","407");
//                map.put("message",unauthorizedErrorMessage);
//                return map;
//            }
//        }
//        map.put("code","200");
//        map.put("message","成功");
//        return map;
//    }
}
