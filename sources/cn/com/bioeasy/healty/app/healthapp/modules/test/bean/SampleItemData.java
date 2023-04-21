package cn.com.bioeasy.healty.app.healthapp.modules.test.bean;

import java.io.Serializable;
import java.util.Date;
import org.litepal.crud.DataSupport;

public class SampleItemData extends DataSupport implements Serializable {
    private Date date;
    private int id;
    private int itemId;
    private String itemName;
    private int itemType;
    private int result;
    private int sampleId;
    private String unit;
    private String value;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int getSampleId() {
        return this.sampleId;
    }

    public void setSampleId(int sampleId2) {
        this.sampleId = sampleId2;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId2) {
        this.itemId = itemId2;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName2) {
        this.itemName = itemName2;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit2) {
        this.unit = unit2;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date2) {
        this.date = date2;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result2) {
        this.result = result2;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public int getItemType() {
        return this.itemType;
    }

    public void setItemType(int itemType2) {
        this.itemType = itemType2;
    }
}
