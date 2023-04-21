package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;

public class AddressBean implements Serializable {
    private String address;
    private Object city;
    private String consignee;
    private Object country;
    private long ctime;
    private Object district;
    private int id;
    private int isDefault;
    private String mobile;
    private Object mtime;
    private String province;
    private Object remark;
    private Object town;
    private int userId;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public long getCtime() {
        return this.ctime;
    }

    public void setCtime(long ctime2) {
        this.ctime = ctime2;
    }

    public Object getMtime() {
        return this.mtime;
    }

    public void setMtime(Object mtime2) {
        this.mtime = mtime2;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId2) {
        this.userId = userId2;
    }

    public String getConsignee() {
        return this.consignee;
    }

    public void setConsignee(String consignee2) {
        this.consignee = consignee2;
    }

    public Object getCountry() {
        return this.country;
    }

    public void setCountry(Object country2) {
        this.country = country2;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province2) {
        this.province = province2;
    }

    public Object getCity() {
        return this.city;
    }

    public void setCity(Object city2) {
        this.city = city2;
    }

    public Object getDistrict() {
        return this.district;
    }

    public void setDistrict(Object district2) {
        this.district = district2;
    }

    public Object getTown() {
        return this.town;
    }

    public void setTown(Object town2) {
        this.town = town2;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address2) {
        this.address = address2;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile2) {
        this.mobile = mobile2;
    }

    public int getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(int isDefault2) {
        this.isDefault = isDefault2;
    }

    public Object getRemark() {
        return this.remark;
    }

    public void setRemark(Object remark2) {
        this.remark = remark2;
    }
}
