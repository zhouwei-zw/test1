package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;
import java.io.Serializable;

/**
 * 公共机构考核(KhGgjgdf)实体类
 *
 * @author makejava
 * @since 2021-07-30 11:15:39
 */
public class KhGgjgdf implements Serializable {
    private static final long serialVersionUID = 589206335728427659L;
    /**
     * id
     */
    private String id;
    /**
     * 单位名称
     */
    private String dwmc;
    /**
     * 所属社区
     */
    private String sqmc;
    /**
     * 考核日期
     */
    private Date khrq;
    /**
     * 总得分
     */
    private String zdf;
    /**
     * 合计扣分
     */
    private String hjkf;
    /**
     * 分类基础设施设置扣分项（40）
     */
    private String fljcsssz;
    /**
     * 分类指示牌缺失
     */
    private String jcssZspqs;
    /**
     * 分类指示牌不规范
     */
    private String jcssZspbgf;
    /**
     * 分类桶缺失
     */
    private String jcssFltqs;
    /**
     * 分类桶颜色不规范
     */
    private String jcssFltysbgf;
    /**
     * 有害垃圾桶内胆缺失
     */
    private String jcssYhljtndqs;
    /**
     * 小计扣分
     */
    private String jcssXjkf;
    /**
     * 分类容器及收集点日常维护扣分项（40）
     */
    private String flrqsjdrcwh;
    /**
     * 损坏
     */
    private String rcwhSh;
    /**
     * 污垢
     */
    private String rcwhWg;
    /**
     * 有图贴
     */
    private String rcwhYtt;
    /**
     * 收集点周边脏乱
     */
    private String rcwhSjdzbzl;
    /**
     * 分类垃圾桶满溢
     */
    private String rcwhFltmy;
    /**
     * 分类桶摆放不规范
     */
    private String rcwhFltbfbgf;
    /**
     * 分类桶不能正常投放
     */
    private String rcwhFltbnzctf;
    /**
     * 小计扣分
     */
    private String rcwhXjkf;
    /**
     * 分类投放准确率扣分项（15）
     */
    private String fltfzql;
    /**
     * 分类收集情况（5分）
     */
    private String flsjqk;
    /**
     * 车辆无标识
     */
    private String flsjClwbs;
    /**
     * 车辆不整洁
     */
    private String flsjClbzj;
    /**
     * 小计扣分
     */
    private String flsjXjkf;
    /**
     * 额外扣分项
     */
    private String ewkfx;
    /**
     * 考核不配合
     */
    private String ewkfxKhbph;
    /**
     * 混装收运
     */
    private String ewkfxHzsy;
    /**
     * 小计扣分
     */
    private String ewkfxXjkf;
    /**
     * 考核人员
     */
    private String khry;
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
     * 考核状态
     */
    private String khzt;
    /**
     * 删除状态（0：未删除；1：已删除）
     */
    private String removed;

    @TableField(exist = false)
    private String count;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getSqmc() {
        return sqmc;
    }

    public void setSqmc(String sqmc) {
        this.sqmc = sqmc;
    }

    public Date getKhrq() {
        return khrq;
    }

    public void setKhrq(Date khrq) {
        this.khrq = khrq;
    }

    public String getZdf() {
        return zdf;
    }

    public void setZdf(String zdf) {
        this.zdf = zdf;
    }

    public String getHjkf() {
        return hjkf;
    }

    public void setHjkf(String hjkf) {
        this.hjkf = hjkf;
    }

    public String getFljcsssz() {
        return fljcsssz;
    }

    public void setFljcsssz(String fljcsssz) {
        this.fljcsssz = fljcsssz;
    }

    public String getJcssZspqs() {
        return jcssZspqs;
    }

    public void setJcssZspqs(String jcssZspqs) {
        this.jcssZspqs = jcssZspqs;
    }

    public String getJcssZspbgf() {
        return jcssZspbgf;
    }

    public void setJcssZspbgf(String jcssZspbgf) {
        this.jcssZspbgf = jcssZspbgf;
    }

    public String getJcssFltqs() {
        return jcssFltqs;
    }

    public void setJcssFltqs(String jcssFltqs) {
        this.jcssFltqs = jcssFltqs;
    }

    public String getJcssFltysbgf() {
        return jcssFltysbgf;
    }

