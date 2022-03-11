package com.example.demo.mapper;

import com.example.demo.entity.Fileupload;
import com.example.demo.entity.Testbm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author alex wong
 * @since 2021-11-08
 */
@Mapper
@Component
public interface TestbmMapper extends BaseMapper<Testbm> {

    @Select("select * from testbm")
    List<Testbm> getList();
}
