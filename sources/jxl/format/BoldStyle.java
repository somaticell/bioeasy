package jxl.format;

import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;

public class BoldStyle {
    public static final BoldStyle BOLD = new BoldStyle(ActionConstant.tagComment, "Bold");
    public static final BoldStyle NORMAL = new BoldStyle(400, "Normal");
    private String string;
    private int value;

    protected BoldStyle(int val, String s) {
        this.value = val;
        this.string = s;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.string;
    }
}
