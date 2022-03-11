package com.example.demo.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.constant.AuthToken;
import com.example.demo.constant.SysLog;
import com.example.demo.dao.SqlCommon;
import com.example.demo.entity.ResultMsg;
import com.example.demo.entity.UserToken;
import com.example.demo.service.impl.TestbmServiceImpl;
import com.example.demo.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    TestbmServiceImpl testbmServiceimpl;

    @Autowired
    private SqlCommon sqlCommon;

    @Value("${jwt.key}")
    private String jwtKey;
//    @GetMapping
//    public ResultMsg login(HttpServletRequest request,
//                           HttpServletResponse response,
//                           @RequestParam("account") String account,
//                           @RequestParam("password") String password,
//                           @RequestParam(value = "remember", required = false) boolean remember) {
//        String ip=request.getRemoteAddr();
//        String userAgent=request.getHeader(HttpHeaders.USER_AGENT);
//        LocalDateTime issuedAt=LocalDateTime.now();
//        LocalDateTime expiresAt=issuedAt.plusSeconds(remember? TimeUnit.DAYS.toSeconds(1):TimeUnit.MINUTES.toSeconds(30));
//        // 距离过期时间剩余的秒数
//        int expiresSeconds = (int) Duration.between(issuedAt, expiresAt).getSeconds();
//        UserToken userToken=new UserToken();
//        String USERID="SADA";
//        userToken.setId(UUID.randomUUID().toString().replace("-", ""));
//        userToken.setUserId(USERID);
//        userToken.setIssuedAt(issuedAt);
//        userToken.setExpiresAt(expiresAt);
//        userToken.setRemember(remember);
//        userToken.setUserAgent(userAgent);
//        userToken.setIp(ip);
//        redisUtil.set("token:"+USERID,userToken,expiresSeconds);
//        String token=testbmServiceimpl.jwttoken(USERID,userToken.getId(),issuedAt);
//
//        Cookie cookie=new Cookie("_token",token);
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(remember?expiresSeconds:-1);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//        Claims jwt1= Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
//        String USERID1= jwt1.getId();
//        String USERID11= jwt1.get("uid").toString();
//        return ResultMsg.ok("1");
//    }


    @GetMapping("/zx")
    public ResultMsg loginzx(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam("account") String account,
                           @RequestParam("password") String password) {

        // 距离过期时间剩余的秒数
        String sql="select * from gl_g04_user where appid='gxqz_zcgl' and useraccount='"+account+"' and passwd='"+password+"'";
        List<Map<String,Object>> user=sqlCommon.sql(sql);
        if(user.size()==0 || user.size()>1){
            return ResultMsg.ok("错误");
        }
        String userid="";
        for(Map<String,Object> usermap:user){
             userid=usermap.get("id").toString();
        }
        String token="";
        if(redisUtil.hasKey(userid)){
            token=(String) redisUtil.get(userid);
            return ResultMsg.ok(token);
        }
        LocalDateTime issuedAt=LocalDateTime.now();

        token=testbmServiceimpl.jwttokenzx(userid,account,issuedAt);
        redisUtil.set(token,userid,1800);
        redisUtil.set(userid,token,1800);
        Long currentTime = System.currentTimeMillis()/1000;
        redisUtil.set(token+userid,currentTime.toString(),1800);

        Cookie cookie=new Cookie("_token",token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        Claims jwt1= Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
        String USERID1= jwt1.getId();
        String USERID11= jwt1.get("uid").toString();
        String uaccount= jwt1.get("uaccount").toString();
        return ResultMsg.ok(token);
    }

    @GetMapping("/tests")
    @AuthToken
    public ResultMsg loginzxs(
                             @RequestParam("account") String account,
                             @RequestParam("password") String password) {

        // 距离过期时间剩余的秒数
        String sql="select * from gl_g04_user where appid='gxqz_zcgl' and useraccount='"+account+"' and passwd='"+password+"'";
        List<Map<String,Object>> user=sqlCommon.sql(sql);
        return ResultMsg.ok(user);
    }

    @GetMapping("/testaop")
    @SysLog("用户登录信息")
    public ResultMsg loginzaop(
            @RequestParam("account") String account,
            @RequestParam("password") String password) {

        // 距离过期时间剩余的秒数
        String sql="select * from gl_g04_user where appid='gxqz_zcgl' and useraccount='"+account+"' and passwd='"+password+"'";
        List<Map<String,Object>> user=sqlCommon.sql(sql);
        return ResultMsg.ok(user);
    }

}
