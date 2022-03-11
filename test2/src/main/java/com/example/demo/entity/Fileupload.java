package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Date;
import java.io.Serializable;

/**
 * 文件表(Fileupload)实体类
 *
 * @author makejava
 * @since 2021-07-08 15:15:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "fileupload")
public class Fileupload implements Serializable {
    private static final long serialVersionUID = 233377059662150250L;
    /**
     * id
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    private String pid;
    /**
     * 文件地址
     */
    private String url;
    /**
     * 文件类型
     */
    private String type;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 数据库名
     */
    private String appid;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 修改时间
     */
    private Date updatetime;
    /**
     * 真实路径
     */
    private String path;

}
