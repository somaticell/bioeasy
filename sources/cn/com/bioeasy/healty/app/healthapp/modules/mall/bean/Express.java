package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;

public class Express implements Serializable {
    private String company;
    private Long ctime;
    private Integer id;
    private Long mtime;
    private String price;
    private String props;
    private Integer status;
    private String trackingNo;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id2) {
        this.id = id2;
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

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company2) {
        this.company = company2;
    }

    public String getTrackingNo() {
        return this.trackingNo;
    }

    public void setTrackingNo(String trackingNo2) {
        this.trackingNo = trackingNo2;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price2) {
        this.price = price2;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status2) {
        this.status = status2;
    }

    public String getProps() {
        return this.props;
    }

    public void setProps(String props2) {
        this.props = props2;
    }
}
