package com.sipsd.jkcx.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConstants {

    public static String FILE_PATH;
    public static String SAVE_PATH;

    @Value("${filePath}")
    public void setFilePath(String path){
        AppConstants.FILE_PATH = path;
    }

    @Value("${savePath}")
    public void setSavePath(String path){
        AppConstants.SAVE_PATH = path;
    }

}
