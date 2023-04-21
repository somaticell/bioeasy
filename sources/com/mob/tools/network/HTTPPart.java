package com.mob.tools.network;

import com.mob.tools.utils.ReflectHelper;
import java.io.InputStream;

public abstract class HTTPPart {
    private OnReadListener listener;
    private long offset;

    /* access modifiers changed from: protected */
    public abstract InputStream getInputStream() throws Throwable;

    /* access modifiers changed from: protected */
    public abstract long length() throws Throwable;

    public void setOffset(long offset2) {
        this.offset = offset2;
    }

    public InputStream toInputStream() throws Throwable {
        ByteCounterInputStream is = new ByteCounterInputStream(getInputStream());
        is.setOnInputStreamReadListener(this.listener);
        if (this.offset > 0) {
            is.skip(this.offset);
        }
        return is;
    }

    public Object getInputStreamEntity() throws Throwable {
        InputStream is = toInputStream();
        long length = length() - this.offset;
        ReflectHelper.importClass("org.apache.http.entity.InputStreamEntity");
        return ReflectHelper.newInstance("InputStreamEntity", is, Long.valueOf(length));
    }

    public void setOnReadListener(OnReadListener l) {
        this.listener = l;
    }
}
