package com.facebook.imagepipeline.platform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.MemoryFile;
import com.facebook.common.internal.ByteStreams;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.streams.LimitedInputStream;
import com.facebook.common.webp.WebpSupportStatus;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import javax.annotation.Nullable;

public class GingerbreadPurgeableDecoder extends DalvikPurgeableDecoder {
    private static Method sGetFileDescriptorMethod;
    private final boolean mWebpSupportEnabled;

    public /* bridge */ /* synthetic */ CloseableReference decodeFromEncodedImage(EncodedImage x0, Bitmap.Config x1) {
        return super.decodeFromEncodedImage(x0, x1);
    }

    public /* bridge */ /* synthetic */ CloseableReference decodeJPEGFromEncodedImage(EncodedImage x0, Bitmap.Config x1, int x2) {
        return super.decodeJPEGFromEncodedImage(x0, x1, x2);
    }

    public /* bridge */ /* synthetic */ CloseableReference pinBitmap(Bitmap x0) {
        return super.pinBitmap(x0);
    }

    public GingerbreadPurgeableDecoder(boolean webpSupportEnabled) {
        this.mWebpSupportEnabled = webpSupportEnabled;
    }

    /* access modifiers changed from: protected */
    public Bitmap decodeByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> bytesRef, BitmapFactory.Options options) {
        return decodeFileDescriptorAsPurgeable(bytesRef, bytesRef.get().size(), (byte[]) null, options);
    }

    /* access modifiers changed from: protected */
    public Bitmap decodeJPEGByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> bytesRef, int length, BitmapFactory.Options options) {
        return decodeFileDescriptorAsPurgeable(bytesRef, length, endsWithEOI(bytesRef, length) ? null : EOI, options);
    }

    private static MemoryFile copyToMemoryFile(CloseableReference<PooledByteBuffer> bytesRef, int inputLength, @Nullable byte[] suffix) throws IOException {
        MemoryFile memoryFile = new MemoryFile((String) null, inputLength + (suffix == null ? 0 : suffix.length));
        memoryFile.allowPurging(false);
        PooledByteBufferInputStream pbbIs = null;
        LimitedInputStream is = null;
        try {
            PooledByteBufferInputStream pbbIs2 = new PooledByteBufferInputStream(bytesRef.get());
            try {
                LimitedInputStream is2 = new LimitedInputStream(pbbIs2, inputLength);
                try {
                    OutputStream os = memoryFile.getOutputStream();
                    ByteStreams.copy(is2, os);
                    if (suffix != null) {
                        memoryFile.writeBytes(suffix, 0, inputLength, suffix.length);
                    }
                    CloseableReference.closeSafely((CloseableReference<?>) bytesRef);
                    Closeables.closeQuietly((InputStream) pbbIs2);
                    Closeables.closeQuietly((InputStream) is2);
                    Closeables.close(os, true);
                    return memoryFile;
                } catch (Throwable th) {
                    th = th;
                    is = is2;
                    pbbIs = pbbIs2;
                    CloseableReference.closeSafely((CloseableReference<?>) bytesRef);
                    Closeables.closeQuietly((InputStream) pbbIs);
                    Closeables.closeQuietly((InputStream) is);
                    Closeables.close((Closeable) null, true);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                pbbIs = pbbIs2;
                CloseableReference.closeSafely((CloseableReference<?>) bytesRef);
                Closeables.closeQuietly((InputStream) pbbIs);
                Closeables.closeQuietly((InputStream) is);
                Closeables.close((Closeable) null, true);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            CloseableReference.closeSafely((CloseableReference<?>) bytesRef);
            Closeables.closeQuietly((InputStream) pbbIs);
            Closeables.closeQuietly((InputStream) is);
            Closeables.close((Closeable) null, true);
            throw th;
        }
    }

    private synchronized Method getFileDescriptorMethod() {
        if (sGetFileDescriptorMethod == null) {
            try {
                sGetFileDescriptorMethod = MemoryFile.class.getDeclaredMethod("getFileDescriptor", new Class[0]);
            } catch (Exception e) {
                throw Throwables.propagate(e);
            }
        }
        return sGetFileDescriptorMethod;
    }

    private FileDescriptor getMemoryFileDescriptor(MemoryFile memoryFile) {
        try {
            return (FileDescriptor) getFileDescriptorMethod().invoke(memoryFile, new Object[0]);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    /* access modifiers changed from: protected */
    public Bitmap decodeFileDescriptorAsPurgeable(CloseableReference<PooledByteBuffer> bytesRef, int inputLength, byte[] suffix, BitmapFactory.Options options) {
        Bitmap bitmap;
        MemoryFile memoryFile = null;
        try {
            memoryFile = copyToMemoryFile(bytesRef, inputLength, suffix);
            FileDescriptor fd = getMemoryFileDescriptor(memoryFile);
            if (this.mWebpSupportEnabled) {
                bitmap = WebpSupportStatus.sWebpBitmapFactory.decodeFileDescriptor(fd, (Rect) null, options);
            } else {
                bitmap = BitmapFactory.decodeFileDescriptor(fd, (Rect) null, options);
            }
            Bitmap bitmap2 = (Bitmap) Preconditions.checkNotNull(bitmap, "BitmapFactory returned null");
            if (memoryFile != null) {
                memoryFile.close();
            }
            return bitmap2;
        } catch (IOException e) {
            throw Throwables.propagate(e);
        } catch (Throwable th) {
            if (memoryFile != null) {
                memoryFile.close();
            }
            throw th;
        }
    }
}
