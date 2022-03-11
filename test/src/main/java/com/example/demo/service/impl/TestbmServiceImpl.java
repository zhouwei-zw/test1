package com.example.demo.service.impl;

import com.example.demo.entity.Testbm;
import com.example.demo.mapper.TestbmMapper;
import com.example.demo.service.TestbmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author alex wong
 * @since 2021-11-08
 */
@Service
public class TestbmServiceImpl extends ServiceImpl<TestbmMapper, Testbm> implements TestbmService {

    public String jwttoken  (String userid, String tokenid, LocalDateTime issuedAt){

        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String,Object> claims1=new HashMap<>();
        claims1.put("uid",userid);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date2);
        ca.add(Calendar.HOUR_OF_DAY, 1);
        date2 = ca.getTime();
        String sdate = formatter.format(date2);
        int times= (int) (Timestamp.valueOf(sdate).getTime()/1000);
        //秘钥，不可给别人看
        String secret1="abcdefg";
        String token="";
        try {
            JwtBuilder builder= Jwts.builder();
            builder.setClaims(claims1);
            builder.setId(tokenid);
            builder.setIssuedAt(new Date(issuedAt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
          //  builder.setExpiration(date2);
            byte[] signkey= DatatypeConverter.parseBase64Binary(secret1);
            builder.signWith(SignatureAlgorithm.HS256,signkey);
            token=builder.compact();
            System.out.println(token);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return token;
    }

}
