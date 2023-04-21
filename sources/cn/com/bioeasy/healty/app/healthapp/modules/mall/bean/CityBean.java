package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.util.List;

public class CityBean {
    private List<DistrictBean> districtList;
    private String name;

    public CityBean() {
    }

    public CityBean(String name2, List<DistrictBean> districtList2) {
        this.name = name2;
        this.districtList = districtList2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public List<DistrictBean> getDistrictList() {
        return this.districtList;
    }

    public void setDistrictList(List<DistrictBean> districtList2) {
        this.districtList = districtList2;
    }

    public String toString() {
        return "CityModel [name=" + this.name + ", districtList=" + this.districtList + "]";
    }
}
