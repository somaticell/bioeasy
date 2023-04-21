package cn.com.bioeasy.healty.app.healthapp.modules.test.bean;

import java.io.Serializable;

public class TestResultItem implements Serializable {
    private Integer area;
    private String areaValue;
    private Integer height;
    private Item item;
    private Integer position;
    private Integer result;
    private String value;

    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(Integer position2) {
        this.position = position2;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height2) {
        this.height = height2;
    }

    public Integer getArea() {
        return this.area;
    }

    public void setArea(Integer area2) {
        this.area = area2;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result2) {
        this.result = result2;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item2) {
        this.item = item2;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public String getAreaValue() {
        return this.areaValue;
    }

    public void setAreaValue(String areaValue2) {
        this.areaValue = areaValue2;
    }

    public String toString() {
        String resultStr = "[P：" + getPosition() + ", H：" + getHeight();
        if (this.value != null) {
            return resultStr + ", T/C：" + getValue() + "]";
        }
        return resultStr + "]";
    }
}
