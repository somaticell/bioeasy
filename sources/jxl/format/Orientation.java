package jxl.format;

import com.alibaba.fastjson.asm.Opcodes;

public final class Orientation {
    public static Orientation HORIZONTAL = new Orientation(0, "horizontal");
    public static Orientation MINUS_45 = new Orientation(135, "down 45");
    public static Orientation MINUS_90 = new Orientation(Opcodes.GETFIELD, "down 90");
    public static Orientation PLUS_45 = new Orientation(45, "up 45");
    public static Orientation PLUS_90 = new Orientation(90, "up 90");
    public static Orientation STACKED = new Orientation(255, "stacked");
    public static Orientation VERTICAL = new Orientation(255, "vertical");
    private static Orientation[] orientations = new Orientation[0];
    private String string;
    private int value;

    protected Orientation(int val, String s) {
        this.value = val;
        this.string = s;
        Orientation[] oldorients = orientations;
        orientations = new Orientation[(oldorients.length + 1)];
        System.arraycopy(oldorients, 0, orientations, 0, oldorients.length);
        orientations[oldorients.length] = this;
    }

    public int getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.string;
    }

    public static Orientation getOrientation(int val) {
        for (int i = 0; i < orientations.length; i++) {
            if (orientations[i].getValue() == val) {
                return orientations[i];
            }
        }
        return HORIZONTAL;
    }
}
