package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;
import org.litepal.crud.DataSupport;

public class ShopCartBean extends DataSupport implements Serializable {
    private boolean checked;
    private int goodId;
    private int selectGoodsNum;

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked2) {
        this.checked = checked2;
    }

    public int getGoodId() {
        return this.goodId;
    }

    public void setGoodId(int goodId2) {
        this.goodId = goodId2;
    }

    public int getSelectGoodsNum() {
        return this.selectGoodsNum;
    }

    public void setSelectGoodsNum(int selectGoodsNum2) {
        this.selectGoodsNum = selectGoodsNum2;
    }
}
