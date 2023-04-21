package com.facebook.common.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Files {
    private Files() {
    }

    static byte[] readFile(InputStream in, long expectedSize) throws IOException {
        if (expectedSize <= 2147483647L) {
            return expectedSize == 0 ? ByteStreams.toByteArray(in) : ByteStreams.toByteArray(in, (int) expectedSize);
        }
        throw new OutOfMemoryError("file is too large to fit in a byte array: " + expectedSize + " bytes");
    }

    public static byte[] toByteArray(File file) throws IOException {
        FileInputStream in = null;
        try {
            FileInputStream in2 = new FileInputStream(file);
            try {
                byte[] readFile = readFile(in2, in2.getChannel().size());
                if (in2 != null) {
                    in2.close();
                }
                return readFile;
            } catch (Throwable th) {
                th = th;
                in = in2;
            }
        } catch (Throwable th2) {
            th = th2;
            if (in != null) {
                in.close();
            }
            throw th;
        }
    }
}
