package com.sipsd.jkcx.controller;

import com.sipsd.jkcx.constants.AppConstants;
import com.sipsd.jkcx.dao.JiekouDao;
import com.sipsd.jkcx.model.JiekouEntity;
import com.sipsd.jkcx.service.JiekouService;
import com.sipsd.jkcx.util.DeleteFileUtil;
import com.sipsd.jkcx.util.JsonResult;
import com.sipsd.jkcx.vo.JiekouVo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: cuixr
 * @Date: 2020年12月14日17点04分
 */

@AllArgsConstructor //全参数构造器
@RestController //声明是一个”返回字符“的控制器 @Controller --> @Component 把标注的类交给框架管理，下面的类属性会被框架自动注入（不需要new）
@RequestMapping("/jiekou") //映射网络地址
public class MyController {

    private static final Logger log = LoggerFactory.getLogger(MyController.class);
    private final JiekouService jiekouService;
    @Autowired JiekouDao jiekouDao;

    @RequestMapping("/saveJiekou") //保存Json字段并插入数据至表jiekoulist
    public String getAndSave(JiekouEntity jiekouEntity) { //删除了括号中的@RequestParam
        jiekouService.getAndSave(jiekouEntity);
        return "保存成功";
    }

    @RequestMapping("list") //查询所有接口
    public JsonResult list() { //直接调用了IService中提供的list方法，因此不需要在Impl中重写
        List<JiekouEntity> list = jiekouService.list();
        return JsonResult.ok().put(list);
    }

    @RequestMapping("getNewestList") //自动分页显示每个name中最新的项目，按最新更新时间排序
    public JsonResult paging(JiekouVo jiekouVo) {
        return jiekouService.paging(jiekouVo);
    }

    @RequestMapping("queryBy") //按id,name或package查询
    public JsonResult queryByMap(@RequestParam Map<String,Object> map) {
        List<JiekouEntity> list = jiekouService.queryByMap(map);
        if(list.size()==0){
            return JsonResult.ok("查询结果为空").put(list);
        }
        return JsonResult.ok("查询成功").put(list);
    }

    @RequestMapping("updateBy") //按id编辑数据库中XAppMS字段
    public JsonResult updateById(@RequestParam String id,@RequestParam String msg) {
        JiekouEntity jiekouEntity = new JiekouEntity();
        jiekouEntity.setId(id);
        jiekouEntity.setXAppMS(msg);
        boolean u = jiekouService.updateById(jiekouEntity);
        if(u){
            return JsonResult.ok("更新字段成功");
        }
        return JsonResult.error("更新字段失败");
    }

    @RequestMapping("del") //按id或package删除
    public JsonResult removeByMap(@RequestParam Map<String,Object> map) {
        String id = (String)map.get("id");
        String packageStr = (String)map.get("package");
        if(!StringUtils.isEmpty(id)){
            DeleteFileUtil.deleteDirectory(AppConstants.SAVE_PATH + id);
            jiekouService.removeByMap(map);
            return JsonResult.ok("按id删除成功");
        }else if(!StringUtils.isEmpty(packageStr)){
            List<String> idList = jiekouDao.delByPackageList(packageStr);
            for(String packageId: idList){
                DeleteFileUtil.deleteDirectory(AppConstants.SAVE_PATH + packageId);
            }
            jiekouService.removeByMap(map);
            return JsonResult.ok("按package删除成功");
        }
        return JsonResult.error("删除失败");
    }

    @RequestMapping("upload") //把Body中的字段插入数据库，并将Body中的Base64编码转换为图片,同时上传文件和转码图到服务器中
    public JsonResult uploadFileBufferToLocal(@RequestParam("file") MultipartFile file,JiekouEntity jiekouEntity, String image) {
        String status = jiekouService.uploadFileBufferToLocal(file,jiekouEntity,image);
        Map map = new HashMap();
        map.put("apk名称",file.getOriginalFilename());
        map.put("id",jiekouEntity.getId());
        return JsonResult.ok(status).put(map);
    }

    @RequestMapping("download") //根据前端提供的id和文件类型来下载对应的文件
    public String fileDownload(@RequestParam String id, @RequestParam String type,HttpServletResponse response) throws UnsupportedEncodingException {
        return jiekouService.fileDownload(id,type,response);
    }

}
