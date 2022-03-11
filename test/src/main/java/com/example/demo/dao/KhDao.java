package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Fileupload;
import com.example.demo.entity.KhKfmx;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface KhDao extends BaseMapper<KhKfmx> {
    @Options(statementType = StatementType.CALLABLE)
    @Select("call upkhlist(#{table},#{sqmc},#{dwmc},#{khrq})")
    List<Map<String,String>> getkhList(@Param("table")String table,@Param("sqmc")String sqmc,@Param("dwmc")String dwmc,@Param("khrq")String khrq);


    @Options(statementType = StatementType.CALLABLE)
    @Select("call upkhlist2(#{table},#{sqmc},#{dwmc},#{khrq})")
    List<Map<String,String>> getkhList2(@Param("table")String table,@Param("sqmc")String sqmc,@Param("dwmc")String dwmc,@Param("khrq")String khrq);

}
