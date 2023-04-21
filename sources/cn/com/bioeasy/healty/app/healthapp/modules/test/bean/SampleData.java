package cn.com.bioeasy.healty.app.healthapp.modules.test.bean;

import java.io.Serializable;
import java.util.List;
import org.litepal.crud.DataSupport;

public class SampleData extends DataSupport implements Serializable {
    private String address;
    private String city;
    private String deviceName;
    private String distinct;
    private int id;
    private String images;
    private List<SampleItemData> itemDataList;
    private double latitude;
    private double longitude;
    private String marketName;
    private int productType;
    private String province;
    private String remark;
    private int result;
    private String resultList;
    private int sampleId;
    private String sampleName;
    private int status;
    private String stripName;
    private String time;
    private int userId;

    public String getStripName() {
        return this.stripName;
    }

    public void setStripName(String stripName2) {
        this.stripName = stripName2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

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

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address2) {
        this.address = address2;
    }

    public String getMarketName() {
        return this.marketName;
    }

    public void setMarketName(String marketName2) {
        this.marketName = marketName2;
    }

    public String getSampleName() {
        return this.sampleName;
    }

    public void setSampleName(String sampleName2) {
        this.sampleName = sampleName2;
    }

    public List<SampleItemData> getItemDataList() {
        return this.itemDataList;
    }

    public void setItemDataList(List<SampleItemData> itemDataList2) {
        this.itemDataList = itemDataList2;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result2) {
        this.result = result2;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId2) {
        this.userId = userId2;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude2) {
        this.longitude = longitude2;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude2) {
        this.latitude = latitude2;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time2) {
        this.time = time2;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark2) {
        this.remark = remark2;
    }

    public String getImages() {
        return this.images;
    }

    public void setImages(String images2) {
        this.images = images2;
    }

    public int getProductType() {
        return this.productType;
    }

    public void setProductType(int productType2) {
        this.productType = productType2;
    }

    public int getSampleId() {
        return this.sampleId;
    }

    public void setSampleId(int sampleId2) {
        this.sampleId = sampleId2;
    }

    public String getResultList() {
        return this.resultList;
    }

    public void setResultList(String resultList2) {
        this.resultList = resultList2;
    }
}
