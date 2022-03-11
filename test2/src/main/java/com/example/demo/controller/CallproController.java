package com.example.demo.controller;

import ch.qos.logback.classic.db.DBHelper;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.demo.entity.ResultMsg;
import com.example.demo.entity.Testbm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("callpro")
public class CallproController {
    private static DataSource dataSource;

    @Autowired
    private void setDataSource(DataSource dataSource) {CallproController.dataSource=dataSource;}

//    执行存储过程，返回两个结果集
    @GetMapping("call")
    public ResultMsg testdate1() throws SQLException, ClassNotFoundException {
        String qsql="CALL uptest()";
        ResultSet rs=null;
        Connection conn=null;
        String qd="com.mysql.cj.jdbc.Driver";
        String jdbcurl="jdbc:mysql://192.168.63.16:3306/zw";
        String usern="root";
        Class.forName(qd);
        conn=DriverManager.getConnection(jdbcurl,usern,usern) ;
        Statement statement = conn.createStatement();
        rs = statement.executeQuery(qsql);
        Map<String,Object> map1=new HashMap<>();

       List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        if(rs!=null) {
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                ResultSetMetaData meta = rs.getMetaData();
                int colcount = meta.getColumnCount();
                for (int i = 1; i <= colcount; i++) {
                    map.put(meta.getColumnLabel(i),rs.getObject(i).toString());
                }
                list.add(map);
            }
            map1.put("data",list);
            if(statement.getMoreResults()){
                Map<String, Object> map = new HashMap<>();
                rs=statement.getResultSet();
                while (rs.next()) {
                    map1.put("count", rs.getObject("size1").toString());
                }

            }
            map1.put("data",list);
        }
        rs.close();
        statement.close();
        conn.close();
        return ResultMsg.ok(map1);
    }
//存储过程多个结果集（包含结果与总数量）
    @GetMapping("cal1")
    public Map<String,Object> testdate11()  {

        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        JdbcTemplate jt=new JdbcTemplate(ds.getDataSource("LF_ZCGL"));
        String qsql="CALL uptest()";
        System.out.println("qqq");
        Map<String,Object> mapsq=new HashMap<>();
        Map<String,Object> ss= jt.execute(qsql,(CallableStatementCallback<Map<String,Object>>) cs -> {
            List<Map<String,Object>> list=new ArrayList<>();

            boolean execute=cs.execute();
            if(execute){
                ResultSet rs=cs.getResultSet();
                if(rs!=null) {
                    while (rs.next()) {
                        Map<String, Object> map = new HashMap<>();
                        ResultSetMetaData meta = rs.getMetaData();
                        int colcount = meta.getColumnCount();
                        for (int i = 1; i <= colcount; i++) {
                            if(rs.getObject(i) instanceof LocalDateTime){
//                                map.put(meta.getColumnLabel(i), Date.from(((LocalDateTime) rs.getObject(i)).atZone(ZoneId.systemDefault()).toInstant()));
                                map.put(meta.getColumnLabel(i), ((LocalDateTime) rs.getObject(i)).toString().replace("T"," "));
                            }else {
                            map.put(meta.getColumnLabel(i),rs.getObject(i));
                            }
                        }
                        list.add(map);
                    }
                    mapsq.put("data",list);
                    if(cs.getMoreResults()){
                        Map<String, Object> map = new HashMap<>();
                        rs=cs.getResultSet();
                        while (rs.next()) {
                           // map.put("count", rs.getObject("size1"));
                            mapsq.put("count",rs.getObject("size1"));
                        }
                      //  list.add(map);
                    }
            }
        }
          //  return list;
            return mapsq;
        });
       return mapsq;
    }


    //存储过程多个结果集（包含结果与总数量）
    @RequestMapping("cal2")
    public Map<String,Object> callres(@RequestParam(name="sql",required = true) String sql,
                                      @RequestParam(name="DBTag",required = true) String DBTag,
                                      HttpServletRequest request)  {

        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        JdbcTemplate jt=new JdbcTemplate(ds.getDataSource(DBTag.toUpperCase()));
//        String qsql="CALL uptest()";
        String qsql=sql;
        System.out.println("qqs");
        Map<String,Object> mapsq=new HashMap<>();
        Map<String,Object> ss= jt.execute(qsql,(CallableStatementCallback<Map<String,Object>>) cs -> {
            List<Map<String,Object>> list=new ArrayList<>();

            boolean execute=cs.execute();
            if(execute){
                ResultSet rs=cs.getResultSet();
                if(rs!=null) {
                    while (rs.next()) {
                        Map<String, Object> map = new HashMap<>();
                        ResultSetMetaData meta = rs.getMetaData();
                        int colcount = meta.getColumnCount();
                        for (int i = 1; i <= colcount; i++) {
                            if(rs.getObject(i) instanceof LocalDateTime){
//                                map.put(meta.getColumnLabel(i), Date.from(((LocalDateTime) rs.getObject(i)).atZone(ZoneId.systemDefault()).toInstant()));
                                map.put(meta.getColumnLabel(i), ((LocalDateTime) rs.getObject(i)).toString().replace("T"," "));
                            }else {
                                map.put(meta.getColumnLabel(i),rs.getObject(i));
                            }
                        }
                        list.add(map);
                    }
                    mapsq.put("data",list);
                    if(cs.getMoreResults()){
                        Map<String, Object> map = new HashMap<>();
                        rs=cs.getResultSet();
                        while (rs.next()) {
                            // map.put("count", rs.getObject("size1"));
                            mapsq.put("count",rs.getObject("size1"));
                        }
                        //  list.add(map);
                    }
                }
            }
            //  return list;
            return mapsq;
        });
        return mapsq;
    }

}
