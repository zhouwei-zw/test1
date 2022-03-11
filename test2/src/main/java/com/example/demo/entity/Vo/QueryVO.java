package com.example.demo.entity.Vo;

import lombok.Data;

@Data

public class QueryVO {
    private String DBTag;
    private String querySQL;
    private String filedname;
    private String orderby;
    private String tablename;
}
