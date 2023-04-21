package net.oschina.common.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class BitmapUtil {
    public static final int DEFAULT_BUFFER_SIZE = 65536;

    public static BitmapFactory.Options createOptions() {
        return new BitmapFactory.Options();
    }

    @TargetApi(11)
    public static void resetOptions(BitmapFactory.Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;
        if (11 <= Build.VERSION.SDK_INT) {
            options.inBitmap = null;
            options.inMutable = true;
        }
    }

    public static String getExtension(String filePath) {
        BitmapFactory.Options options = createOptions();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        String mimeType = options.outMimeType;
        return mimeType.substring(mimeType.lastIndexOf("/") + 1);
    }

    public static Bitmap decodeBitmap(File file, int maxWidth, int maxHeight, byte[] byteStorage, BitmapFactory.Options options, boolean exactDecode) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(file), byteStorage == null ? 65536 : byteStorage.length);
            if (options == null) {
                options = createOptions();
            } else {
                resetOptions(options);
            }
            options.inJustDecodeBounds = true;
            is.mark(5242880);
            BitmapFactory.decodeStream(is, (Rect) null, options);
            try {
                is.reset();
                calculateScaling(options, maxWidth, maxHeight, exactDecode);
                if (byteStorage == null) {
                    byteStorage = new byte[65536];
                }
                options.inTempStorage = byteStorage;
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeStream(is, (Rect) null, options);
                StreamUtil.close(is);
                resetOptions(options);
                return scaleBitmap(bitmap, maxWidth, maxHeight, true);
            } catch (IOException e) {
                e.printStackTrace();
                StreamUtil.close(is);
                resetOptions(options);
                return null;
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap scaleBitmap(Bitmap source, float scale, boolean recycleSource) {
        if (scale <= 0.0f || scale >= 1.0f) {
            return source;
        }
        Matrix m = new Matrix();
        int width = source.getWidth();
        int height = source.getHeight();
        m.setScale(scale, scale);
        Bitmap createBitmap = Bitmap.createBitmap(source, 0, 0, width, height, m, false);
        if (!recycleSource) {
            return createBitmap;
        }
        source.recycle();
        return createBitmap;
    }

    public static Bitmap scaleBitmap(Bitmap source, int targetMaxWidth, int targetMaxHeight, boolean recycleSource) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        Bitmap scaledBitmap = source;
        if (sourceWidth > targetMaxWidth || sourceHeight > targetMaxHeight) {
            float minScale = Math.min(((float) targetMaxWidth) / ((float) sourceWidth), ((float) targetMaxHeight) / ((float) sourceHeight));
            scaledBitmap = Bitmap.createScaledBitmap(scaledBitmap, (int) (((float) sourceWidth) * minScale), (int) (((float) sourceHeight) * minScale), false);
            if (recycleSource) {
                source.recycle();
            }
        }
        return scaledBitmap;
    }

    private static BitmapFactory.Options calculateScaling(BitmapFactory.Options options, int requestedMaxWidth, int requestedMaxHeight, boolean exactDecode) {
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        if (sourceWidth > requestedMaxWidth || sourceHeight > requestedMaxHeight) {
            float maxFloatFactor = Math.max(((float) sourceHeight) / ((float) requestedMaxHeight), ((float) sourceWidth) / ((float) requestedMaxWidth));
            int lesserOrEqualSampleSize = Math.max(1, Integer.highestOneBit((int) Math.floor((double) maxFloatFactor)));
            options.inSampleSize = lesserOrEqualSampleSize;
            if (exactDecode && Build.VERSION.SDK_INT >= 19) {
                options.inTargetDensity = 1000;
                options.inDensity = (int) (((double) (1000.0f * ((((float) sourceWidth) / ((float) lesserOrEqualSampleSize)) / (((float) sourceWidth) / maxFloatFactor)))) + 0.5d);
                if (options.inTargetDensity != options.inDensity) {
                    options.inScaled = true;
                } else {
                    options.inTargetDensity = 0;
                    options.inDensity = 0;
                }
            }
        }
        return options;
    }

    public static final class Compressor {
        public static File compressImage(File sourceFile, long maxSize, int minQuality, int maxWidth, int maxHeight) {
            return compressImage(sourceFile, maxSize, minQuality, maxWidth, maxHeight, true);
        }

        public static File compressImage(File sourceFile, long maxSize, int minQuality, int maxWidth, int maxHeight, boolean exactDecode) {
            return compressImage(sourceFile, maxSize, minQuality, maxWidth, maxHeight, (byte[]) null, (BitmapFactory.Options) null, exactDecode);
        }

        public static File compressImage(File sourceFile, long maxSize, int minQuality, int maxWidth, int maxHeight, byte[] byteStorage, BitmapFactory.Options options, boolean exactDecode) {
            if (sourceFile == null || !sourceFile.exists() || !sourceFile.canRead()) {
                return null;
            }
            File file = new File(sourceFile.getParent(), String.format("compress_%s.temp", new Object[]{Long.valueOf(System.currentTimeMillis())}));
            if (!file.exists()) {
                try {
                    if (!file.createNewFile()) {
                        return null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            Bitmap bitmap = BitmapUtil.decodeBitmap(sourceFile, maxWidth, maxHeight, byteStorage, options, exactDecode);
            if (bitmap == null) {
                return null;
            }
            Bitmap.CompressFormat compressFormat = bitmap.hasAlpha() ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
            boolean isOk = false;
            for (int i = 1; i <= 10; i++) {
                int quality = 92;
                while (true) {
                    BufferedOutputStream outputStream = null;
                    try {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                        try {
                            bitmap.compress(compressFormat, quality, bufferedOutputStream);
                            StreamUtil.close(bufferedOutputStream);
                            if (file.length() > maxSize) {
                                if (quality < minQuality) {
                                    break;
                                }
                                quality--;
                            } else {
                                isOk = true;
                                break;
                            }
                        } catch (IOException e2) {
                            e = e2;
                            outputStream = bufferedOutputStream;
                            try {
                                e.printStackTrace();
                                bitmap.recycle();
                                StreamUtil.close(outputStream);
                                return null;
                            } catch (Throwable th) {
                                th = th;
                                StreamUtil.close(outputStream);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            outputStream = bufferedOutputStream;
                            StreamUtil.close(outputStream);
                            throw th;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        bitmap.recycle();
                        StreamUtil.close(outputStream);
                        return null;
                    }
                }
                if (isOk) {
                    break;
                }
                bitmap = BitmapUtil.scaleBitmap(bitmap, 1.0f - (0.2f * ((float) i)), true);
            }
            bitmap.recycle();
            if (!isOk) {
                return null;
            }
            return file;
        }
    }
}
