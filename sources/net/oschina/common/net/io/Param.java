package net.oschina.common.net.io;

import java.io.File;

public class Param extends StrParam {
    private boolean isFile;

    public Param(String key, File file) {
        super(key, file.getAbsolutePath());
        this.isFile = true;
    }

    public Param(IOParam param) {
        this(param.key, param.file);
    }

    public Param(StrParam strParam) {
        super(strParam.key, strParam.value);
    }

    public Param(String key, String value) {
        super(key, value);
    }

    public Param(String key, int value) {
        super(key, value);
    }

    public Param(String key, float value) {
        super(key, value);
    }

    public Param(String key, long value) {
        super(key, value);
    }

    public Param(String key, double value) {
        super(key, value);
    }

    public StrParam getStringParam() {
        return new StrParam(this.key, this.value);
    }

    public IOParam getFileParam() {
        return new IOParam(this.key, new File(this.value));
    }

    public boolean isFile() {
        return this.isFile;
    }
}
