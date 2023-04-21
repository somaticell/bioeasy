package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;

public class OrderPay implements Serializable {
    private static final long serialVersionUID = 1;
    private Long ctime;
    private Integer id;
    private Long mtime;
    private Long orderId;
    private String payId;
    private Integer payType;
    private String platform;
    private Double price;
    private String props;
    private Integer status;
    private Integer userId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id2) {
        this.id = id2;
    }

    public String getPayId() {
        return this.payId;
    }

    public void setPayId(String payId2) {
        this.payId = payId2 == null ? null : payId2.trim();
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

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform2) {
        this.platform = platform2 == null ? null : platform2.trim();
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setPayType(Integer payType2) {
        this.payType = payType2;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status2) {
        this.status = status2;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price2) {
        this.price = price2;
    }

    public String getProps() {
        return this.props;
    }

    public void setProps(String props2) {
        this.props = props2 == null ? null : props2.trim();
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
