package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.example.demo.entity.ResultMsg;
import com.example.demo.entity.Vo.QueryVO;
import com.example.demo.service.QueryTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/Tree")
public class QtreeController {

    @Autowired
    private QueryTreeService queryTreeService;

    @RequestMapping("/queryTree")
    public ResultMsg<?> queryTree(QueryVO vo) throws InvocationTargetException, IllegalAccessException {
        if (ObjectUtils.isEmpty(vo)) {
            return ResultMsg.serverError("没有传入参数");
        }

        if (StrUtil.hasEmpty(vo.getQuerySQL())) {
            return ResultMsg.serverError("传入的querySql不能为空");
        }
        return queryTreeService.queryTree(vo);
    }
}