    public void setJcssFltysbgf(String jcssFltysbgf) {
        this.jcssFltysbgf = jcssFltysbgf;
    }

    public String getJcssYhljtndqs() {
        return jcssYhljtndqs;
    }

    public void setJcssYhljtndqs(String jcssYhljtndqs) {
        this.jcssYhljtndqs = jcssYhljtndqs;
    }

    public String getJcssXjkf() {
        return jcssXjkf;
    }

    public void setJcssXjkf(String jcssXjkf) {
        this.jcssXjkf = jcssXjkf;
    }

    public String getFlrqsjdrcwh() {
        return flrqsjdrcwh;
    }

    public void setFlrqsjdrcwh(String flrqsjdrcwh) {
        this.flrqsjdrcwh = flrqsjdrcwh;
    }

    public String getRcwhSh() {
        return rcwhSh;
    }

    public void setRcwhSh(String rcwhSh) {
        this.rcwhSh = rcwhSh;
    }

    public String getRcwhWg() {
        return rcwhWg;
    }

    public void setRcwhWg(String rcwhWg) {
        this.rcwhWg = rcwhWg;
    }

    public String getRcwhYtt() {
        return rcwhYtt;
    }

    public void setRcwhYtt(String rcwhYtt) {
        this.rcwhYtt = rcwhYtt;
    }

    public String getRcwhSjdzbzl() {
        return rcwhSjdzbzl;
    }

    public void setRcwhSjdzbzl(String rcwhSjdzbzl) {
        this.rcwhSjdzbzl = rcwhSjdzbzl;
    }

    public String getRcwhFltmy() {
        return rcwhFltmy;
    }

    public void setRcwhFltmy(String rcwhFltmy) {
        this.rcwhFltmy = rcwhFltmy;
    }

    public String getRcwhFltbfbgf() {
        return rcwhFltbfbgf;
    }

    public void setRcwhFltbfbgf(String rcwhFltbfbgf) {
        this.rcwhFltbfbgf = rcwhFltbfbgf;
    }

    public String getRcwhFltbnzctf() {
        return rcwhFltbnzctf;
    }

    public void setRcwhFltbnzctf(String rcwhFltbnzctf) {
        this.rcwhFltbnzctf = rcwhFltbnzctf;
    }

    public String getRcwhXjkf() {
        return rcwhXjkf;
    }

    public void setRcwhXjkf(String rcwhXjkf) {
        this.rcwhXjkf = rcwhXjkf;
    }

    public String getFltfzql() {
        return fltfzql;
    }

    public void setFltfzql(String fltfzql) {
        this.fltfzql = fltfzql;
    }

    public String getFlsjqk() {
        return flsjqk;
    }

    public void setFlsjqk(String flsjqk) {
        this.flsjqk = flsjqk;
    }

    public String getFlsjClwbs() {
        return flsjClwbs;
    }

    public void setFlsjClwbs(String flsjClwbs) {
        this.flsjClwbs = flsjClwbs;
    }

    public String getFlsjClbzj() {
        return flsjClbzj;
    }

    public void setFlsjClbzj(String flsjClbzj) {
        this.flsjClbzj = flsjClbzj;
    }

    public String getFlsjXjkf() {
        return flsjXjkf;
    }

    public void setFlsjXjkf(String flsjXjkf) {
        this.flsjXjkf = flsjXjkf;
    }

    public String getEwkfx() {
        return ewkfx;
    }

    public void setEwkfx(String ewkfx) {
        this.ewkfx = ewkfx;
    }

    public String getEwkfxKhbph() {
        return ewkfxKhbph;
    }

    public void setEwkfxKhbph(String ewkfxKhbph) {
        this.ewkfxKhbph = ewkfxKhbph;
    }

    public String getEwkfxHzsy() {
        return ewkfxHzsy;
    }

    public void setEwkfxHzsy(String ewkfxHzsy) {
        this.ewkfxHzsy = ewkfxHzsy;
    }

    public String getEwkfxXjkf() {
        return ewkfxXjkf;
    }

    public void setEwkfxXjkf(String ewkfxXjkf) {
        this.ewkfxXjkf = ewkfxXjkf;
    }

    public String getKhry() {
        return khry;
    }

    public void setKhry(String khry) {
        this.khry = khry;
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

    public String getKhzt() {
        return khzt;
    }

    public void setKhzt(String khzt) {
        this.khzt = khzt;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

}
