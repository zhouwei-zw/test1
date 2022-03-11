package com.sipsd.jkcx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@TableName("jiekoulist")
public class JiekouEntity {
//    @TableId(type = IdType.ASSIGN_ID)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    @JsonProperty("xAppPath")private String xAppPath;
    @JsonProperty("xAppMS")private String xAppMS;
    @JsonProperty("pFilepath")private String pFilepath;
    @JsonProperty("xVer")private String xVer;
    private String version_code;
    private String updatetime;
    @JsonProperty("package")@JsonAlias("package")@TableField(value = "package") private String packageStr;
    private String ip;
    private Integer rw;
}
