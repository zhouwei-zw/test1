package com.example.demo.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ResultMsg;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.IOException;
import java.util.Map;

public class HttpClientHelper {

    public static String httpPost(String url, JSONObject param) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost method = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            method.setConfig(requestConfig);
            StringEntity entity = new StringEntity(param.toString(), "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(method);
            HttpEntity httpEntity = httpResponse.getEntity();
            String resultStr =  EntityUtils.toString(httpEntity);
            return resultStr;
//            JSONObject obj = JSONObject.parseObject(resultStr);
//            ResultMsg result = JSONObject.toJavaObject(obj, ResultMsg.class);
//            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(httpClient);
        }
        return "";
    }


    /**
     * httpget请求返回json字符串
     */
    public static String httpMapGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            url = url.replace("%", "%25").replace(" ", "%20").replace("<>", "!=");
//            log.info("httpGet url = {}", url);
            HttpGet httpget = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            httpget.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httpget);

            HttpEntity httpEntity = response.getEntity();
            String resultStr =  EntityUtils.toString(httpEntity);
            return resultStr;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(httpclient);
        }
        return null;
    }


    /**
     * httpget请求包含header参数返回json字符串
     */
    public static String httpheaderGet(String url, Map<String,String> headermap) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            url = url.replace("%", "%25").replace(" ", "%20").replace("<>", "!=");
//            log.info("httpGet url = {}", url);
            HttpGet httpget = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            httpget.setConfig(requestConfig);
            for(Map.Entry<String,String> entry1:headermap.entrySet()){
                httpget.addHeader(entry1.getKey(),entry1.getValue());
            }
            CloseableHttpResponse response = httpclient.execute(httpget);

            HttpEntity httpEntity = response.getEntity();
            String resultStr =  EntityUtils.toString(httpEntity);
            return resultStr;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(httpclient);
        }
        return null;
    }

    /**
     * http get请求返回ResultMsg,对url不做任何处理
     */
    public static String httpGet2(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // url = url.replace("%", "%25").replace(" ", "%20").replace("<>", "!=");
//            log.info("httpGet url = {}", url);
            HttpGet httpget = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(10000).setConnectionRequestTimeout(10000)
                    .setSocketTimeout(10000).build();
            httpget.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(httpget);

            HttpEntity httpEntity = response.getEntity();
            String resultStr =  EntityUtils.toString(httpEntity);
     //       JSONObject obj = JSONObject.parseObject(resultStr);
     //      ResultMsg result = JSONObject.toJavaObject(obj, ResultMsg.class);
            return resultStr;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(httpclient);
        }

        return null;
    }

}
