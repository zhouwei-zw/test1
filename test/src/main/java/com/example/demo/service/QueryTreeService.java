package com.example.demo.service;


import com.example.demo.entity.ResultMsg;
import com.example.demo.entity.Vo.QueryVO;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public interface QueryTreeService {

    ResultMsg<?> queryTree(QueryVO vo) throws InvocationTargetException, IllegalAccessException;

}
