package com.sipsd.jkcx.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sipsd.jkcx.model.JiekouEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface JiekouDao extends BaseMapper<JiekouEntity> {
//    @Select("SELECT id, name, xAppPath, xAppMS, pFilepath, xVer, version_code, updatetime, package as packageStr, ip, rw FROM jiekoulist WHERE NAME like '%${name}%' AND (NAME, updatetime) IN (SELECT NAME, MAX(updatetime) AS updatetime FROM jiekoulist GROUP BY NAME) ORDER BY updatetime DESC")
    IPage<JiekouEntity> getNewestList(IPage<JiekouEntity> page); //获取去除同名项目的列表（按最新更新时间排序）
    IPage<JiekouEntity> selectByName(IPage<JiekouEntity> page,String name); //根据条件（name,current,size）对最新列表进行模糊查询

    @Select("select id from jiekoulist where package = #{packageStr}") //根据输入的package名获取id
    List<String> delByPackageList(@Param("packageStr") String packageStr); //建立选取的id数组
}
