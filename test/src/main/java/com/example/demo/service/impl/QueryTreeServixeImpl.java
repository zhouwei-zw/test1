package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.demo.dao.SqlCommon;
import com.example.demo.entity.ResultMsg;
import com.example.demo.entity.Vo.QueryVO;
import com.example.demo.service.QueryTreeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class QueryTreeServixeImpl implements QueryTreeService {

    @Autowired
    private SqlCommon sqlCommon;

    public ResultMsg<?> queryTree(QueryVO vo) {
        long start = System.currentTimeMillis();
        String querySQL = vo.getQuerySQL();
        String dbTag = vo.getDBTag();
        String filedname = vo.getFiledname();
        String orderby = vo.getOrderby();
        String tablename = vo.getTablename();
        // 如果DBTag或者querySql为空
        if (StrUtil.hasEmpty(dbTag, querySQL)) {
            return ResultMsg.serverError("参数为空");
        }
        Object result = null;
        List<Map<String,Object>> queryDataList=null;
        String sql="select "+filedname+" from "+tablename;
        String sql1="select "+filedname+" from "+tablename+" where pid='0' order by "+orderby+" desc";
        DynamicDataSourceContextHolder.push(dbTag);
        List<Map<String,Object>> res=null;
        List<Map<String,Object>> res1=null;
        try {
             res=sqlCommon.sql(sql);
             res1=sqlCommon.sql(sql1);
        }catch (Exception e){
            return  ResultMsg.serverError("sql语句错误，请检查sql语句!");
        }

        queryDataList=getchild(res,res1);

        DynamicDataSourceContextHolder.clear();
        long end = System.currentTimeMillis();
        log.info("querytree time={}",end-start);
        return ResultMsg.ok(queryDataList);
    }

    public List<Map<String,Object>> getchild(List<Map<String,Object>> res,List<Map<String,Object>> res1) {
        return res1.stream().peek(res1menu->{
            List<Map<String,Object>> childlist=res.stream().filter(resmenu->resmenu.get("pid")!=null&&resmenu.get("pid").equals(res1menu.get("id"))).collect(Collectors.toList());
            if(childlist.size()>0){
                getchild(res,childlist);
                res1menu.put("childList",childlist);
            }
        }).collect(Collectors.toList());
    }

}
