package cn.com.bioeasy.healty.app.healthapp.modules.home.bean;

import java.io.Serializable;

public class BusinessOperator implements Serializable {
    private static final long serialVersionUID = 1;
    private String businessAddress;
    private String businessCertificate;
    private Integer category;
    private String contact;
    private String corporation;
    private Long ctime;
    private Integer id;
    private Integer insNgCount;
    private Integer insTotalCount;
    private Double latitude;
    private String licenseSn;
    private Integer licenseType;
    private Double longitude;
    private Long mtime;
    private String name;
    private Integer orgId;
    private String orgName;
    private Integer productTypes;
    private String sn;
    private Integer status;
    private String tel;

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

    public Integer getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Integer orgId2) {
        this.orgId = orgId2;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn2) {
        this.sn = sn2 == null ? null : sn2.trim();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2 == null ? null : name2.trim();
    }

    public Integer getLicenseType() {
        return this.licenseType;
    }

    public void setLicenseType(Integer licenseType2) {
        this.licenseType = licenseType2;
    }

    public String getLicenseSn() {
        return this.licenseSn;
    }

    public void setLicenseSn(String licenseSn2) {
        this.licenseSn = licenseSn2 == null ? null : licenseSn2.trim();
    }

    public String getCorporation() {
        return this.corporation;
    }

    public void setCorporation(String corporation2) {
        this.corporation = corporation2 == null ? null : corporation2.trim();
    }

    public String getBusinessCertificate() {
        return this.businessCertificate;
    }

    public void setBusinessCertificate(String businessCertificate2) {
        this.businessCertificate = businessCertificate2 == null ? null : businessCertificate2.trim();
    }

    public String getBusinessAddress() {
        return this.businessAddress;
    }

    public void setBusinessAddress(String businessAddress2) {
        this.businessAddress = businessAddress2 == null ? null : businessAddress2.trim();
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact2) {
        this.contact = contact2 == null ? null : contact2.trim();
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel2) {
        this.tel = tel2 == null ? null : tel2.trim();
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status2) {
        this.status = status2;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName2) {
        this.orgName = orgName2;
    }

    public Integer getProductTypes() {
        return this.productTypes;
    }

    public void setProductTypes(Integer productTypes2) {
        this.productTypes = productTypes2;
    }

    public Integer getInsTotalCount() {
        return this.insTotalCount;
    }

    public void setInsTotalCount(Integer insTotalCount2) {
        this.insTotalCount = insTotalCount2;
    }

    public Integer getInsNgCount() {
        return this.insNgCount;
    }

    public void setInsNgCount(Integer insNgCount2) {
        this.insNgCount = insNgCount2;
    }

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category2) {
        this.category = category2;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude2) {
        this.longitude = longitude2;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude2) {
        this.latitude = latitude2;
    }
}
