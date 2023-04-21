package cn.com.bioeasy.healty.app.healthapp.modules.home.bean;

public class HealthInfoResponse {
    private String date;
    private int imagesId;
    private int readCount;
    private String title;

    public HealthInfoResponse(int imagesId2, String title2, int readCount2, String date2) {
        this.imagesId = imagesId2;
        this.title = title2;
        this.readCount = readCount2;
        this.date = date2;
    }

    public int getImagesId() {
        return this.imagesId;
    }

    public void setImagesId(int imagesId2) {
        this.imagesId = imagesId2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public int getReadCount() {
        return this.readCount;
    }

    public void setReadCount(int readCount2) {
        this.readCount = readCount2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }
}
