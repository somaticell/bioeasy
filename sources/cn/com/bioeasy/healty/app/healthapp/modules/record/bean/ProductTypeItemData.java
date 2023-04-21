package cn.com.bioeasy.healty.app.healthapp.modules.record.bean;

import java.io.Serializable;

public class ProductTypeItemData implements Serializable {
    private int finishCount;
    private int invalidCount;
    private int productType;
    private int totalCount;

    public int getProductType() {
        return this.productType;
    }

    public void setProductType(int productType2) {
        this.productType = productType2;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount2) {
        this.totalCount = totalCount2;
    }

    public int getFinishCount() {
        return this.finishCount;
    }

    public void setFinishCount(int finishCount2) {
        this.finishCount = finishCount2;
    }

    public int getInvalidCount() {
        return this.invalidCount;
    }

    public void setInvalidCount(int invalidCount2) {
        this.invalidCount = invalidCount2;
    }
}
