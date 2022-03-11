package com.sipsd.jkcx.vo;

import lombok.Data;

@Data
public class JiekouVo {
    private String name;
    private Integer current = 1;
    private Integer size = 5;
}