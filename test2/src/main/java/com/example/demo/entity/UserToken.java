package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserToken implements Serializable {

    private static final long serialVersionUID = 8798594496773855969L;
    // token id
    private String id;
    // 用户id
    private String userId;
    // ip
    private String ip;
    // 客户端
    private String userAgent;
    // 授权时间
    private LocalDateTime issuedAt;
    // 过期时间
    private LocalDateTime expiresAt;
    // 是否记住我
    private boolean remember;
}
