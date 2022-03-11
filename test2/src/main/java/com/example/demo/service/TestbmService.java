package com.example.demo.service;

import com.example.demo.entity.Testbm;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author alex wong
 * @since 2021-11-08
 */
public interface TestbmService extends IService<Testbm> {

    String jwttoken  (String userid, String tokenid, LocalDateTime issuedAt);

    String jwttokenzx  (String userid,String useraccount, LocalDateTime issuedAt);

}
