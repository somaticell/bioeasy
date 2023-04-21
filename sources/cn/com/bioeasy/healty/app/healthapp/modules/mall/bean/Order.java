package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private Integer addressId;
    private Long ctime;
    private Integer expressId;
    private Long id;
    private String invoiceTitle;
    private Integer invoiceType;
    private List<OrderItem> items;
    private Long mtime;
    private String props;
    private String remark;
    private Byte status;
    private String totalPrice;
    private Integer userId;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId2) {
        this.userId = userId2;
    }

    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status2) {
        this.status = status2;
    }

    public Integer getExpressId() {
        return this.expressId;
    }

    public void setExpressId(Integer expressId2) {
        this.expressId = expressId2;
    }

    public Integer getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Integer addressId2) {
        this.addressId = addressId2;
    }

    public Integer getInvoiceType() {
        return this.invoiceType;
    }

    public void setInvoiceType(Integer invoiceType2) {
        this.invoiceType = invoiceType2;
    }

    public String getInvoiceTitle() {
        return this.invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle2) {
        this.invoiceTitle = invoiceTitle2 == null ? null : invoiceTitle2.trim();
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

    public List<OrderItem> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItem> items2) {
        this.items = items2;
    }

    public long getCtime() {
        return this.ctime.longValue();
    }

    public void setCtime(long ctime2) {
        this.ctime = Long.valueOf(ctime2);
    }

    public long getMtime() {
        return this.mtime.longValue();
    }

    public void setMtime(long mtime2) {
        this.mtime = Long.valueOf(mtime2);
    }
}
