package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Fileupload;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文件表(Fileupload)表数据库访问层
 *
 * @author makejava
 * @since 2021-07-12 13:30:31
 */
@Mapper
@Component
public interface FileuploadDao extends BaseMapper<Fileupload> {
    List<Fileupload> getfileByid(@Param("id") String id);

    @Select("select * from fileupload")
    List<Fileupload> getList();

}

