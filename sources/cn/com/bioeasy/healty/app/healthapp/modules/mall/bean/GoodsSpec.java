package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;

public class GoodsSpec implements Serializable {
    private long ctime;
    private int goodsId;
    private int id;
    private Object mtime;
    private String price;
    private Object remark;
    private String sku;
    private String specKeys;
    private String specNames;
    private String stockCount;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(int goodsId2) {
        this.goodsId = goodsId2;
    }

    public String getSpecKeys() {
        return this.specKeys;
    }

    public void setSpecKeys(String specKeys2) {
        this.specKeys = specKeys2;
    }

    public String getSpecNames() {
        return this.specNames;
    }

    public void setSpecNames(String specNames2) {
        this.specNames = specNames2;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price2) {
        this.price = price2;
    }

    public String getStockCount() {
        return this.stockCount;
    }

    public void setStockCount(String stockCount2) {
        this.stockCount = stockCount2;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku2) {
        this.sku = sku2;
    }

    public long getCtime() {
        return this.ctime;
    }

    public void setCtime(long ctime2) {
        this.ctime = ctime2;
    }

    public Object getMtime() {
        return this.mtime;
    }

    public void setMtime(Object mtime2) {
        this.mtime = mtime2;
    }

    public Object getRemark() {
        return this.remark;
    }

    public void setRemark(Object remark2) {
        this.remark = remark2;
    }
}
