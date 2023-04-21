package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;

public class GoodsModalSpec implements Serializable {
    private long ctime;
    private int id;
    private int modelId;
    private long mtime;
    private String name;
    private Object remark;
    private Object src;
    private String value;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public long getCtime() {
        return this.ctime;
    }

    public void setCtime(long ctime2) {
        this.ctime = ctime2;
    }

    public long getMtime() {
        return this.mtime;
    }

    public void setMtime(long mtime2) {
        this.mtime = mtime2;
    }

    public int getModelId() {
        return this.modelId;
    }

    public void setModelId(int modelId2) {
        this.modelId = modelId2;
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

    public Object getRemark() {
        return this.remark;
    }

    public void setRemark(Object remark2) {
        this.remark = remark2;
    }

    public Object getSrc() {
        return this.src;
    }

    public void setSrc(Object src2) {
        this.src = src2;
    }
}
