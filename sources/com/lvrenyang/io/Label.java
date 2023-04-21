package com.lvrenyang.io;

import android.graphics.Bitmap;
import cn.com.bioeasy.healty.app.healthapp.ble.BLEServiceApi;
import com.lvrenyang.io.base.IO;

public class Label {
    public IO IO = new IO();

    public void Set(IO io) {
        if (io != null) {
            this.IO = io;
        }
    }

    public IO GetIO() {
        return this.IO;
    }

    public boolean PageBegin(int startx, int starty, int width, int height, int rotate) {
        byte[] data = {26, 91, 1, (byte) (startx & 255), (byte) ((startx >> 8) & 255), (byte) (starty & 255), (byte) ((starty >> 8) & 255), (byte) (width & 255), (byte) ((width >> 8) & 255), (byte) (height & 255), (byte) ((height >> 8) & 255), (byte) (rotate & 255)};
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean PageEnd() {
        byte[] data = {26, 93, 0};
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean PagePrint(int num) {
        byte[] data = {26, 79, 1, 1};
        data[3] = (byte) (num & 255);
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean PageFeed() {
        byte[] data = {26, 12, 0};
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean DrawPlainText(int startx, int starty, int font, int style, byte[] str) {
        int datalen = str.length + 11 + 1;
        byte[] data = new byte[datalen];
        data[0] = 26;
        data[1] = 84;
        data[2] = 1;
        data[3] = (byte) (startx & 255);
        data[4] = (byte) ((startx >> 8) & 255);
        data[5] = (byte) (starty & 255);
        data[6] = (byte) ((starty >> 8) & 255);
        data[7] = (byte) (font & 255);
        data[8] = (byte) ((font >> 8) & 255);
        data[9] = (byte) (style & 255);
        data[10] = (byte) ((style >> 8) & 255);
        System.arraycopy(str, 0, data, 11, str.length);
        data[datalen - 1] = 0;
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean DrawLine(int startx, int starty, int endx, int endy, int width, int color) {
        byte[] data = {26, 92, 1, (byte) (startx & 255), (byte) ((startx >> 8) & 255), (byte) (starty & 255), (byte) ((starty >> 8) & 255), (byte) (endx & 255), (byte) ((endx >> 8) & 255), (byte) (endy & 255), (byte) ((endy >> 8) & 255), (byte) (width & 255), (byte) ((width >> 8) & 255), (byte) (color & 255)};
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean DrawBox(int left, int top, int right, int bottom, int borderwidth, int bordercolor) {
        byte[] data = {26, BLEServiceApi.POWER_STATUS_CMD, 1, (byte) (left & 255), (byte) ((left >> 8) & 255), (byte) (top & 255), (byte) ((top >> 8) & 255), (byte) (right & 255), (byte) ((right >> 8) & 255), (byte) (bottom & 255), (byte) ((bottom >> 8) & 255), (byte) (borderwidth & 255), (byte) ((borderwidth >> 8) & 255), (byte) (bordercolor & 255)};
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean DrawRectangel(int left, int top, int right, int bottom, int color) {
        byte[] data = {26, 42, 0, (byte) (left & 255), (byte) ((left >> 8) & 255), (byte) (top & 255), (byte) ((top >> 8) & 255), (byte) (right & 255), (byte) ((right >> 8) & 255), (byte) (bottom & 255), (byte) ((bottom >> 8) & 255), (byte) (color & 255)};
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean DrawBarcode(int startx, int starty, int type, int height, int unitwidth, int rotate, byte[] str) {
        int datalen = str.length + 11 + 1;
        byte[] data = new byte[datalen];
        data[0] = 26;
        data[1] = 48;
        data[2] = 0;
        data[3] = (byte) (startx & 255);
        data[4] = (byte) ((startx >> 8) & 255);
        data[5] = (byte) (starty & 255);
        data[6] = (byte) ((starty >> 8) & 255);
        data[7] = (byte) (type & 255);
        data[8] = (byte) (height & 255);
        data[9] = (byte) (unitwidth & 255);
        data[10] = (byte) (rotate & 255);
        System.arraycopy(str, 0, data, 11, str.length);
        data[datalen - 1] = 0;
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean DrawQRCode(int startx, int starty, int version, int ecc, int unitwidth, int rotate, byte[] str) {
        int datalen = str.length + 11 + 1;
        byte[] data = new byte[datalen];
        data[0] = 26;
        data[1] = 49;
        data[2] = 0;
        data[3] = (byte) (version & 255);
        data[4] = (byte) (ecc & 255);
        data[5] = (byte) (startx & 255);
        data[6] = (byte) ((startx >> 8) & 255);
        data[7] = (byte) (starty & 255);
        data[8] = (byte) ((starty >> 8) & 255);
        data[9] = (byte) (unitwidth & 255);
        data[10] = (byte) (rotate & 255);
        System.arraycopy(str, 0, data, 11, str.length);
        data[datalen - 1] = 0;
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean DrawPDF417(int startx, int starty, int colnum, int lwratio, int ecc, int unitwidth, int rotate, byte[] str) {
        int datalen = str.length + 12 + 1;
        byte[] data = new byte[datalen];
        data[0] = 26;
        data[1] = 49;
        data[2] = 1;
        data[3] = (byte) (colnum & 255);
        data[4] = (byte) (ecc & 255);
        data[5] = (byte) (lwratio & 255);
        data[6] = (byte) (startx & 255);
        data[7] = (byte) ((startx >> 8) & 255);
        data[8] = (byte) (starty & 255);
        data[9] = (byte) ((starty >> 8) & 255);
        data[10] = (byte) (unitwidth & 255);
        data[11] = (byte) (rotate & 255);
        System.arraycopy(str, 0, data, 12, str.length);
        data[datalen - 1] = 0;
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean DrawBitmap(int startx, int starty, int width, int height, int style, byte[] pdata) {
        byte[] data = new byte[(((width * height) / 8) + 13)];
        data[0] = 26;
        data[1] = BLEServiceApi.LED_CMD;
        data[2] = 1;
        data[3] = (byte) (startx & 255);
        data[4] = (byte) ((startx >> 8) & 255);
        data[5] = (byte) (starty & 255);
        data[6] = (byte) ((starty >> 8) & 255);
        data[7] = (byte) (width & 255);
        data[8] = (byte) ((width >> 8) & 255);
        data[9] = (byte) (height & 255);
        data[10] = (byte) ((height >> 8) & 255);
        data[11] = (byte) (style & 255);
        data[12] = (byte) ((style >> 8) & 255);
        System.arraycopy(pdata, 0, data, 13, pdata.length);
        if (this.IO.Write(data, 0, data.length) == data.length) {
            return true;
        }
        return false;
    }

    public boolean DrawBitmap(int startx, int starty, int dstWidth, int dstHeight, int style, Bitmap mBitmap, int nBinaryAlgorithm) {
        int dstw = ((dstWidth + 7) / 8) * 8;
        int dsth = dstHeight;
        int[] dst = new int[(dstw * dsth)];
        ImageProcessing.resizeImage(mBitmap, dstw, dsth).getPixels(dst, 0, dstw, 0, 0, dstw, dsth);
        byte[] gray = ImageProcessing.GrayImage(dst);
        boolean[] dithered = new boolean[(dstw * dsth)];
        if (nBinaryAlgorithm == 0) {
            ImageProcessing.format_K_dither16x16(dstw, dsth, gray, dithered);
        } else {
            ImageProcessing.format_K_threshold(dstw, dsth, gray, dithered);
        }
        return DrawBitmap(startx, starty, dstw, dsth, style, ImageProcessing.Image1ToRasterData(dstw, dsth, dithered));
    }
}
