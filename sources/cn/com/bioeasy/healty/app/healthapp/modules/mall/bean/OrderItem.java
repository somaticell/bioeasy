package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private Integer amount;
    private Long ctime;
    private String getGoodsSpecName;
    private Goods goods;
    private Integer goodsId;
    private Integer goodsSpecId;
    private Integer id;
    private Long mtime;
    private Long orderId;
    private String props;
    private String remark;
    private String totalPrice;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id2) {
        this.id = id2;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long orderId2) {
        this.orderId = orderId2;
    }

    public Integer getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Integer goodsId2) {
        this.goodsId = goodsId2;
    }

    public Integer getGoodsSpecId() {
        return this.goodsSpecId;
    }

    public void setGoodsSpecId(Integer goodsSpecId2) {
        this.goodsSpecId = goodsSpecId2;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount2) {
        this.amount = amount2;
    }

    public String getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(String totalPrice2) {
        this.totalPrice = totalPrice2 == null ? null : totalPrice2.trim();
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

    public Goods getGoods() {
        return this.goods;
    }

    public void setGoods(Goods goods2) {
        this.goods = goods2;
    }

    public Long getCtime() {
        return this.ctime;
    }

    public void setCtime(Long ctime2) {
        this.ctime = ctime2;
    }

    public Long getMtime() {
        return this.mtime;
    }

    public void setMtime(Long mtime2) {
        this.mtime = mtime2;
    }

    public String getGetGoodsSpecName() {
        return this.getGoodsSpecName;
    }

    public void setGetGoodsSpecName(String getGoodsSpecName2) {
        this.getGoodsSpecName = getGoodsSpecName2;
    }
}
