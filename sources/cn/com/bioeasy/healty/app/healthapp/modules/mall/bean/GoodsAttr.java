package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;

public class GoodsAttr implements Serializable {
    private int attrId;
    private long ctime;
    private int goodsId;
    private int id;
    private Object mtime;
    private String name;
    private Object remark;
    private String value;

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

    public int getAttrId() {
        return this.attrId;
    }

    public void setAttrId(int attrId2) {
        this.attrId = attrId2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
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
