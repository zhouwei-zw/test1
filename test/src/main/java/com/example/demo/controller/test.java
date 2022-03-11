//package com.example.demo.controller;
//
//import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Map;
//
//public class test {
//
//    @GetMapping(value = "/queryDepartUserAll")
//    public ResultMsg queryDepartUserAll(@RequestParam(name="pageIndex", defaultValue="1") Integer pageNo,
//                                        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//                                        @RequestParam(name="globalSearch") Boolean globalSearch,
//                                        HttpServletRequest request) {
//        String appid = request.getParameter("appid");
//        String usera = request.getParameter("useraccount");
//        String depid = request.getParameter("depid");
//        if (appid != null) {
//            DynamicDataSourceContextHolder.push(AppIdEnum.DATACENTER.getCode());
//            String result = departmentService.getDepartlist(appid, depid);
//            String sql = "";
//            if (globalSearch == false) {
//                sql = "SELECT * FROM (select *,substring_index(substring_index(a.depid, ',', b.help_topic_id + 1), ',', -1) as floorid from(select * from gl_g04_user where appid='" + appid + "') a\n" +
//                        "join mysql.help_topic b on b.help_topic_id < (length(a.depid) - length(replace(a.depid, ',', '')) + 1)) t WHERE t.floorid='" + depid + "'";
//            } else {
//                sql = "SELECT * FROM (select *,substring_index(substring_index(a.depid, ',', b.help_topic_id + 1), ',', -1) as floorid from(select * from gl_g04_user where appid='" + appid + "') a\n" +
//                        "join mysql.help_topic b on b.help_topic_id < (length(a.depid) - length(replace(a.depid, ',', '')) + 1)) t WHERE t.floorid in ( " + result + ")";
//                if (StringUtils.hasLength(usera)) {
//                    sql += " and useraccount like '%" + usera + "%'";
//                }
//            }
//            String sql1 = "";
//
//            PageHelper.startPage(pageNo, pageSize);
//            List<Map<String, Object>> res = commonMapper.sql(sql);
//            List<Map<String, Object>> deplist11 = glG06DepartmentMapper.depart(appid);
//            for (Map<String, Object> maps : res) {
//                if (maps.get("depid").toString().contains(",")) {
//                    String[] st = maps.get("depid").toString().split(",");
//                    String comp = "";
//                    for (int i = 0; i < st.length; i++) {
//                        for (Map<String, Object> deplist12 : deplist11) {
//                            if (deplist12.get("id").toString().equals(st[i].toString())) {
//                                comp += deplist12.get("name").toString() + ",";
//                            }
//                        }
//                    }
//                    comp = comp.substring(0, comp.length() - 1);
//                    maps.put("companyname", comp);
//                } else {
//                    for (Map<String, Object> deplist12 : deplist11) {
//                        if (deplist12.get("id").toString().equals(maps.get("depid").toString())) {
//                            maps.put("companyname", deplist12.get("name").toString());
//                        }
//                    }
//                }
//            }
//            PageInfo pageInfo = new PageInfo(res);
//            PageQRespVO pageQRespVO = new PageQRespVO()
//                    .setData(pageInfo.getList())
//                    .setCurrent(pageInfo.getPageNum())
//                    .setSize(pageInfo.getPageSize())
//                    .setPages(pageInfo.getPages())
//                    .setTotal(pageInfo.getTotal());
//            DynamicDataSourceContextHolder.clear();
//            return ResultMsg.ok(pageQRespVO);
//
//        }
//        return ResultMsg.serverError("参数异常");
//    }
//
//}
