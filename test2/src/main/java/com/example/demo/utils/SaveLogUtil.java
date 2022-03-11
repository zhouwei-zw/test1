package com.example.demo.utils;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.demo.entity.SysLogEntity;
import com.example.demo.service.impl.SysLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveLogUtil {
    private static SysLogServiceImpl sysLogService;

    @Autowired
    public  void setsysLogService(SysLogServiceImpl sysLogService){SaveLogUtil.sysLogService=sysLogService;}

    public static boolean saveLog(SysLogEntity log) {
        boolean flag = false;
        try {
            DynamicDataSourceContextHolder.push("DATACENTER");
            flag = sysLogService.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DynamicDataSourceContextHolder.clear();
        }
        return flag;
    }
}
