package com.lvrenyang.io;

import android.graphics.Bitmap;
import android.util.Log;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.lvrenyang.io.base.IO;
import com.lvrenyang.qrcode.QRCode;

public class Page {
    public static final int BARCODE_TYPE_CODE128 = 73;
    public static final int BARCODE_TYPE_CODE39 = 69;
    public static final int BARCODE_TYPE_CODE93 = 72;
    public static final int BARCODE_TYPE_CODEBAR = 71;
    public static final int BARCODE_TYPE_EAN13 = 67;
    public static final int BARCODE_TYPE_EAN8 = 68;
    public static final int BARCODE_TYPE_ITF = 70;
    public static final int BARCODE_TYPE_UPCA = 65;
    public static final int BARCODE_TYPE_UPCE = 66;
    public static final int BINARYALGORITHM_DITHER = 0;
    public static final int BINARYALGORITHM_THRESHOLD = 1;
    public static final int DIRECTION_BOTTOM_TO_TOP = 1;
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 2;
    public static final int DIRECTION_TOP_TO_BOTTOM = 3;
    public static final int FONTSTYLE_BOLD = 8;
    public static final int FONTSTYLE_HIGHLIGHT = 1024;
    public static final int FONTSTYLE_REVERSE = 512;
    public static final int FONTSTYLE_UNDERLINE = 256;
    public static final int FONTTYPE_SMALL = 1;
    public static final int FONTTYPE_STANDARD = 0;
    public static final int HORIZONTALALIGNMENT_CENTER = -2;
    public static final int HORIZONTALALIGNMENT_LEFT = -1;
    public static final int HORIZONTALALIGNMENT_RIGHT = -3;
    public static final int HRI_FONTPOSITION_ABOVE = 1;
    public static final int HRI_FONTPOSITION_ABOVE_AND_BELOW = 3;
    public static final int HRI_FONTPOSITION_BELOW = 2;
    public static final int HRI_FONTPOSITION_NONE = 0;
    public static final int HRI_FONTTYPE_SMALL = 1;
    public static final int HRI_FONTTYPE_STANDARD = 0;
    public static final int LINEHEIGHT_DEFAULT = 32;
    private static final String TAG = "Page";
    private IO IO = new IO();
    private int b;
    int baseline = 21;
    private int dir;
    private int l;
    int lineheight = 32;
    private int r;
    int rightspacing = 0;
    private int t;

    public void Set(IO io) {
        if (io != null) {
            this.IO = io;
        }
    }

    public IO GetIO() {
        return this.IO;
    }

    public boolean PageEnter() {
        boolean result = false;
        if (this.IO.IsOpened()) {
            result = false;
            this.IO.mMainLocker.lock();
            try {
                byte[] buf = {27, 64, 29, 80, -56, -56, 27, 76, 27, 51, (byte) this.lineheight};
                int len = buf.length;
                if (this.IO.Write(buf, 0, len) == len) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
            } finally {
                this.IO.mMainLocker.unlock();
            }
        }
        return result;
    }

