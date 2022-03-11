package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface XkVideoService {
    List<Map<String,Object>> saveRiverpatrol();

    JSONObject geojson();
}
