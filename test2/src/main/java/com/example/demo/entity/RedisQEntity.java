package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisQEntity implements Serializable {
    private String dbTag;
    private String sql;
    private String keyname;
    private String keytimeout;
}
