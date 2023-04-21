package cn.com.bioeasy.healty.app.healthapp.modules.mall.view.tagview;

import java.io.Serializable;

public class Tag implements Serializable {
    private static final long serialVersionUID = 2684657309332033242L;
    private int backgroundResId;
    private int id;
    private boolean isChecked;
    private String key;
    private int leftDrawableResId;
    private int rightDrawableResId;
    private String title;
    private String typeValue;
    private String value;

    public String getTypeValue() {
        return this.typeValue;
    }

    public void setTypeValue(String typeValue2) {
        this.typeValue = typeValue2;
    }

    public Tag() {
    }

    public Tag(int paramInt, String paramString) {
        this.id = paramInt;
        this.title = paramString;
    }

    public int getBackgroundResId() {
        return this.backgroundResId;
    }

    public int getId() {
        return this.id;
    }

    public int getLeftDrawableResId() {
        return this.leftDrawableResId;
    }

    public int getRightDrawableResId() {
        return this.rightDrawableResId;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setBackgroundResId(int paramInt) {
        this.backgroundResId = paramInt;
    }

    public void setChecked(boolean paramBoolean) {
        this.isChecked = paramBoolean;
    }

    public void setId(int paramInt) {
        this.id = paramInt;
    }

    public void setLeftDrawableResId(int paramInt) {
        this.leftDrawableResId = paramInt;
    }

    public void setRightDrawableResId(int paramInt) {
        this.rightDrawableResId = paramInt;
    }

    public void setTitle(String paramString) {
        this.title = paramString;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }
}
