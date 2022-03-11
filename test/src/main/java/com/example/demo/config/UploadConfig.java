package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class UploadConfig {

    @Value("${upload.ip}")
    public String uploadPath1;

//    @Value("${upload.ip}")
//    public void setUploadPath1(String relativepath) {
//        this.uploadPath1 = relativepath;
//    }

//    public String getUploadPath1() {
//        return uploadPath1;
//    }
}
