package com.example.demo.config;

import com.example.demo.entity.MinioEntity;
import com.example.demo.utils.MinioUtil;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@EnableConfigurationProperties(MinioEntity.class)
@Configuration
public class MinioConfig {

//    @Autowired
//    private MinioEntity minioEntity;



//    @Bean
//    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
//        MinioClient client=new MinioClient(minioEntity.getMiniourl(),minioEntity.getName(),minioEntity.getPass());
//        return client;
//    }

    @Value(value = "${minio.miniourl}")
    private String minioUrl;
    @Value(value = "${minio.name}")
    private String minioName;
    @Value(value = "${minio.pass}")
    private String minioPass;
    @Value(value = "${minio.bucketName}")
    private String bucketName;
    @Value("${minio.urlpath}")
    private String upLoadPath;

    @Bean
    public void initMinio(){
        if(!minioUrl.startsWith("http")){
            minioUrl = "http://" + minioUrl;
        }
        if(!minioUrl.endsWith("/")){
            minioUrl = minioUrl.concat("/");
        }
        MinioUtil.setMinioUrl(minioUrl);
        MinioUtil.setMinioName(minioName);
        MinioUtil.setMinioPass(minioPass);
        MinioUtil.setBucketName(bucketName);
        MinioUtil.setUpLoadPath(upLoadPath);

    }
}
