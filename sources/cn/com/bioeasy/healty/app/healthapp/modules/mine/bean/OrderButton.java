package cn.com.bioeasy.healty.app.healthapp.modules.mine.bean;

public class OrderButton {
    private boolean isLight;
    private int tag;
    private String text;

    public OrderButton(String text2, int tag2, boolean isLight2) {
        this.text = text2;
        this.tag = tag2;
        this.isLight = isLight2;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text2) {
        this.text = text2;
    }

    public int getTag() {
        return this.tag;
    }

    public void setTag(int tag2) {
        this.tag = tag2;
    }

    public boolean isLight() {
        return this.isLight;
    }

    public void setIsLight(boolean isLight2) {
        this.isLight = isLight2;
    }
}
