package cn.com.bioeasy.healty.app.healthapp.modules.mine.bean;

import java.io.Serializable;

public class PresentBean implements Serializable {
    private int counts;
    private String imgUrl;
    private String productName;
    private int scores;

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl2) {
        this.imgUrl = imgUrl2;
    }

    public int getCounts() {
        return this.counts;
    }

    public void setCounts(int counts2) {
        this.counts = counts2;
    }

    public int getScores() {
        return this.scores;
    }

    public void setScores(int scores2) {
        this.scores = scores2;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName2) {
        this.productName = productName2;
    }

    public PresentBean(String productName2, String imgUrl2, int counts2, int scores2) {
        this.productName = productName2;
        this.imgUrl = imgUrl2;
        this.counts = counts2;
        this.scores = scores2;
    }
}
