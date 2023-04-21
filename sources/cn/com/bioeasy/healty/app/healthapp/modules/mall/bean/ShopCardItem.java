package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;
import org.litepal.crud.DataSupport;

public class ShopCardItem extends DataSupport implements Serializable {
    private int amount;
    private int goodId;
    private String goodSpecKeyNames;
    private String goodSpecKeys;
    private Goods goods;
    private int goodsSpecId;
    private int id;
    private boolean isChecked;
    private boolean isEditing;
    private String price;
    private int status;

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

    public int getGoodId() {
        return this.goodId;
    }

    public void setGoodId(int goodId2) {
        this.goodId = goodId2;
    }

    public String getGoodSpecKeys() {
        return this.goodSpecKeys;
    }

    public void setGoodSpecKeys(String goodSpecKeys2) {
        this.goodSpecKeys = goodSpecKeys2;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount2) {
        this.amount = amount2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public Goods getGoods() {
        return this.goods;
    }

    public void setGoods(Goods goods2) {
        this.goods = goods2;
    }

    public String getGoodSpecKeyNames() {
        return this.goodSpecKeyNames;
    }

    public void setGoodSpecKeyNames(String goodSpecKeyNames2) {
        this.goodSpecKeyNames = goodSpecKeyNames2;
    }

    public int getGoodsSpecId() {
        return this.goodsSpecId;
    }

    public void setGoodsSpecId(int goodsSpecId2) {
        this.goodsSpecId = goodsSpecId2;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price2) {
        this.price = price2;
    }
}
