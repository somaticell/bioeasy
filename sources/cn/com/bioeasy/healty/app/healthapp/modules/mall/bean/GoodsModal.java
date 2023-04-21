package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;
import java.util.List;

public class GoodsModal implements Serializable {
    private List<GoodsModalAttr> attrs;
    private long ctime;
    private int id;
    private long mtime;
    private String name;
    private Object remark;
    private List<GoodsModalSpec> specs;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public Object getRemark() {
        return this.remark;
    }

    public void setRemark(Object remark2) {
        this.remark = remark2;
    }

    public List<GoodsModalAttr> getAttrs() {
        return this.attrs;
    }

    public void setAttrs(List<GoodsModalAttr> attrs2) {
        this.attrs = attrs2;
    }

    public List<GoodsModalSpec> getSpecs() {
        return this.specs;
    }

    public void setSpecs(List<GoodsModalSpec> specs2) {
        this.specs = specs2;
    }
}
