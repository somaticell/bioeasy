package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;

public class OrderEvaluation implements Serializable {
    private static final long serialVersionUID = 1;
    private Long ctime;
    private String evaluation;
    private Integer express;
    private Integer id;
    private String ipAddress;
    private Long mtime;
    private Long orderId;
    private Integer quality;
    private Integer service;
    private Integer userId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id2) {
        this.id = id2;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId2) {
        this.orderId = orderId2;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId2) {
        this.userId = userId2;
    }

    public String getEvaluation() {
        return this.evaluation;
    }

    public void setEvaluation(String evaluation2) {
        this.evaluation = evaluation2 == null ? null : evaluation2.trim();
    }

    public Integer getQuality() {
        return this.quality;
    }

    public void setQuality(Integer quality2) {
        this.quality = quality2;
    }

    public Integer getService() {
        return this.service;
    }

    public void setService(Integer service2) {
        this.service = service2;
    }

    public Integer getExpress() {
        return this.express;
    }

    public void setExpress(Integer express2) {
        this.express = express2;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress2) {
        this.ipAddress = ipAddress2 == null ? null : ipAddress2.trim();
    }

    public Long getCtime() {
        return this.ctime;
    }

    public void setCtime(Long ctime2) {
        this.ctime = ctime2;
    }

    public Long getMtime() {
        return this.mtime;
    }

    public void setMtime(Long mtime2) {
        this.mtime = mtime2;
    }
}
