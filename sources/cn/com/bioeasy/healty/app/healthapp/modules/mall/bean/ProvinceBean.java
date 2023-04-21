package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.util.List;

public class ProvinceBean {
    private List<CityBean> cityList;
    private String name;

    public ProvinceBean() {
    }

    public ProvinceBean(String name2, List<CityBean> cityList2) {
        this.name = name2;
        this.cityList = cityList2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public List<CityBean> getCityList() {
        return this.cityList;
    }

    public void setCityList(List<CityBean> cityList2) {
        this.cityList = cityList2;
    }

    public String toString() {
        return "ProvinceModel [name=" + this.name + ", cityList=" + this.cityList + "]";
    }
}
