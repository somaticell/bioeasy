package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

public class RecommendGoodsBean {
    private String currentPrice;
    private Integer goodsId;
    private String image;
    private String price;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image2) {
        this.image = image2;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price2) {
        this.price = price2;
    }

    public String getCurrentPrice() {
        return this.currentPrice;
    }

    public void setCurrentPrice(String currentPrice2) {
        this.currentPrice = currentPrice2;
    }

    public Integer getGoodsId() {
        return this.goodsId;
    }

    public void setGoodsId(Integer goodsId2) {
        this.goodsId = goodsId2;
    }
}
