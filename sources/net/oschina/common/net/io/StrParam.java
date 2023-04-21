package net.oschina.common.net.io;

public class StrParam {
    public String key;
    public String value;

    public StrParam(String key2, String value2) {
        this.key = key2;
        this.value = value2;
    }

    public StrParam(String key2, int value2) {
        this(key2, String.valueOf(value2));
    }

    public StrParam(String key2, float value2) {
        this(key2, String.valueOf(value2));
    }

    public StrParam(String key2, long value2) {
        this(key2, String.valueOf(value2));
    }

    public StrParam(String key2, double value2) {
        this(key2, String.valueOf(value2));
    }
}
