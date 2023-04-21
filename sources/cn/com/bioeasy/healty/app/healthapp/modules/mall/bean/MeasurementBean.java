package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;
import org.litepal.crud.DataSupport;

public class MeasurementBean extends DataSupport implements Serializable {
    private int counts;
    private String desc;
    private int id;
    private String imgUrl;
    private boolean isChecked;
    private boolean isEditing;
    private double price;
    private String productName;
    private int status;
    private int stores;
    private int type;

    public int getStores() {
        return this.stores;
    }

    public void setStores(int stores2) {
        this.stores = stores2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc2) {
        this.desc = desc2;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public boolean isEditing() {
        return this.isEditing;
    }

    public void setEditing(boolean editing) {
        this.isEditing = editing;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public String getProductName() {
        return this.productName;
    }

    public MeasurementBean(int id2, int type2, double price2, String productName2, String imgUrl2, int counts2, int stores2) {
        this.price = price2;
        this.type = type2;
        this.productName = productName2;
        this.imgUrl = imgUrl2;
        this.counts = counts2;
        this.stores = stores2;
        if (id2 == 0) {
            this.id = id2 + 1;
        }
        this.id = id2;
    }

    public int getCounts() {
        return this.counts;
    }

    public void setCounts(int counts2) {
        this.counts = counts2;
    }

    public void setProductName(String productName2) {
        this.productName = productName2;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl2) {
        this.imgUrl = imgUrl2;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price2) {
        this.price = price2;
    }

    public void setIsEditing(boolean isEditing2) {
        this.isEditing = isEditing2;
    }
}
