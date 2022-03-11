package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface XkVideoService {
    List<Map<String,Object>> saveRiverpatrol();

    JSONObject geojson();

    CompletableFuture<String> doSomething1(String message) throws InterruptedException;

    CompletableFuture<String> doSomething2(String message) throws InterruptedException;

    CompletableFuture<String> doSomething3(String message) throws InterruptedException;

    CompletableFuture<Map<String,Object>> doSomething4(String message) throws InterruptedException;
}
