package cn.com.bioeasy.healty.app.healthapp.modules.test.bean;

import java.io.Serializable;

public class Item implements Serializable {
    private Integer id;
    private String name;
    private String props;
    private String remark;
    private String sn;
    private Integer standardId;
    private String standardName;
    private String standardSn;
    private Integer type;
    private String valueBound;
    private String valueDefault;
    private String valueRemark;
    private String valueStandard;
    private String valueType;
    private String valueUnit;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id2) {
        this.id = id2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2 == null ? null : name2.trim();
    }

    public Integer getStandardId() {
        return this.standardId;
    }

    public void setStandardId(Integer standardId2) {
        this.standardId = standardId2;
    }

    public String getStandardName() {
        return this.standardName;
    }

    public void setStandardName(String standardName2) {
        this.standardName = standardName2 == null ? null : standardName2.trim();
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type2) {
        this.type = type2;
    }

    public String getValueType() {
        return this.valueType;
    }

    public void setValueType(String valueType2) {
        this.valueType = valueType2 == null ? null : valueType2.trim();
    }

    public String getValueBound() {
        return this.valueBound;
    }

    public void setValueBound(String valueBound2) {
        this.valueBound = valueBound2 == null ? null : valueBound2.trim();
    }

    public String getValueUnit() {
        return this.valueUnit;
    }

    public void setValueUnit(String valueUnit2) {
        this.valueUnit = valueUnit2 == null ? null : valueUnit2.trim();
    }

    public String getValueStandard() {
        return this.valueStandard;
    }

    public void setValueStandard(String valueStandard2) {
        this.valueStandard = valueStandard2 == null ? null : valueStandard2.trim();
    }

    public String getValueDefault() {
        return this.valueDefault;
    }

    public void setValueDefault(String valueDefault2) {
        this.valueDefault = valueDefault2 == null ? null : valueDefault2.trim();
    }

    public String getValueRemark() {
        return this.valueRemark;
    }

    public void setValueRemark(String valueRemark2) {
        this.valueRemark = valueRemark2 == null ? null : valueRemark2.trim();
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark2) {
        this.remark = remark2 == null ? null : remark2.trim();
    }

    public String getProps() {
        return this.props;
    }

    public void setProps(String props2) {
        this.props = props2 == null ? null : props2.trim();
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn2) {
        this.sn = sn2;
    }

    public String getStandardSn() {
        return this.standardSn;
    }

    public void setStandardSn(String standardSn2) {
        this.standardSn = standardSn2;
    }
}
