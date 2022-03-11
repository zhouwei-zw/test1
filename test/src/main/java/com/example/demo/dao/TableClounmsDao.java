package com.example.demo.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface TableClounmsDao {

    @Select("\t\t SELECT column_name FROM information_schema.columns  WHERE table_name = #{tablename} and table_schema=#{DBName} order by ORDINAL_POSITION asc;")
    List<Map<String,String>> getkhList2(@Param("tablename")String tablename, @Param("DBName")String DBName);
}
