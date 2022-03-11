package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ZipUtils {

    public static InputStream zipImage(InputStream inStream, long desFileSize, String imageId) throws IOException {
        byte[] imageBytes = inputtobyte(inStream);
        // || imageBytes.length < desFileSize * 1024
        if (imageBytes == null || imageBytes.length <= 0 ) {
            return inStream;
        }

        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
//                        .watermark(Positions.BOTTOM_RIGHT,,0.25f)  //添加水印
                        .scale(accuracy)  //0-1 float 压缩大小
                        .outputQuality(accuracy)  //0-1 压缩质量
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            log.info("【图片压缩】imageId={} | 图片原大小={}kb | 压缩后大小={}kb",
                    imageId, srcSize / 1024, imageBytes.length / 1024);
        } catch (Exception e) {
            log.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return bytetoinput(imageBytes);
    }

    public static InputStream bytetoinput(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static final byte[] inputtobyte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }
}
