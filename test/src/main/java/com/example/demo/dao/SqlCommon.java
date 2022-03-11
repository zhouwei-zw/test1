package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Mapper
@Component
public interface SqlCommon {

    List<Map<String, Object>> sql(@Param("sql") String sql);

    List<LinkedHashMap<String, Object>> sqlLinked(@Param("sql") String sql);

    List<String> sqlzd(@Param("sql") String sql);

    void insertByMap(@Param("tableName") String tableName, @Param("rowMap") Map<String, Object> row);
}
