//package com.example.demo.utils;
//
//import com.example.demo.entity.MinioEntity;
//import io.minio.MinioClient;
//import io.minio.errors.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//@Slf4j
//public class Upload_Util {
//
//
//       @Autowired
//       private MinioEntity minioEntity;
////    @Autowired
////    private MinioClient minioClient;
//    private static MinioClient minioClient = null;
//
//    @Value(value = "${minio.bucketName}")
//    public String bucketName;
//
//    public  InputStream getInputStream(String fileName) {
//        initMinio(minioEntity.getMiniourl(), minioEntity.getName(), minioEntity.getName());
//        InputStream fileInputStream = null;
//        // 检查存储桶是否已经存在
//        try {
//            fileInputStream = minioClient.getObject(bucketName, fileName);
//        } catch (IOException | InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException  | ErrorResponseException | InternalException   | XmlParserException | InvalidResponseException e) {
//            log.error(e.getMessage(), e);
//           // throw new BusinessException("获取文件数据流失败");
////        }
////
//        return fileInputStream;
//    }
//
//
//    private static MinioClient initMinio(String minioUrl, String minioName, String minioPass) {
//        if (minioClient == null) {
//            try {
//                minioClient = new MinioClient(minioUrl, minioName, minioPass);
//            } catch (InvalidEndpointException e) {
//                e.printStackTrace();
//            } catch (InvalidPortException e) {
//                e.printStackTrace();
//            }
//        }
//        return minioClient;
//    }
//}
