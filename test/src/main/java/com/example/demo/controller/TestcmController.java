package com.example.demo.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author alex wong
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/testcm")
public class TestcmController {

    @GetMapping("/generatetoken")
    public static String generatetoken(String username,String password,String appid) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date2);
        ca.add(Calendar.HOUR_OF_DAY, 1);
        date2 = ca.getTime();
        String sdate = formatter.format(date2);
        int times= (int) (Timestamp.valueOf(sdate).getTime()/1000);
        //秘钥，不可给别人看
        String secret="abcdefg";
        String token="";
        try {
            token= JWT.create()
                    .withHeader(header)
                    .withIssuer(appid)
                    .withClaim("uname",username)
                    .withClaim("upwd",password)
                    .withExpiresAt(date2)
                    .sign(Algorithm.HMAC256(secret));
            System.out.println(token);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return token;
    }


    @GetMapping("/parsetoken")
    public Claims parsetoken(String token) {
        String test="abcdefg";
        try {
            Claims jwt=Jwts.parser().setSigningKey(test).parseClaimsJws(token).getBody();
            String SS= jwt.get("uid").toString();
            Claims cas=Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(test)).parseClaimsJws((token.trim())).getBody();
            System.out.println(jwt);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Jwts.parser().setSigningKey(test).parseClaimsJws(token).getBody();
    }



    @GetMapping("/generatetoken1")
    public static String generatetoken1(String id1) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String,Object> claims1=new HashMap<>();
        claims1.put("uid",id1);

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
            JwtBuilder builder=Jwts.builder();
            builder.setClaims(claims1);
            builder.setIssuer("hd_ljfl");
            builder.setExpiration(date2);
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

