package com.example.demo.utils;

import com.example.demo.entity.Fileupload;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MinioUtil {
    private static String minioUrl;
    private static String minioName;
    private static String minioPass;
    private static String bucketName;
    private static String upLoadPath;

    public static String getUpLoadPath() {
        return upLoadPath;
    }

    public static String getMinioUrl() {
        return minioUrl;
    }

    public static void setMinioUrl(String minioUrl) {
        MinioUtil.minioUrl = minioUrl;
    }

    public static void setMinioName(String minioName) {
        MinioUtil.minioName = minioName;
    }

    public static void setMinioPass(String minioPass) {
        MinioUtil.minioPass = minioPass;
    }

    public static void setBucketName(String bucketName) {
        MinioUtil.bucketName = bucketName;
    }
    public static void setUpLoadPath(String upLoadPath) { MinioUtil.upLoadPath = upLoadPath; }

    public static String getBucketName() {
        return MinioUtil.bucketName;
    }

    private static MinioClient minioClient = null;

    static List<String> imageList = new ArrayList<>();
    static{
        imageList.add("png");
        imageList.add("jpg");
        imageList.add("jpeg");
        imageList.add("gif");
    }

    public static String upload(MultipartFile file,String pathName){
        String fileurl="";
        try {
            initMinio(minioUrl,minioName,minioPass);
            if(!minioClient.bucketExists(bucketName)){
                minioClient.makeBucket(bucketName);
            }
            InputStream stream=file.getInputStream();
            System.out.println("bucketName = "+bucketName);
            System.out.println("pathName = "+pathName);
            System.out.println("stream = "+stream);
            minioClient.putObject(bucketName, pathName, stream, "application/octet-stream");
            stream.close();
            fileurl=minioUrl+bucketName+"/"+pathName;
        } catch (InvalidPortException e) {
            e.printStackTrace();
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
         catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        }  catch (InternalException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RegionConflictException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return fileurl;
    }

    public static Fileupload minioupload(MultipartFile file, String appid, String filename, String tablename,String id){
        Fileupload fileupload=new Fileupload();
        String uuid= UUID.randomUUID().toString();
        String[] filenames=filename.split("\\.");
        String name=filenames[0];
        String type=filenames[filenames.length-1];
        fileupload.setId(uuid);
        fileupload.setAppid(appid);
        fileupload.setPid(id);
        fileupload.setFilename(filename);
        String realpath=minioUrl+bucketName+"/"+appid+"/"+tablename+"/"+id+"/"+filename;
        String path=appid+"/"+tablename+"/"+id+"/"+filename;
        fileupload.setPath(path);
       // fileupload.setUrl(realpath);
        try{
            initMinio(minioUrl,minioName,minioPass);
            if(!minioClient.bucketExists(bucketName)){
                minioClient.makeBucket(bucketName);
            }
//            InputStream stream= null;
//            if(imageList.contains(type)){
//
//            }
            InputStream stream=file.getInputStream();
            minioClient.putObject(bucketName,path,stream,file.getContentType());
            if(null != stream) {
                stream.close();
            }
            fileupload.setUrl(realpath);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (RegionConflictException e) {
            e.printStackTrace();
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return fileupload;
    }

    public static String deletefile(String path){
      //  String fileurl=fileupload.getUrl();
        try {
            initMinio(minioUrl,minioName,minioPass);
            minioClient.removeObject(bucketName,path);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return "删除失败"+e.getMessage();
        }
        return "成功";

    }

//    public static Fileupload delete(String id){
//        // fileupload.setUrl(realpath);
//        try{
//            initMinio(minioUrl,minioName,minioPass);
//        } catch (InvalidPortException e) {
//            e.printStackTrace();
//        } catch (InvalidEndpointException e) {
//            e.printStackTrace();
//        }
//    }



    public static MinioClient initMinio(String minioUrl, String minioName, String minioPass) throws InvalidPortException, InvalidEndpointException {
        MinioClient minioClient=new MinioClient(minioUrl,minioName,minioPass);
        MinioUtil.minioClient = minioClient;
        return minioClient;
    }
//    public static InputStream zipImage(InputStream inStream, long desFileSize, String imageId) {
//        byte[] imageBytes = toBs(inStream);
//        // || imageBytes.length < desFileSize * 1024
//        if (imageBytes == null || imageBytes.length <= 0 ) {
//            return inStream;
//        }
//
//        long srcSize = imageBytes.length;
//        double accuracy = getAccuracy(srcSize / 1024);
//        try {
//            while (imageBytes.length > desFileSize * 1024) {
//                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
//                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
//                Thumbnails.of(inputStream)
//                        .scale(accuracy)
//                        .outputQuality(accuracy)
//                        .toOutputStream(outputStream);
//                imageBytes = outputStream.toByteArray();
//            }
//            log.info("【图片压缩】imageId={} | 图片原大小={}kb | 压缩后大小={}kb",
//                    imageId, srcSize / 1024, imageBytes.length / 1024);
//        } catch (Exception e) {
//            log.error("【图片压缩】msg=图片压缩失败!", e);
//        }
//        return toIs(imageBytes);
//    }
}
