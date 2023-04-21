package com.lvrenyang.io;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v4.media.TransportMediator;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.alibaba.fastjson.asm.Opcodes;
import com.facebook.imageutils.JfifUtil;
import com.flyco.tablayout.BuildConfig;

class ImageProcessing {
    private static int[][] Floyd16x16 = {new int[]{0, 128, 32, Opcodes.IF_ICMPNE, 8, 136, 40, 168, 2, TransportMediator.KEYCODE_MEDIA_RECORD, 34, 162, 10, 138, 42, 170}, new int[]{192, 64, 224, 96, 200, 72, 232, 104, 194, 66, 226, 98, BuildConfig.VERSION_CODE, 74, 234, 106}, new int[]{48, Opcodes.ARETURN, 16, 144, 56, Opcodes.INVOKESTATIC, 24, 152, 50, Opcodes.GETSTATIC, 18, 146, 58, 186, 26, Opcodes.IFNE}, new int[]{240, 112, JfifUtil.MARKER_RST0, 80, 248, 120, JfifUtil.MARKER_SOI, 88, 242, 114, 210, 82, 250, 122, JfifUtil.MARKER_SOS, 90}, new int[]{12, 140, 44, 172, 4, 132, 36, 164, 14, 142, 46, 174, 6, 134, 38, Opcodes.IF_ACMPNE}, new int[]{204, 76, 236, 108, 196, 68, 228, 100, 206, 78, 238, 110, Opcodes.IFNULL, 70, 230, 102}, new int[]{60, 188, 28, 156, 52, Opcodes.GETFIELD, 20, Opcodes.LCMP, 62, 190, 30, Opcodes.IFLE, 54, Opcodes.INVOKEVIRTUAL, 22, 150}, new int[]{252, 124, 220, 92, 244, 116, 212, 84, 254, 126, 222, 94, 246, 118, 214, 86}, new int[]{3, 131, 35, Opcodes.IF_ICMPGT, 11, 139, 43, 171, 1, 129, 33, 161, 9, 137, 41, Opcodes.RET}, new int[]{195, 67, 227, 99, 203, 75, 235, 107, Opcodes.INSTANCEOF, 65, JfifUtil.MARKER_APP1, 97, 201, 73, 233, 105}, new int[]{51, 179, 19, 147, 59, Opcodes.NEW, 27, 155, 49, Opcodes.RETURN, 17, 145, 57, Opcodes.INVOKEINTERFACE, 25, Opcodes.IFEQ}, new int[]{243, 115, 211, 83, 251, 123, 219, 91, 241, 113, 209, 81, 249, 121, JfifUtil.MARKER_EOI, 89}, new int[]{15, 143, 47, 175, 7, 135, 39, 167, 13, 141, 45, 173, 5, 133, 37, Opcodes.IF_ACMPEQ}, new int[]{207, 79, 239, 111, Opcodes.IFNONNULL, 71, 231, 103, 205, 77, 237, 109, 197, 69, 229, 101}, new int[]{63, 191, 31, Opcodes.IF_ICMPEQ, 55, Opcodes.INVOKESPECIAL, 23, Opcodes.DCMPL, 61, 189, 29, 157, 53, Opcodes.PUTFIELD, 21, Opcodes.FCMPL}, new int[]{254, TransportMediator.KEYCODE_MEDIA_PAUSE, 223, 95, 247, 119, JfifUtil.MARKER_RST7, 87, 253, 125, 221, 93, 245, 117, 213, 85}};

