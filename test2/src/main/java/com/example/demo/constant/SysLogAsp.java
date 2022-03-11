package com.example.demo.constant;

import com.example.demo.entity.SysLogEntity;
import com.example.demo.utils.RequestIpUtils;
import com.example.demo.utils.SaveLogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Aspect
public class SysLogAsp {


//    @Pointcut("execution(public * com.example.demo.controller.*.*(..))")
//    public void logPointCut() {}
//
//    @Before("logPointCut()")
//    public void beforMethod(JoinPoint point){
//        String methodName = point.getSignature().getName();
//        System.out.println("haha");
//    }

    @Pointcut("@annotation(com.example.demo.constant.SysLog)")
    public void logPointCut() {}


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        String time1=String.valueOf(time);
        try {
//实现保存日志逻辑
            saveLog(point, time1);
        } catch (Exception e) {
        }
        return result;
    }
    private void saveLog(ProceedingJoinPoint joinPoint, String time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLogEntity sysLogEntity = new SysLogEntity();
        HttpServletRequest request=RequestIpUtils.getHttpServletRequest();
        String url=request.getRequestURI();
        sysLogEntity.setUrl(url);
        sysLogEntity.setExcTime(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sysLogEntity.setCreateDate(dateFormat.format(new Date()));
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if(sysLog != null) {
        //注解上的描述
            sysLogEntity.setRemark(sysLog.value());
        }
        //请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogEntity.setClassName(className);
        sysLogEntity.setMethodName(methodName);
        Object[] args = joinPoint.getArgs();
        try {
            List<String> list = new ArrayList<String>();
            for (Object o : args) {
                list.add(o.toString());
            }
            sysLogEntity.setParams(list.toString());
        } catch (Exception e){
        }
        SaveLogUtil.saveLog(sysLogEntity);
    }

}
