package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;
import java.util.List;

public class GoodsWithBLOBs extends Goods implements Serializable {
    private List<GoodsAttr> attrs;
    private String detail;
    private String gallerys;
    private GoodsModal model;
    private List<GoodsSpec> specs;

    public GoodsModal getModel() {
        return this.model;
    }

    public void setModel(GoodsModal model2) {
        this.model = model2;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail2) {
        this.detail = detail2;
    }

    public String getGallerys() {
        return this.gallerys;
    }

    public void setGallerys(String gallerys2) {
        this.gallerys = gallerys2;
    }

    public List<GoodsAttr> getAttrs() {
        return this.attrs;
    }

    public void setAttrs(List<GoodsAttr> attrs2) {
        this.attrs = attrs2;
    }

    public List<GoodsSpec> getSpecs() {
        return this.specs;
    }

    public void setSpecs(List<GoodsSpec> specs2) {
        this.specs = specs2;
    }
}