    ImageProcessing() {
    }

    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        Bitmap bmpGrayscale = Bitmap.createBitmap(bmpOriginal.getWidth(), bmpOriginal.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        c.drawBitmap(bmpOriginal, 0.0f, 0.0f, paint);
        return bmpGrayscale;
    }

    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) w) / ((float) bitmapWidth), ((float) h) / ((float) bitmapHeight));
        return Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapHeight, matrix, false);
    }

    public static void format_K_dither16x16(int xsize, int ysize, byte[] orgpixels, boolean[] despixels) {
        int k = 0;
        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {
                if ((orgpixels[k] & BLEServiceApi.CONNECTED_STATUS) > Floyd16x16[x & 15][y & 15]) {
                    despixels[k] = false;
                } else {
                    despixels[k] = true;
                }
                k++;
            }
        }
    }

    public static void format_K_threshold(int xsize, int ysize, byte[] orgpixels, boolean[] despixels) {
        int graytotal = 0;
        int k = 0;
        for (int i = 0; i < ysize; i++) {
            for (int j = 0; j < xsize; j++) {
                graytotal += orgpixels[k] & 255;
                k++;
            }
        }
        int grayave = (graytotal / ysize) / xsize;
        int k2 = 0;
        for (int i2 = 0; i2 < ysize; i2++) {
            for (int j2 = 0; j2 < xsize; j2++) {
                if ((orgpixels[k2] & 255) > grayave) {
                    despixels[k2] = false;
                } else {
                    despixels[k2] = true;
                }
                k2++;
            }
        }
    }

    public static void format_K_threshold(Bitmap mBitmap) {
        int graytotal = 0;
        int graycnt = 1;
        int ysize = mBitmap.getHeight();
        int xsize = mBitmap.getWidth();
        for (int i = 0; i < ysize; i++) {
            for (int j = 0; j < xsize; j++) {
                int gray = mBitmap.getPixel(j, i) & 255;
                if (!(gray == 0 || gray == 255)) {
                    graytotal += gray;
                    graycnt++;
                }
            }
        }
        int grayave = graytotal / graycnt;
        for (int i2 = 0; i2 < ysize; i2++) {
            for (int j2 = 0; j2 < xsize; j2++) {
                if ((mBitmap.getPixel(j2, i2) & 255) > grayave) {
                    mBitmap.setPixel(j2, i2, -1);
                } else {
                    mBitmap.setPixel(j2, i2, -16777216);
                }
            }
        }
    }

    public static Bitmap alignBitmap(Bitmap bitmap, int wbits, int hbits, int color) {
        if (bitmap.getWidth() % wbits == 0 && bitmap.getHeight() % hbits == 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[(width * height)];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int newwidth = (((width + wbits) - 1) / wbits) * wbits;
        int newheight = (((height + hbits) - 1) / hbits) * hbits;
        int[] newpixels = new int[(newwidth * newheight)];
        Bitmap newbitmap = Bitmap.createBitmap(newwidth, newheight, Bitmap.Config.ARGB_8888);
        for (int i = 0; i < newheight; i++) {
            for (int j = 0; j < newwidth; j++) {
                if (i >= height || j >= width) {
                    newpixels[(i * newwidth) + j] = color;
                } else {
                    newpixels[(i * newwidth) + j] = pixels[(i * width) + j];
                }
            }
        }
        newbitmap.setPixels(newpixels, 0, newwidth, 0, 0, newwidth, newheight);
        return newbitmap;
    }

    public static Bitmap adjustPhotoRotation(Bitmap bm, int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate((float) orientationDegree, ((float) bm.getWidth()) / 2.0f, ((float) bm.getHeight()) / 2.0f);
        try {
            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private static int PixOffset(int w, int x, int y) {
        return (y * w) + x;
    }

    public static byte[] Image1ToNVData(int width, int height, boolean[] src) {
        int idx;
        int x = (width + 7) / 8;
        int y = (height + 7) / 8;
        byte[] dst = new byte[((x * y * 8) + 4)];
        dst[0] = (byte) x;
        dst[1] = (byte) (x >> 8);
        dst[2] = (byte) y;
        dst[3] = (byte) (y >> 8);
        int idx2 = 4;
        int d = 0;
        int i = 0;
        while (i < width) {
            int j = 0;
            int idx3 = idx2;
            while (j < height) {
                int offset = PixOffset(width, i, j);
                if (j % 8 == 0) {
                    d = (src[offset] ? 1 : 0) << (7 - (j % 8));
                } else {
                    d |= (src[offset] ? 1 : 0) << (7 - (j % 8));
                }
                if (j % 8 == 7 || j == height - 1) {
                    idx = idx3 + 1;
                    dst[idx3] = (byte) d;
                } else {
                    idx = idx3;
                }
                j++;
                idx3 = idx;
            }
            i++;
            idx2 = idx3;
        }
        return dst;
    }

    public static byte[] Image1ToGSCmd(int width, int height, boolean[] src) {
        int idx;
        int x = (width + 7) / 8;
        int y = (height + 7) / 8;
        byte[] dst = new byte[((x * y * 8) + 4)];
        dst[0] = 29;
        dst[1] = 42;
        dst[2] = (byte) x;
        dst[3] = (byte) y;
        int idx2 = 4;
        int d = 0;
        int i = 0;
        while (i < width) {
            int j = 0;
            int idx3 = idx2;
            while (j < height) {
                int offset = PixOffset(width, i, j);
                if (j % 8 == 0) {
                    d = (src[offset] ? 1 : 0) << (7 - (j % 8));
                } else {
                    d |= (src[offset] ? 1 : 0) << (7 - (j % 8));
                }
                if (j % 8 == 7 || j == height - 1) {
                    idx = idx3 + 1;
                    dst[idx3] = (byte) d;
                } else {
                    idx = idx3;
                }
                j++;
                idx3 = idx;
            }
            i++;
            idx2 = idx3;
        }
        return dst;
    }

    public static byte[] Image1ToRasterCmd(int width, int height, boolean[] src) {
        int idx;
        int x = (width + 7) / 8;
        int y = ((height + 7) / 8) * 8;
        byte[] dst = new byte[((x * y) + 8)];
        dst[0] = 29;
        dst[1] = 118;
        dst[2] = 48;
        dst[3] = 0;
        dst[4] = (byte) (x % 256);
        dst[5] = (byte) (x / 256);
        dst[6] = (byte) (y % 256);
        dst[7] = (byte) (y / 256);
        int idx2 = 8;
        int d = 0;
        int j = 0;
        while (j < height) {
            int i = 0;
            int idx3 = idx2;
            while (i < width) {
                int offset = PixOffset(width, i, j);
                if (i % 8 == 0) {
                    d = (src[offset] ? 1 : 0) << (7 - (i % 8));
                } else {
                    d |= (src[offset] ? 1 : 0) << (7 - (i % 8));
                }
                if (i % 8 == 7 || i == width - 1) {
                    idx = idx3 + 1;
                    dst[idx3] = (byte) d;
                } else {
                    idx = idx3;
                }
                i++;
                idx3 = idx;
            }
            j++;
            idx2 = idx3;
        }
        return dst;
    }

    public static byte[] Image1ToRasterData(int width, int height, boolean[] src) {
        int idx;
        byte[] dst = new byte[(((width + 7) / 8) * height)];
        int idx2 = 0;
        int d = 0;
        int j = 0;
        while (j < height) {
            int i = 0;
            int idx3 = idx2;
            while (i < width) {
                int offset = PixOffset(width, i, j);
                if (i % 8 == 0) {
                    d = (src[offset] ? 1 : 0) << (7 - (i % 8));
                } else {
                    d |= (src[offset] ? 1 : 0) << (7 - (i % 8));
                }
                if (i % 8 == 7 || i == width - 1) {
                    idx = idx3 + 1;
                    dst[idx3] = (byte) d;
                } else {
                    idx = idx3;
                }
                i++;
                idx3 = idx;
            }
            j++;
            idx2 = idx3;
        }
        return dst;
    }

    public static byte[] Image1ToTM88IVRasterCmd(int width, int height, boolean[] src) {
        int idx;
        int x = ((width + 7) / 8) * 8;
        int y = ((height + 7) / 8) * 8;
        int dstlen = ((x * y) / 8) + 26;
        byte[] dst = new byte[dstlen];
        dst[0] = 29;
        dst[1] = 56;
        dst[2] = 76;
        dst[3] = (byte) ((((x * y) / 8) + 10) & 255);
        dst[4] = (byte) (((((x * y) / 8) + 10) >> 8) & 255);
        dst[5] = (byte) (((((x * y) / 8) + 10) >> 16) & 255);
        dst[6] = (byte) (((((x * y) / 8) + 10) >> 24) & 255);
        dst[7] = 48;
        dst[8] = 112;
        dst[9] = 48;
        dst[10] = 1;
        dst[11] = 1;
        dst[12] = 49;
        dst[13] = (byte) (x % 256);
        dst[14] = (byte) (x / 256);
        dst[15] = (byte) (y % 256);
        dst[16] = (byte) (y / 256);
        byte[] cmdPrint = {29, 56, 76, 2, 0, 0, 0, 48, 2};
        System.arraycopy(cmdPrint, 0, dst, dstlen - cmdPrint.length, cmdPrint.length);
        int idx2 = 17;
        int d = 0;
        int j = 0;
        while (j < height) {
            int i = 0;
            int idx3 = idx2;
            while (i < width) {
                int offset = PixOffset(width, i, j);
                if (i % 8 == 0) {
                    d = (byte) ((src[offset] ? 1 : 0) << (7 - (i % 8)));
                } else {
                    d |= (byte) ((src[offset] ? 1 : 0) << (7 - (i % 8)));
                }
                if (i % 8 == 7 || i == width - 1) {
                    idx = idx3 + 1;
                    dst[idx3] = (byte) d;
                } else {
                    idx = idx3;
                }
                i++;
                idx3 = idx;
            }
            j++;
            idx2 = idx3;
        }
        return dst;
    }

    public static byte[] eachLinePixToCmd(boolean[] src, int nWidth, int nMode) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int nHeight = src.length / nWidth;
        int nBytesPerLine = nWidth / 8;
        byte[] data = new byte[((nBytesPerLine + 8) * nHeight)];
        int k = 0;
        for (int i9 = 0; i9 < nHeight; i9++) {
            int offset = i9 * (nBytesPerLine + 8);
            data[offset + 0] = 29;
            data[offset + 1] = 118;
            data[offset + 2] = 48;
            data[offset + 3] = (byte) (nMode & 1);
            data[offset + 4] = (byte) (nBytesPerLine % 256);
            data[offset + 5] = (byte) (nBytesPerLine / 256);
            data[offset + 6] = 1;
            data[offset + 7] = 0;
            for (int j = 0; j < nBytesPerLine; j++) {
                int i10 = offset + 8 + j;
                if (src[k]) {
                    i = 128;
                } else {
                    i = 0;
                }
                if (src[k + 1]) {
                    i2 = 64;
                } else {
                    i2 = 0;
                }
                int i11 = i2 | i;
                if (src[k + 2]) {
                    i3 = 32;
                } else {
                    i3 = 0;
                }
                int i12 = i11 | i3;
                if (src[k + 3]) {
                    i4 = 16;
                } else {
                    i4 = 0;
                }
                int i13 = i12 | i4;
                if (src[k + 4]) {
                    i5 = 8;
                } else {
                    i5 = 0;
                }
                int i14 = i13 | i5;
                if (src[k + 5]) {
                    i6 = 4;
                } else {
                    i6 = 0;
                }
                int i15 = i14 | i6;
                if (src[k + 6]) {
                    i7 = 2;
                } else {
                    i7 = 0;
                }
                int i16 = i15 | i7;
                if (src[k + 7]) {
                    i8 = 1;
                } else {
                    i8 = 0;
                }
                data[i10] = (byte) (i8 | i16);
                k += 8;
            }
        }
        return data;
    }

    public static byte[] eachLinePixToCompressCmd(boolean[] src, int nWidth) {
        int nHeight = src.length / nWidth;
        int nBytesPerLine = nWidth / 8;
        byte[] data = new byte[(nHeight * nBytesPerLine)];
        int k = 0;
        for (int i = 0; i < nHeight; i++) {
            for (int j = 0; j < nBytesPerLine; j++) {
                data[(i * nBytesPerLine) + j] = (byte) ((src[k + 7] ? 1 : 0) | (src[k + 1] ? 64 : 0) | (src[k] ? 128 : 0) | (src[k + 2] ? 32 : 0) | (src[k + 3] ? 16 : 0) | (src[k + 4] ? 8 : 0) | (src[k + 5] ? 4 : 0) | (src[k + 6] ? 2 : 0));
                k += 8;
            }
        }
        int compresseddatalen = 0;
        for (int y = 0; y < nHeight; y++) {
            byte[] line = new byte[nBytesPerLine];
            System.arraycopy(data, y * nBytesPerLine, line, 0, nBytesPerLine);
            byte[] buf = CompressDataBuf(line);
            compresseddatalen = compresseddatalen + new byte[]{31, 40, 80, (byte) ((int) (((long) buf.length) & 255)), (byte) ((int) ((((long) buf.length) & 65535) >> 8))}.length + buf.length;
        }
        byte[] compresseddatabytes = new byte[compresseddatalen];
        int offset = 0;
        for (int y2 = 0; y2 < nHeight; y2++) {
            byte[] line2 = new byte[nBytesPerLine];
            System.arraycopy(data, y2 * nBytesPerLine, line2, 0, nBytesPerLine);
            byte[] buf2 = CompressDataBuf(line2);
            byte[] cmd = {31, 40, 80, (byte) ((int) (((long) buf2.length) & 255)), (byte) ((int) ((((long) buf2.length) & 65535) >> 8))};
            System.arraycopy(cmd, 0, compresseddatabytes, offset, cmd.length);
            int offset2 = offset + cmd.length;
            System.arraycopy(buf2, 0, compresseddatabytes, offset2, buf2.length);
            offset = offset2 + buf2.length;
        }
        return compresseddatabytes;
    }

    public static byte[] CompressDataBuf(byte[] src) {
        int srclen = src.length;
        byte[] buf = new byte[(srclen * 2)];
        byte ch = src[0];
        buf[0] = ch;
        int cnt = 1;
        int idx = 1;
        int i = 1;
        while (true) {
            if (i >= srclen) {
                break;
            }
            while (src[i] == ch) {
                i++;
                cnt++;
                if (i >= srclen) {
                    break;
                }
            }
            if (i >= srclen) {
                buf[idx] = (byte) cnt;
                idx++;
                break;
            }
            buf[idx] = (byte) cnt;
            ch = src[i];
            buf[idx + 1] = ch;
            cnt = 1;
            idx += 2;
            i++;
        }
        if ((idx & 1) != 0) {
            buf[idx] = (byte) cnt;
            idx++;
        }
        if (idx >= srclen) {
            byte[] dst = new byte[(srclen + 1)];
            dst[0] = 0;
            System.arraycopy(src, 0, dst, 1, srclen);
            return dst;
        }
        byte[] dst2 = new byte[(idx + 1)];
        dst2[0] = (byte) idx;
        System.arraycopy(buf, 0, dst2, 1, idx);
        return dst2;
    }

    static class TARGB32 {
        public int a;
        public int b;
        public int g;
        public int r;

        public TARGB32(int argb) {
            this.a = (int) ((((long) argb) & 4294967295L) >> 24);
            this.r = (int) ((((long) argb) & 4294967295L) >> 16);
            this.g = (int) ((((long) argb) & 4294967295L) >> 8);
            this.b = (int) ((((long) argb) & 4294967295L) >> 0);
        }

        public int IntValue() {
            return (int) (((((long) this.a) & 255) << 24) | ((((long) this.r) & 255) << 16) | ((((long) this.g) & 255) << 8) | ((((long) this.b) & 255) << 0));
        }
    }

    static class TPicRegion {
        public int height;
        public int[] pdata;
        public int width;

        TPicRegion() {
        }
    }

    public static void PicZoom_ThreeOrder0(int srcw, int srch, int[] src, int dstw, int dsth, int[] dst) {
        int k;
        if (dstw != 0 && dsth != 0 && srcw != 0 && srch != 0) {
            if (srcw == dstw && srch == dsth) {
                System.arraycopy(src, 0, dst, 0, src.length);
                return;
            }
            int k2 = 0;
            int y = 0;
            while (y < dsth) {
                double srcy = (((((double) y) + 0.4999999d) * ((double) srch)) / ((double) dsth)) - 0.5d;
                int x = 0;
                while (true) {
                    k = k2;
                    if (x >= dstw) {
                        break;
                    }
                    k2 = k + 1;
                    dst[k] = ThreeOrder0(srcw, srch, src, (((((double) x) + 0.4999999d) * ((double) srcw)) / ((double) dstw)) - 0.5d, srcy);
                    x++;
                }
                y++;
                k2 = k;
            }
        }
    }

    static int ThreeOrder0(int srcw, int srch, int[] src, double fx, double fy) {
        int x0 = (int) fx;
        if (((double) x0) > fx) {
            x0--;
        }
        int y0 = (int) fy;
        if (((double) y0) > fy) {
            y0--;
        }
        double fu = fx - ((double) x0);
        double fv = fy - ((double) y0);
        TARGB32[] pixel = new TARGB32[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                pixel[(i * 4) + j] = Pixels_Bound(srcw, srch, src, (long) ((x0 - 1) + j), (long) ((y0 - 1) + i));
            }
        }
        double[] afu = {SinXDivX(1.0d + fu), SinXDivX(fu), SinXDivX(1.0d - fu), SinXDivX(2.0d - fu)};
        double[] afv = {SinXDivX(1.0d + fv), SinXDivX(fv), SinXDivX(1.0d - fv), SinXDivX(2.0d - fv)};
        float sR = 0.0f;
        float sG = 0.0f;
        float sB = 0.0f;
        float sA = 0.0f;
        for (int i2 = 0; i2 < 4; i2++) {
            float aR = 0.0f;
            float aG = 0.0f;
            float aB = 0.0f;
            float aA = 0.0f;
            for (int j2 = 0; j2 < 4; j2++) {
                aA = (float) (((double) aA) + (afu[j2] * ((double) pixel[(i2 * 4) + j2].a)));
                aR = (float) (((double) aR) + (afu[j2] * ((double) pixel[(i2 * 4) + j2].r)));
                aG = (float) (((double) aG) + (afu[j2] * ((double) pixel[(i2 * 4) + j2].g)));
                aB = (float) (((double) aB) + (afu[j2] * ((double) pixel[(i2 * 4) + j2].b)));
            }
            sA = (float) (((double) sA) + (((double) aA) * afv[i2]));
            sR = (float) (((double) sR) + (((double) aR) * afv[i2]));
            sG = (float) (((double) sG) + (((double) aG) * afv[i2]));
            sB = (float) (((double) sB) + (((double) aB) * afv[i2]));
        }
        return (int) (((((long) ((byte) ((int) border_color((long) (((double) sA) + 0.5d))))) & 255) << 24) | ((((long) ((byte) ((int) border_color((long) (((double) sR) + 0.5d))))) & 255) << 16) | ((((long) ((byte) ((int) border_color((long) (((double) sG) + 0.5d))))) & 255) << 8) | ((((long) ((byte) ((int) border_color((long) (((double) sB) + 0.5d))))) & 255) << 0));
    }

    static TARGB32 Pixels_Bound(int srcw, int srch, int[] src, long x, long y) {
        boolean IsInPic = true;
        if (x < 0) {
            x = 0;
            IsInPic = false;
        } else if (x >= ((long) srcw)) {
            x = (long) (srcw - 1);
            IsInPic = false;
        }
        if (y < 0) {
            y = 0;
            IsInPic = false;
        } else if (y >= ((long) srch)) {
            y = (long) (srch - 1);
            IsInPic = false;
        }
        TARGB32 result = Pixels(srcw, srch, src, x, y);
        if (!IsInPic) {
            result.a = 0;
        }
        return result;
    }

    static TARGB32 Pixels(int srcw, int srch, int[] src, long x, long y) {
        return new TARGB32(src[(int) ((((long) srcw) * y) + x)]);
    }

    static double SinXDivX(double x) {
        if (x < 0.0d) {
            x = -x;
        }
        double x2 = x * x;
        double x3 = x2 * x;
        if (x <= 1.0d) {
            return ((1.0d * x3) - (2.0d * x2)) + 1.0d;
        }
        if (x <= 2.0d) {
            return (((-1.0d * x3) - (-5.0d * x2)) + (-8.0d * x)) - -4.0d;
        }
        return 0.0d;
    }

    static long border_color(long Color) {
        if (Color <= 0) {
            return 0;
        }
        if (Color >= 255) {
            return 255;
        }
        return Color;
    }

    public static byte[] GrayImage(int[] src) {
        int srclen = src.length;
        byte[] dst = new byte[srclen];
        for (int k = 0; k < srclen; k++) {
            dst[k] = (byte) ((int) ((((((((long) src[k]) & 16711680) >> 16) * 19595) + (((((long) src[k]) & 65280) >> 8) * 38469)) + (((((long) src[k]) & 255) >> 0) * 7472)) >> 16));
        }
        return dst;
    }

    public static void ReverseBitmap(int srcw, int srch, int[] src) {
        int srclen = src.length;
        for (int i = 0; i < srclen; i++) {
            src[i] = (int) (4278190080L | ((((long) src[i]) & 16777215) ^ -1));
        }
    }
}
