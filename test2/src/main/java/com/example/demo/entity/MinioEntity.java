package com.example.demo.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioEntity {
    private String miniourl;
    private String name;
    private String pass;
    private String bucketName;

//    private void test(){
//
//    }
}
