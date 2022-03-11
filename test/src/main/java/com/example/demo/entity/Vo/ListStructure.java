package com.example.demo.entity.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListStructure {

    //数据库
    private String TABLE_SCHEMA;
   //表名
    private String TABLE_NAME;
    //表列名
    private String COLUMN_NAME;
    //列的备注
    private String COLUMN_COMMENT;


}
