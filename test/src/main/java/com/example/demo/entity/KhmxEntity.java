package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhmxEntity implements Serializable {
    private String id;
    private String gzid;
    private String khid;
    private String afterphoto;
    private String beforphoto;
    private String kf;
    private String kfsm;
//    private String type;
//    private String pid;
}
