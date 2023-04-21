package net.oschina.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public final class StreamUtil {
    public static void close(Closeable... closeables) {
        if (closeables != null && closeables.length != 0) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static boolean copyFile(File srcFile, File saveFile) {
        File parentFile = saveFile.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            return false;
        }
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            BufferedInputStream inputStream2 = new BufferedInputStream(new FileInputStream(srcFile));
            try {
                BufferedOutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(saveFile));
                try {
                    byte[] buffer = new byte[4096];
                    while (true) {
                        int len = inputStream2.read(buffer);
                        if (len != -1) {
                            outputStream2.write(buffer, 0, len);
                        } else {
                            outputStream2.flush();
                            close(inputStream2, outputStream2);
                            return true;
                        }
                    }
                } catch (IOException e) {
                    e = e;
                    outputStream = outputStream2;
                    inputStream = inputStream2;
                    try {
                        e.printStackTrace();
                        close(inputStream, outputStream);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        close(inputStream, outputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    outputStream = outputStream2;
                    inputStream = inputStream2;
                    close(inputStream, outputStream);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                inputStream = inputStream2;
                e.printStackTrace();
                close(inputStream, outputStream);
                return false;
            } catch (Throwable th3) {
                th = th3;
                inputStream = inputStream2;
                close(inputStream, outputStream);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            close(inputStream, outputStream);
            return false;
        }
    }
}
