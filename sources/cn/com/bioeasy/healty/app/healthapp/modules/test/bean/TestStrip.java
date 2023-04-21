package cn.com.bioeasy.healty.app.healthapp.modules.test.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class TestStrip implements Serializable {
    private Integer categoryId;
    private Integer ctDirection;
    private Integer id;
    private List<Integer> itemIdList;
    private List<Item> itemList;
    private Integer judgeType;
    private String name;
    private BigDecimal negativeValue;
    private BigDecimal positiveValue;
    private Integer testCount;

    public List<Integer> getItemIdList() {
        return this.itemIdList;
    }

    public void setItemIdList(List<Integer> itemIdList2) {
        this.itemIdList = itemIdList2;
    }

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
        this.name = name2;
    }

    public Integer getTestCount() {
        return this.testCount;
    }

    public void setTestCount(Integer testCount2) {
        this.testCount = testCount2;
    }

    public Integer getJudgeType() {
        return this.judgeType;
    }

    public void setJudgeType(Integer judgeType2) {
        this.judgeType = judgeType2;
    }

    public Integer getCtDirection() {
        return this.ctDirection;
    }

    public void setCtDirection(Integer ctDirection2) {
        this.ctDirection = ctDirection2;
    }

    public BigDecimal getPositiveValue() {
        return this.positiveValue;
    }

    public void setPositiveValue(BigDecimal positiveValue2) {
        this.positiveValue = positiveValue2;
    }

    public BigDecimal getNegativeValue() {
        return this.negativeValue;
    }

    public void setNegativeValue(BigDecimal negativeValue2) {
        this.negativeValue = negativeValue2;
    }

    public List<Item> getItemList() {
        return this.itemList;
    }

    public void setItemList(List<Item> itemList2) {
        this.itemList = itemList2;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId2) {
        this.categoryId = categoryId2;
    }
}
