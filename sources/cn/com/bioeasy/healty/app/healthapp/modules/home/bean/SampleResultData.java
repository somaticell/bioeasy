package cn.com.bioeasy.healty.app.healthapp.modules.home.bean;

import java.io.Serializable;

public class SampleResultData implements Serializable {
    private static final long serialVersionUID = 1;
    private Long ctime;
    private Integer operatorId;
    private String operatorSn;
    private Integer orgId;
    private String orgName;
    private String parentOrgName;
    private Integer processType;
    private Integer productId;
    private String productName;
    private Integer productType;
    private Integer result;
    private String sampleCode;
    private Integer sampleId;
    private String sampleSn;
    private Long sampleTime;
    private Integer status;
    private String tester;

    public Integer getSampleId() {
        return this.sampleId;
    }

    public void setSampleId(Integer sampleId2) {
        this.sampleId = sampleId2;
    }

    public String getSampleSn() {
        return this.sampleSn;
    }

    public void setSampleSn(String sampleSn2) {
        this.sampleSn = sampleSn2;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName2) {
        this.productName = productName2;
    }

    public Integer getProductType() {
        return this.productType;
    }

    public void setProductType(Integer productType2) {
        this.productType = productType2;
    }

    public String getOperatorSn() {
        return this.operatorSn;
    }

    public void setOperatorSn(String operatorSn2) {
        this.operatorSn = operatorSn2;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName2) {
        this.orgName = orgName2;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result2) {
        this.result = result2;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId2) {
        this.productId = productId2;
    }

    public Integer getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Integer orgId2) {
        this.orgId = orgId2;
    }

    public Integer getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(Integer operatorId2) {
        this.operatorId = operatorId2;
    }

    public Long getCtime() {
        return this.ctime;
    }

    public void setCtime(Long ctime2) {
        this.ctime = ctime2;
    }

    public Long getSampleTime() {
        return this.sampleTime;
    }

    public void setSampleTime(Long sampleTime2) {
        this.sampleTime = sampleTime2;
    }

    public String getSampleCode() {
        return this.sampleCode;
    }

    public void setSampleCode(String sampleCode2) {
        this.sampleCode = sampleCode2;
    }

    public String getTester() {
        return this.tester;
    }

    public void setTester(String tester2) {
        this.tester = tester2;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status2) {
        this.status = status2;
    }

    public Integer getProcessType() {
        return this.processType;
    }

    public void setProcessType(Integer processType2) {
        this.processType = processType2;
    }

    public String getParentOrgName() {
        return this.parentOrgName;
    }

    public void setParentOrgName(String parentOrgName2) {
        this.parentOrgName = parentOrgName2;
    }
}