    public boolean PagePrint() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] buf = {27, 12};
            int len = buf.length;
            if (this.IO.Write(buf, 0, len) == len) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return result;
    }

    public boolean PageExit() {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] buf = {27, 83};
            int len = buf.length;
            if (this.IO.Write(buf, 0, len) == len) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return result;
    }

    public boolean SetPrintArea(int left, int top, int right, int bottom, int direction) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            byte[] buf = {27, 87, 0, 0, 0, 0, 0, 0, 0, 0, 27, 84, 0};
            int len = buf.length;
            buf[2] = (byte) left;
            buf[3] = (byte) (left >> 8);
            buf[4] = (byte) top;
            buf[5] = (byte) (top >> 8);
            buf[6] = (byte) (right - left);
            buf[7] = (byte) ((right - left) >> 8);
            buf[8] = (byte) (bottom - top);
            buf[9] = (byte) ((bottom - top) >> 8);
            buf[12] = (byte) direction;
            this.l = left;
            this.t = top;
            this.r = right;
            this.b = bottom;
            this.dir = direction;
            if (this.IO.Write(buf, 0, len) == len) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return result;
    }

    public boolean SetLineHeight(int nLineHeight) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            this.lineheight = nLineHeight;
            byte[] buf = {27, 51, (byte) this.lineheight};
            int len = buf.length;
            if (this.IO.Write(buf, 0, len) == len) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return result;
    }

    public boolean SetCharacterRightSpacing(int nRightSpacing) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        boolean result = false;
        this.IO.mMainLocker.lock();
        try {
            this.rightspacing = nRightSpacing;
            byte[] buf = {27, 32, (byte) this.rightspacing, 28, 83, 0, (byte) this.rightspacing};
            int len = buf.length;
            if (this.IO.Write(buf, 0, len) == len) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
        } finally {
            this.IO.mMainLocker.unlock();
        }
        return result;
    }

    private int ComputeStringWidth(String pszString, int nEngCharWidth, int nChnCharWidth) {
        int i;
        int nWidth = 0;
        int i2 = 0;
        while (i2 < pszString.length() && pszString.charAt(i2) >= ' ') {
            if (pszString.charAt(i2) < 256) {
                i = this.rightspacing + nEngCharWidth;
            } else {
                i = this.rightspacing + nChnCharWidth;
            }
            nWidth += i;
            i2++;
        }
        return nWidth;
    }

    public boolean DrawText(String pszString, int x, int y, int nWidthScale, int nHeightScale, int nFontType, int nFontStyle) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        if (x == -1) {
            x = 0;
        } else if (x == -2) {
            int nWidth = ComputeStringWidth(pszString, (nWidthScale + 1) * 12, (nWidthScale + 1) * 24);
            if (this.dir == 0 || this.dir == 2) {
                x = ((this.r - this.l) - nWidth) / 2;
            } else if (this.dir == 1 || this.dir == 3) {
                x = ((this.b - this.t) - nWidth) / 2;
            }
        } else if (x == -3) {
            int nWidth2 = ComputeStringWidth(pszString, (nWidthScale + 1) * 12, (nWidthScale + 1) * 24);
            if (this.dir == 0 || this.dir == 2) {
                x = (this.r - this.l) - nWidth2;
            } else if (this.dir == 1 || this.dir == 3) {
                x = (this.b - this.t) - nWidth2;
            }
        }
        if (y >= 0) {
            try {
                y += ((nHeightScale + 1) * 24) - this.baseline;
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
                return false;
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        }
        byte[] bufx = {27, 36, (byte) x, (byte) (x >> 8)};
        byte[] bufy = {29, 36, (byte) y, (byte) (y >> 8)};
        byte[] buffont = {27, BLEServiceApi.LED_CMD, (byte) nFontType};
        byte[] bufscale = {29, BLEServiceApi.LED_CMD, (byte) ((nWidthScale << 4) | nHeightScale)};
        byte[] bufbold = new byte[3];
        bufbold[0] = 27;
        bufbold[1] = 69;
        bufbold[2] = (byte) ((nFontStyle & 8) == 8 ? 1 : 0);
        byte[] bufunderline = new byte[3];
        bufunderline[0] = 27;
        bufunderline[1] = 45;
        bufunderline[2] = (byte) ((nFontStyle & 256) == 256 ? 2 : 0);
        byte[] bufreverse = new byte[3];
        bufreverse[0] = 27;
        bufreverse[1] = 123;
        bufreverse[2] = (byte) ((nFontStyle & 512) == 512 ? 1 : 0);
        byte[] bufhighlight = new byte[3];
        bufhighlight[0] = 29;
        bufhighlight[1] = 66;
        bufhighlight[2] = (byte) ((nFontStyle & 1024) == 1024 ? 1 : 0);
        byte[] buf = byteArraysToBytes(new byte[][]{bufx, bufy, buffont, bufscale, bufbold, bufunderline, bufreverse, bufhighlight, new byte[]{28, BLEServiceApi.POWER_STATUS_CMD, 27, 57, 1}, pszString.getBytes()});
        int len = buf.length;
        boolean result = this.IO.Write(buf, 0, len) == len;
        this.IO.mMainLocker.unlock();
        return result;
    }

    private int ComputeUPCAWidth(String pszString, int nBarcodeUnitWidth) {
        return 113 * nBarcodeUnitWidth;
    }

    private int ComputeUPCEWidth(String pszString, int nBarcodeUnitWidth) {
        return 51 * nBarcodeUnitWidth;
    }

    private int ComputeEAN13Width(String pszString, int nBarcodeUnitWidth) {
        return ((new int[]{0}.length * 2) + (new int[]{1, 0, 1}.length * 2) + new int[]{0, 1, 0, 1, 0}.length + 84) * nBarcodeUnitWidth;
    }

    private int ComputeEAN8Width(String pszString, int nBarcodeUnitWidth) {
        return 67 * nBarcodeUnitWidth;
    }

    public boolean DrawBarcode(String pszString, int x, int y, int nBarcodeUnitWidth, int nBarcodeHeight, int nHriFontType, int nHriFontPosition, int nBarcodeType) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        if (y >= 0) {
            try {
                y += (((nHriFontType == 0 ? 24 : 17) * ((int) Math.ceil(((double) (nHriFontPosition & 3)) / 2.0d))) + nBarcodeHeight) - this.baseline;
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
                return false;
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        }
        byte[] head = {29, 119, 2, 29, 104, -94, 29, 102, 0, 29, 72, 2, 29, 107, 0, 0};
        byte[] szContent = pszString.getBytes();
        head[2] = (byte) nBarcodeUnitWidth;
        head[5] = (byte) nBarcodeHeight;
        head[8] = (byte) (nHriFontType & 1);
        head[11] = (byte) (nHriFontPosition & 3);
        head[14] = (byte) nBarcodeType;
        head[15] = (byte) szContent.length;
        byte[] buf = byteArraysToBytes(new byte[][]{new byte[]{27, 36, (byte) x, (byte) (x >> 8)}, new byte[]{29, 36, (byte) y, (byte) (y >> 8)}, head, szContent});
        int len = buf.length;
        boolean result = this.IO.Write(buf, 0, len) == len;
        this.IO.mMainLocker.unlock();
        return result;
    }

    private int ComputeQRCodeWidth(String pszContent, int nQRCodeUnitWidth, int nVersion, int nEcLevel) {
        int w = 0;
        int typeNumber = QRCode.getMinimumQRCodeTypeNumber(pszContent, nEcLevel - 1);
        if (nVersion < typeNumber) {
            nVersion = typeNumber;
        }
        QRCode codes = QRCode.getFixedSizeQRCode(pszContent, nEcLevel - 1, nVersion);
        if (codes != null) {
            w = codes.getModules().length;
        }
        return w * nQRCodeUnitWidth;
    }

    public boolean DrawQRCode(String pszString, int x, int y, int nQRCodeUnitWidth, int nVersion, int nEcLevel) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        if (x == -1) {
            x = 0;
        } else if (x == -2) {
            int nWidth = ComputeQRCodeWidth(pszString, nQRCodeUnitWidth, nVersion, nEcLevel);
            if (this.dir == 0 || this.dir == 2) {
                x = ((this.r - this.l) - nWidth) / 2;
            } else if (this.dir == 1 || this.dir == 3) {
                x = ((this.b - this.t) - nWidth) / 2;
            }
        } else if (x == -3) {
            int nWidth2 = ComputeQRCodeWidth(pszString, nQRCodeUnitWidth, nVersion, nEcLevel);
            if (this.dir == 0 || this.dir == 2) {
                x = (this.r - this.l) - nWidth2;
            } else if (this.dir == 1 || this.dir == 3) {
                x = (this.b - this.t) - nWidth2;
            }
        }
        if (y >= 0) {
            try {
                y += this.lineheight;
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
                return false;
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        }
        byte[] head = {29, 119, 2, 29, 107, 97, 10, 1, 0, 0};
        byte[] szContent = pszString.getBytes();
        head[2] = (byte) nQRCodeUnitWidth;
        head[6] = (byte) nVersion;
        head[7] = (byte) nEcLevel;
        head[8] = (byte) szContent.length;
        head[9] = (byte) (szContent.length >> 8);
        byte[] buf = byteArraysToBytes(new byte[][]{new byte[]{27, 36, (byte) x, (byte) (x >> 8)}, new byte[]{29, 36, (byte) y, (byte) (y >> 8)}, head, szContent});
        int len = buf.length;
        boolean result = this.IO.Write(buf, 0, len) == len;
        this.IO.mMainLocker.unlock();
        return result;
    }

    public boolean DrawBitmap(Bitmap mBitmap, int x, int y, int dwWidth, int dwHeight, int nBinaryAlgorithm) {
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        if (x == -1) {
            x = 0;
        } else if (x == -2) {
            int nWidth = dwWidth;
            if (this.dir == 0 || this.dir == 2) {
                x = ((this.r - this.l) - nWidth) / 2;
            } else if (this.dir == 1 || this.dir == 3) {
                x = ((this.b - this.t) - nWidth) / 2;
            }
        } else if (x == -3) {
            int nWidth2 = dwWidth;
            if (this.dir == 0 || this.dir == 2) {
                x = (this.r - this.l) - nWidth2;
            } else if (this.dir == 1 || this.dir == 3) {
                x = (this.b - this.t) - nWidth2;
            }
        }
        if (y >= 0) {
            try {
                y += dwHeight - this.baseline;
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                this.IO.mMainLocker.unlock();
                return false;
            } catch (Throwable th) {
                this.IO.mMainLocker.unlock();
                throw th;
            }
        }
        byte[] bufx = {27, 36, (byte) x, (byte) (x >> 8)};
        byte[] bufy = {29, 36, (byte) y, (byte) (y >> 8)};
        int dstw = dwWidth;
        int dsth = dwHeight;
        int[] dst = new int[(dstw * dsth)];
        ImageProcessing.resizeImage(mBitmap, dstw, dsth).getPixels(dst, 0, dstw, 0, 0, dstw, dsth);
        byte[] gray = ImageProcessing.GrayImage(dst);
        boolean[] dithered = new boolean[(dstw * dsth)];
        if (nBinaryAlgorithm == 0) {
            ImageProcessing.format_K_dither16x16(dstw, dsth, gray, dithered);
        } else {
            ImageProcessing.format_K_threshold(dstw, dsth, gray, dithered);
        }
        byte[] buf = byteArraysToBytes(new byte[][]{bufx, bufy, ImageProcessing.Image1ToTM88IVRasterCmd(dstw, dsth, dithered)});
        int len = buf.length;
        boolean result = this.IO.Write(buf, 0, len) == len;
        this.IO.mMainLocker.unlock();
        return result;
    }

    private byte[] byteArraysToBytes(byte[][] data) {
        int length = 0;
        for (byte[] length2 : data) {
            length += length2.length;
        }
        byte[] send = new byte[length];
        int k = 0;
        for (int i = 0; i < data.length; i++) {
            int j = 0;
            while (j < data[i].length) {
                send[k] = data[i][j];
                j++;
                k++;
            }
        }
        return send;
    }
}
