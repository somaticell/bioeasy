package cn.com.bioeasy.app.upload;

import cn.com.bioeasy.app.RxBus;
import cn.com.bioeasy.app.event.UploadEventInternal;
import java.io.IOException;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class UploadRequestBody extends RequestBody {
    private BufferedSink bufferedSink;
    @Inject
    RxBus bus;
    private final RequestBody requestBody;
    /* access modifiers changed from: private */
    public UploadTask task;

    public UploadRequestBody(RequestBody requestBody2, UploadTask task2) {
        this.requestBody = requestBody2;
        this.task = task2;
    }

    public MediaType contentType() {
        return this.requestBody.contentType();
    }

    public long contentLength() throws IOException {
        return this.requestBody.contentLength();
    }

    public void writeTo(BufferedSink sink) throws IOException {
        if (this.bufferedSink == null) {
            this.bufferedSink = Okio.buffer(sink(sink));
        }
        this.requestBody.writeTo(this.bufferedSink);
        this.bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            long bytesWritten = 0;
            long contentLength = 0;

            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (this.contentLength == 0) {
                    this.contentLength = UploadRequestBody.this.contentLength();
                }
                this.bytesWritten += byteCount;
                UploadRequestBody.this.bus.post(new UploadEventInternal(byteCount, UploadRequestBody.this.task));
            }
        };
    }
}
