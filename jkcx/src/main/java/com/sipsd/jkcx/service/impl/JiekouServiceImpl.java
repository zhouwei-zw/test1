package com.sipsd.jkcx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sipsd.jkcx.constants.AppConstants;
import com.sipsd.jkcx.dao.JiekouDao;
import com.sipsd.jkcx.model.JiekouEntity;
import com.sipsd.jkcx.service.JiekouService;
import com.sipsd.jkcx.util.JsonResult;
import com.sipsd.jkcx.util.MyMappingJackson2HttpMessageConverter;
import com.sipsd.jkcx.vo.JiekouVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class JiekouServiceImpl extends ServiceImpl<JiekouDao, JiekouEntity> implements JiekouService {

    @Autowired
    JiekouDao jiekouDao;

    @Override
    public void getAndSave(JiekouEntity jiekouEntity) {
        //获得rest请求发送器
        RestTemplate restTemplate = new RestTemplate();
        //添加 [text/html;charset=UTF-8] 类型支持
        restTemplate.getMessageConverters().add(new MyMappingJackson2HttpMessageConverter());
        //请求网址获取数据并转为列表
        JiekouEntity[] mJiekouEntity = restTemplate.getForObject("http://58.210.9.133/DataCenter/ApkHandler/QueryApkHandler.ashx", JiekouEntity[].class);
        List<JiekouEntity> jiekouEntities = Arrays.asList(mJiekouEntity);

        log.info(String.valueOf(jiekouEntities));
        this.saveBatch(jiekouEntities);
    }

    @Override
    public JsonResult paging(JiekouVo jiekouVo) {
        //参数一是当前页，参数二是每页个数
        IPage<JiekouEntity> userPage = new Page<>(jiekouVo.getCurrent(), jiekouVo.getSize());

        //如果前端提供了name，则根据name进行模糊查询
        if(!StringUtils.isEmpty(jiekouVo.getName())){ //如果输入的名字不为空
            IPage<JiekouEntity> queryByNameList = jiekouDao.selectByName(userPage,jiekouVo.getName()); //将sql语句筛选出的字段添加到数组queryByNameList
//            ArrayList<JiekouEntity> queryByNameList = new ArrayList<>();
//            for(JiekouEntity jiekouEntity: newestList) { //对于newestList中的每一项
//                if(jiekouEntity.getName().contains(jiekouVo.getName())){ //如果包含输入的name，则添加到数组queryByNameList
//                    queryByNameList.add(jiekouEntity);
//                }
//            }
            log.info(String.valueOf(queryByNameList)); //打印选取的项目列表
            if(queryByNameList.getTotal()==0){
                return JsonResult.ok("未找到名为" + jiekouVo.getName() + "的项目").put(queryByNameList);
            }else{
                return JsonResult.ok("按名字查询成功").put(queryByNameList);
            }
        }

        // 如果未提供name参数，默认查询每个name中最新的项目（调用dao中getNewestList方法的sql语句）
        IPage<JiekouEntity> mapIPage = jiekouDao.getNewestList(userPage);
        log.info(String.valueOf(mapIPage)); //打印选取的项目列表
        return JsonResult.ok("已列出每个name中最新的项目").put(mapIPage);
    }

    @Override
    public List<JiekouEntity> queryByMap(Map<String, Object> map) {
        //查询条件构造
        QueryWrapper<JiekouEntity> jiekouEntityQueryWrapper = new QueryWrapper<>();
        jiekouEntityQueryWrapper.groupBy();
        //如果id不为空，就根据id来查询
        String id = (String)map.get("id");
        if (!StringUtils.isEmpty(id)) {
            jiekouEntityQueryWrapper.eq("id",id);
        }
        //如果name不为空，就根据name来查询
        String name = (String)map.get("name");
        if (!StringUtils.isEmpty(name)) {
            jiekouEntityQueryWrapper.like("name",name);
        }
        //如果package不为空，就根据package来查询
        String packageStr = (String)map.get("package");
        if (!StringUtils.isEmpty(packageStr)) {
            jiekouEntityQueryWrapper.eq("package",packageStr);
        }
        jiekouEntityQueryWrapper.orderByDesc("updatetime");
        return this.list(jiekouEntityQueryWrapper);
    }

    @Transactional //在具体的类或类的方法上使用该注解，在未成功上传时操作回滚，不要使用在类所要实现的任何接口上 **该注解只能用在public方法上https://blog.csdn.net/qq_41517071/article/details/96282332
    @Override
    public String uploadFileBufferToLocal(MultipartFile file, JiekouEntity jiekouEntity, String image) {

        //如果上传文件非空，则执行下面语句
        if (!file.isEmpty()) {
            try {
                //将body内数据库字段插入表中
                this.save(jiekouEntity);

                //根据输入的id自动创建指定路径的文件夹
                String filePath = AppConstants.FILE_PATH + jiekouEntity.getId() + "/"; //设置文件的绝对路径
                String savePath = AppConstants.SAVE_PATH + jiekouEntity.getId() + "/"; //设置文件的相对路径
                File newdir = new File(filePath);
                //检测是否存在目录
                if (!newdir.exists()){
                    newdir.mkdirs(); //file.mkdirs()：只要有权限，就可以创建成功；file.mkdir()：必须目录a和目录b都存在才会成功
                    log.info("创建文件夹成功");
                }

                // 获取文件名
                String fileName = file.getOriginalFilename();
                log.info("上传的文件名为：" + fileName);
                // 获取文件的后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                log.info("文件的后缀名为：" + suffixName);

                //输出文件到指定路径
                File uploadFile = new File(filePath + file.getOriginalFilename());
                file.transferTo(uploadFile);
                //file = null;
//                //输出文件到指定路径
//                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(newdir, file.getOriginalFilename())));
//                FileOutputStream fos = new FileOutputStream(new File(newdir, file.getOriginalFilename())); //1.创建FileOutputStream对象，构造方法中绑定要输出的目的地
//                BufferedOutputStream bos = new BufferedOutputStream(fos); //2.创建BufferedOutputStream对象，构造方法中传递BufferedOutputStream对象，提高BufferedOutputStream对象效率
//                System.out.println(file.getOriginalFilename());
//                log.info(file.getOriginalFilename());
//                bos.write(file.getBytes()); //3.使用BufferedOutputStream对象中的方法write，把数据写入到内部缓冲区中
//                bos.flush(); //4.使用BufferedOutputStream对象中的方法flush，把内部缓冲区中的数据，刷新到文件中
//                bos.close(); //5.释放资源
//                log.info(System.getProperty("user.dir"));

                //Base64解码
                BASE64Decoder decoder = new BASE64Decoder();
                //因为base64编码传递到后台之后会将“+”转换成空格，所以要request.getParameter("imgStr").replace("data:image/png;base64,", "").replace(" ", "+");进行替换回来
                byte[] b = decoder.decodeBuffer(image.replace("data:image/png;base64,", "").replace(" ", "+"));
                for(int i=0;i<b.length;++i)
                {
                    if(b[i]<0) //调整异常数据
                        {
                            b[i]+=256;
                        }
                }
                //生成png图片
                String imgFilePath = filePath; //新生成的图片路径
                String imgName = jiekouEntity.getId() + ".png"; //图片文件名
                log.info("上传图片的文件名为：" + imgName);
                OutputStream out = new FileOutputStream(imgFilePath + imgName);
                out.write(b);
                out.flush();
                out.close();

                //插入apk文件的相对路径到jiekouEntity中
                jiekouEntity.setXAppPath(savePath + file.getOriginalFilename());
                //插入base64图片的相对路径到jiekouEntity中
                jiekouEntity.setPFilepath(savePath + jiekouEntity.getId() + ".png");
                //插入当前时间到jiekouEntity中
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                jiekouEntity.setUpdatetime(df.format(new Date()));
//                插入服务器ip地址jiekouEntity中
//                String address = InetAddress.getLocalHost().getHostAddress();
                jiekouEntity.setIp("122.193.33.86:8083");

                this.updateById(jiekouEntity);
//                System.out.println(jiekouEntity); //打印已插入表中的数据
                log.info(String.valueOf(jiekouEntity)); //打印已插入表中的数据
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            return "文件上传成功";
        } else {
            return "上传失败，因为文件是空的";
        }
    }

    @Override
    public String fileDownload(String id,String type,HttpServletResponse response) throws UnsupportedEncodingException {
        JiekouEntity byId = this.getById(id); //根据id查询数据库中对应字段

        String fileName = ""; //下载的文件名
        if(type.equals("apk")){ //如果输入的type类型是.apk，则提取XAppPath里的apk文件名
            fileName = byId.getXAppPath().substring(byId.getXAppPath().lastIndexOf("/") + 1); //截取/后的字符串
        }else if(type.equals("png")){ //如果输入的type类型是.png，则提取PFilepath里的png文件名
            fileName = byId.getPFilepath().substring(byId.getPFilepath().lastIndexOf("/") + 1);
        }else{
            return "不支持的文件类型";
        }

        log.info("下载的文件名为：" + fileName);
        String downloadFilePath = AppConstants.FILE_PATH + id + "/" + fileName; //被下载的文件在服务器中的路径
        File file = new File(downloadFilePath);

        if (file.exists()) {
//            // 配置文件下载
//            response.setContentType("application/octet-stream");
//            response.setHeader("content-type", "application/octet-stream");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")); //下载文件能正常显示中文

            response.setContentType("application/force-download");// 设置强制下载不打开
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("utf-8"),"ISO8859-1")); //下载文件名能正常显示中文
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    outputStream.flush();
                    i = bis.read(buffer);
                }
                return "下载成功";
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "下载失败";
    }

}
