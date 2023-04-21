package cn.com.bioeasy.healty.app.healthapp.modules.mall.bean;

import java.io.Serializable;
import java.util.List;
import org.litepal.crud.DataSupport;

public class Goods extends DataSupport implements Serializable {
    private int brandId;
    private Object browseCount;
    private int categoryId;
    private Object clickCount;
    private Object commentCount;
    private String commission;
    private String costPrice;
    private long ctime;
    private String icon;
    private int id;
    private int integral;
    private Object isComment;
    private Object isFreePost;
    private Object isHot;
    private Object isNew;
    private String marketPrice;
    private Object memberPrice;
    private int modelId;
    private Object mtime;
    private String name;
    private Object onSale;
    private Object remark;
    private int saleCount;
    private String shopPrice;
    private String sku;
    private String sn;
    private String spu;
    private List<GoodsEvaluationStar> starList;
    private int stockCount;
    private String summary;
    private String tags;
    private String weight;

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

    public Object getMtime() {
        return this.mtime;
    }

    public void setMtime(Object mtime2) {
        this.mtime = mtime2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn2) {
        this.sn = sn2;
    }

    public String getSpu() {
        return this.spu;
    }

    public void setSpu(String spu2) {
        this.spu = spu2;
    }

    public String getSku() {
        return this.sku;
    }

    public void setSku(String sku2) {
        this.sku = sku2;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight2) {
        this.weight = weight2;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId2) {
        this.categoryId = categoryId2;
    }

    public int getModelId() {
        return this.modelId;
    }

    public void setModelId(int modelId2) {
        this.modelId = modelId2;
    }

    public int getBrandId() {
        return this.brandId;
    }

    public void setBrandId(int brandId2) {
        this.brandId = brandId2;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon2) {
        this.icon = icon2;
    }

    public int getStockCount() {
        return this.stockCount;
    }

    public void setStockCount(int stockCount2) {
        this.stockCount = stockCount2;
    }

    public String getShopPrice() {
        return this.shopPrice;
    }

    public void setShopPrice(String shopPrice2) {
        this.shopPrice = shopPrice2;
    }

    public String getMarketPrice() {
        return this.marketPrice;
    }

    public void setMarketPrice(String marketPrice2) {
        this.marketPrice = marketPrice2;
    }

    public String getCostPrice() {
        return this.costPrice;
    }

    public void setCostPrice(String costPrice2) {
        this.costPrice = costPrice2;
    }

    public Object getMemberPrice() {
        return this.memberPrice;
    }

    public void setMemberPrice(Object memberPrice2) {
        this.memberPrice = memberPrice2;
    }

    public Object getCommentCount() {
        return this.commentCount;
    }

    public void setCommentCount(Object commentCount2) {
        this.commentCount = commentCount2;
    }

    public Object getBrowseCount() {
        return this.browseCount;
    }

    public void setBrowseCount(Object browseCount2) {
        this.browseCount = browseCount2;
    }

    public Object getClickCount() {
        return this.clickCount;
    }

    public void setClickCount(Object clickcount) {
        this.clickCount = clickcount;
    }

    public Object getOnSale() {
        return this.onSale;
    }

    public void setOnSale(Object onSale2) {
        this.onSale = onSale2;
    }

    public Object getIsComment() {
        return this.isComment;
    }

    public void setIsComment(Object isComment2) {
        this.isComment = isComment2;
    }

    public Object getIsNew() {
        return this.isNew;
    }

    public void setIsNew(Object isNew2) {
        this.isNew = isNew2;
    }

    public Object getIsHot() {
        return this.isHot;
    }

    public void setIsHot(Object isHot2) {
        this.isHot = isHot2;
    }

    public Object getIsFreePost() {
        return this.isFreePost;
    }

    public void setIsFreePost(Object isFreePost2) {
        this.isFreePost = isFreePost2;
    }

    public String getCommission() {
        return this.commission;
    }

    public void setCommission(String commission2) {
        this.commission = commission2;
    }

    public int getIntegral() {
        return this.integral;
    }

    public void setIntegral(int integral2) {
        this.integral = integral2;
    }

    public String getTags() {
        return this.tags;
    }

    public void setTags(String tags2) {
        this.tags = tags2;
    }

    public Object getRemark() {
        return this.remark;
    }

    public void setRemark(Object remark2) {
        this.remark = remark2;
    }

    public int getSaleCount() {
        return this.saleCount;
    }

    public void setSaleCount(int saleCount2) {
        this.saleCount = saleCount2;
    }

    public List<GoodsEvaluationStar> getStarList() {
        return this.starList;
    }

    public void setStarList(List<GoodsEvaluationStar> starList2) {
        this.starList = starList2;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary2) {
        this.summary = summary2;
    }
}
