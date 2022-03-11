package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 扣分明细表(KhKfmx)实体类
 *
 * @author makejava
 * @since 2021-08-02 12:06:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "kh_kfmx")
public class KhKfmx implements Serializable {
    private static final long serialVersionUID = 292814705436033744L;
    /**
     * id
     */
    private String id;
    /**
     * 考核表id
     */
    private String khid;
    /**
     * 规则表id
     */
    private String gzid;
    /**
     * 扣分
     */
    private String kf;
    /**
     * 扣分说明
     */
    private String kfsm;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 创建人员
     */
    private String createuser;
    /**
     * 更新时间
     */
    private Date updatetime;
    /**
     * 更新人员
     */
    private String updateuser;
    /**
     * 删除状态
     */
    private String removed;
    /**
     * 整改前图片
     */
    private String beforphoto;
    /**
     * 整改后图片
     */
    private String afterphoto;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKhid() {
        return khid;
    }

    public void setKhid(String khid) {
        this.khid = khid;
    }

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public String getKf() {
        return kf;
    }

    public void setKf(String kf) {
        this.kf = kf;
    }

    public String getKfsm() {
        return kfsm;
    }

    public void setKfsm(String kfsm) {
        this.kfsm = kfsm;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    public String getBeforphoto() {
        return beforphoto;
    }

    public void setBeforphoto(String beforphoto) {
        this.beforphoto = beforphoto;
    }

    public String getAfterphoto() {
        return afterphoto;
    }

    public void setAfterphoto(String afterphoto) {
        this.afterphoto = afterphoto;
    }

}
