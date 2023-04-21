package cn.com.bioeasy.healty.app.healthapp.modules.home.bean;

public class SampleItemResultData extends SampleResultData {
    private static final long serialVersionUID = 1;
    private String approveDate;
    private String approveUser;
    private String itemName;
    private String itemSn;
    private Integer itemType;
    private Integer sampleItemId;
    private String unit;
    private String value;

    public Integer getItemType() {
        return this.itemType;
    }

    public void setItemType(Integer itemType2) {
        this.itemType = itemType2;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName2) {
        this.itemName = itemName2;
    }

    public Integer getSampleItemId() {
        return this.sampleItemId;
    }

    public void setSampleItemId(Integer sampleItemId2) {
        this.sampleItemId = sampleItemId2;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit2) {
        this.unit = unit2;
    }

    public String getItemSn() {
        return this.itemSn;
    }

    public void setItemSn(String itemSn2) {
        this.itemSn = itemSn2;
    }

    public String getApproveUser() {
        return this.approveUser;
    }

    public void setApproveUser(String approveUser2) {
        this.approveUser = approveUser2;
    }

    public String getApproveDate() {
        return this.approveDate;
    }

    public void setApproveDate(String approveDate2) {
        this.approveDate = approveDate2;
    }
}
