package cn.com.bioeasy.healty.app.healthapp.modules.record.bean;

import java.io.Serializable;

public class ProductTypeItemCond implements Serializable {
    private String city;
    private int days;
    private String distinct;
    private String province;

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province2) {
        this.province = province2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city2) {
        this.city = city2;
    }

    public String getDistinct() {
        return this.distinct;
    }

    public void setDistinct(String distinct2) {
        this.distinct = distinct2;
    }

    public int getDays() {
        return this.days;
    }

    public void setDays(int days2) {
        this.days = days2;
    }
}
