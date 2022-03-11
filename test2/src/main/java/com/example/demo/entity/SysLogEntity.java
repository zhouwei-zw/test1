package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_log")
public class SysLogEntity {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    private String className;
    private String methodName;
    private String params;
    private String excTime;
    private String remark;
    private String createDate;
    private String url;
}
