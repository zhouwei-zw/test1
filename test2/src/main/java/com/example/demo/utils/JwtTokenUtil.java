package com.example.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {
    public static String secret="abcdefg";

    public static String generatetoken(String username,String password) {
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

        String token="";
        try {
            token= JWT.create()
                    .withHeader(header)
                    .withAudience("hstesting_api")
                    .withIssuer("ntchinatelecom")
                    .withClaim("username",username)
                    .withClaim("pwd",password)
                    .withExpiresAt(date2)
                    .sign(Algorithm.HMAC256(secret));
            System.out.println(token);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return token;
    }

}
