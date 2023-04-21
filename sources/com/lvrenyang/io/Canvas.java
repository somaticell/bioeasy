package com.lvrenyang.io;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import com.lvrenyang.barcode.DSCode128;
import com.lvrenyang.io.base.IO;
import com.lvrenyang.qrcode.QRCode;

public class Canvas {
    public static final int BARCODE_TYPE_CODE128 = 73;
    public static final int DIRECTION_BOTTOM_TO_TOP = 1;
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 2;
    public static final int DIRECTION_TOP_TO_BOTTOM = 3;
    public static final int FONTSTYLE_BOLD = 8;
    public static final int FONTSTYLE_UNDERLINE = 128;
    public static final int HORIZONTALALIGNMENT_CENTER = -2;
    public static final int HORIZONTALALIGNMENT_LEFT = -1;
    public static final int HORIZONTALALIGNMENT_RIGHT = -3;
    private static final String TAG = "Canvas";
    public static final int VERTICALALIGNMENT_BOTTOM = -3;
    public static final int VERTICALALIGNMENT_CENTER = -2;
    public static final int VERTICALALIGNMENT_TOP = -1;
    public IO IO = new IO();
    private Bitmap bitmap;
    private android.graphics.Canvas canvas;
    private int dir;
    private Paint paint;
    private TextPaint textpaint;

    public void Set(IO io) {
        if (io != null) {
            this.IO = io;
        }
    }

    public IO GetIO() {
        return this.IO;
    }

    public void CanvasBegin(int width, int height) {
        this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.canvas = new android.graphics.Canvas(this.bitmap);
        this.paint = new Paint();
        this.textpaint = new TextPaint();
        this.dir = 0;
        this.paint.setColor(-1);
        this.canvas.drawRect(0.0f, 0.0f, (float) width, (float) height, this.paint);
        this.paint.setColor(-16777216);
        this.textpaint.setColor(-16777216);
    }

    public void CanvasEnd() {
        this.textpaint = null;
        this.paint = null;
        this.canvas = null;
    }

    public boolean CanvasPrint(int nBinaryAlgorithm, int nCompressMethod) {
        byte[] data;
        if (!this.IO.IsOpened()) {
            return false;
        }
        this.IO.mMainLocker.lock();
        try {
            Bitmap mBitmap = this.bitmap;
            int dstw = ((this.bitmap.getWidth() + 7) / 8) * 8;
            int dsth = (mBitmap.getHeight() * dstw) / mBitmap.getWidth();
            int[] dst = new int[(dstw * dsth)];
            ImageProcessing.resizeImage(mBitmap, dstw, dsth).getPixels(dst, 0, dstw, 0, 0, dstw, dsth);
            byte[] gray = ImageProcessing.GrayImage(dst);
            boolean[] dithered = new boolean[(dstw * dsth)];
            if (nBinaryAlgorithm == 0) {
                ImageProcessing.format_K_dither16x16(dstw, dsth, gray, dithered);
            } else {
                ImageProcessing.format_K_threshold(dstw, dsth, gray, dithered);
            }
            if (nCompressMethod == 0) {
                data = ImageProcessing.eachLinePixToCmd(dithered, dstw, 0);
            } else {
                data = ImageProcessing.eachLinePixToCompressCmd(dithered, dstw);
            }
            return this.IO.Write(data, 0, data.length) == data.length;
        } catch (Exception ex) {
            Log.i(TAG, ex.toString());
            return false;
        } finally {
            this.IO.mMainLocker.unlock();
        }
    }

    public void SetPrintDirection(int direction) {
        this.dir = direction;
    }

    private float degreeTo360(float degree) {
        if (((double) degree) < 0.0d) {
            do {
                degree = (float) (((double) degree) + 360.0d);
            } while (((double) degree) < 0.0d);
        } else if (((double) degree) >= 360.0d) {
            do {
                degree = (float) (((double) degree) - 360.0d);
            } while (((double) degree) >= 360.0d);
        }
        return degree;
    }

    private class DXDY {
        float dx;
        float dy;

        private DXDY() {
        }
    }

    private void measureTranslate(float w, float h, float x, float y, float tw, float th, DXDY dxdy, float rotation) {
        float dx = x;
        float dy = y;
        float abssinth = (float) Math.abs(((double) th) * Math.sin(0.017453292519943295d * ((double) rotation)));
        float abscosth = (float) Math.abs(((double) th) * Math.cos(0.017453292519943295d * ((double) rotation)));
        float abssintw = (float) Math.abs(((double) tw) * Math.sin(0.017453292519943295d * ((double) rotation)));
        float dw = abssinth + ((float) Math.abs(((double) tw) * Math.cos(0.017453292519943295d * ((double) rotation))));
        float dh = abscosth + abssintw;
        float rotation2 = degreeTo360(rotation);
        if (rotation2 == 0.0f) {
            if (x == -1.0f) {
                dx = 0.0f;
            } else if (x == -2.0f) {
                dx = (w / 2.0f) - (tw / 2.0f);
            } else if (x == -3.0f) {
                dx = w - tw;
            }
            if (y == -1.0f) {
                dy = 0.0f;
            } else if (y == -2.0f) {
                dy = (h / 2.0f) - (th / 2.0f);
            } else if (y == -3.0f) {
                dy = h - th;
            }
        } else if (rotation2 > 0.0f && rotation2 < 90.0f) {
            if (x == -1.0f) {
                dx = abssinth;
            } else if (x == -2.0f) {
                dx = ((w / 2.0f) - (dw / 2.0f)) + abssinth;
            } else if (x == -3.0f) {
                dx = (w - dw) + abssinth;
            }
            if (y == -1.0f) {
                dy = 0.0f;
            } else if (y == -2.0f) {
                dy = (h / 2.0f) - (dh / 2.0f);
            } else if (y == -3.0f) {
                dy = h - dh;
            }
        } else if (rotation2 == 90.0f) {
            if (x == -1.0f) {
                dx = th;
            } else if (x == -2.0f) {
                dx = (w / 2.0f) + (th / 2.0f);
            } else if (x == -3.0f) {
                dx = w - y;
            }
            if (y == -1.0f) {
                dy = 0.0f;
            } else if (y == -2.0f) {
                dy = (h / 2.0f) - (tw / 2.0f);
            } else if (y == -3.0f) {
                dy = h - tw;
            }
        } else if (rotation2 > 90.0f && rotation2 < 180.0f) {
            if (x == -1.0f) {
                dx = dw;
            } else if (x == -2.0f) {
                dx = (w / 2.0f) + (dw / 2.0f);
            } else if (x == -3.0f) {
                dx = w - y;
            }
            if (y == -1.0f) {
                dy = abscosth;
            } else if (y == -2.0f) {
                dy = ((h / 2.0f) - (dh / 2.0f)) + abscosth;
            } else if (y == -3.0f) {
                dy = (h - dh) + abscosth;
            }
        } else if (rotation2 == 180.0f) {
            if (x == -1.0f) {
                dx = tw;
            } else if (x == -2.0f) {
                dx = (w / 2.0f) + (tw / 2.0f);
            } else if (x == -3.0f) {
                dx = w;
            }
            if (y == -1.0f) {
                dy = th;
            } else if (y == -2.0f) {
                dy = (h / 2.0f) + (th / 2.0f);
            } else if (y == -3.0f) {
                dy = h;
            }
        } else if (rotation2 > 180.0f && rotation2 < 270.0f) {
            if (x == -1.0f) {
                dx = dw - abscosth;
            } else if (x == -2.0f) {
                dx = ((w / 2.0f) + (dw / 2.0f)) - abscosth;
            } else if (x == -3.0f) {
                dx = w - abscosth;
            }
            if (y == -1.0f) {
                dy = dh;
            } else if (y == -2.0f) {
                dy = (h / 2.0f) + (dh / 2.0f);
            } else if (y == -3.0f) {
                dy = h;
            }
        } else if (rotation2 == 270.0f) {
            if (x == -1.0f) {
                dx = 0.0f;
            } else if (x == -2.0f) {
                dx = (w - th) / 2.0f;
            } else if (x == -3.0f) {
                dx = w - th;
            }
            if (y == -1.0f) {
                dy = tw;
            } else if (y == -2.0f) {
                dy = (h + tw) / 2.0f;
            } else if (y == -3.0f) {
                dy = h;
            }
        } else if (rotation2 > 270.0f && rotation2 < 360.0f) {
            if (x == -1.0f) {
                dx = 0.0f;
            } else if (x == -2.0f) {
                dx = (w / 2.0f) - (dw / 2.0f);
            } else if (x == -3.0f) {
                dx = w - dw;
            }
            if (y == -1.0f) {
                dy = dh - abscosth;
            } else if (y == -2.0f) {
                dy = ((h / 2.0f) + (dh / 2.0f)) - abscosth;
            } else if (y == -3.0f) {
                dy = h - abscosth;
            }
        }
        dxdy.dx = dx;
        dxdy.dy = dy;
    }

    public void DrawText(String text, float x, float y, float rotation, Typeface typeface, float textSize, int nFontStyle) {
        this.paint.setTypeface(typeface);
        this.paint.setTextSize(textSize);
        this.paint.setFakeBoldText((nFontStyle & 8) != 0);
        this.paint.setUnderlineText((nFontStyle & 128) != 0);
        float w = (float) this.canvas.getWidth();
        float h = (float) this.canvas.getHeight();
        Paint.FontMetricsInt fm = this.paint.getFontMetricsInt();
        float tw = this.paint.measureText(text);
        float th = (float) (fm.descent - fm.ascent);
        DXDY dxdy = new DXDY();
        this.canvas.save();
        if (this.dir == 0) {
            this.canvas.translate(0.0f, 0.0f);
        } else if (this.dir == 1) {
            this.canvas.translate(0.0f, h);
        } else if (this.dir == 2) {
            this.canvas.translate(w, h);
        } else if (this.dir == 3) {
            this.canvas.translate(w, 0.0f);
        }
        this.canvas.rotate((float) (this.dir * -90));
        if (this.dir == 0 || this.dir == 2) {
            measureTranslate(w, h, x, y, tw, th, dxdy, rotation);
        } else {
            measureTranslate(h, w, x, y, tw, th, dxdy, rotation);
        }
        this.canvas.translate(dxdy.dx, dxdy.dy);
        this.canvas.rotate(rotation);
        this.canvas.drawText(text, 0.0f, (float) (-fm.ascent), this.paint);
        this.canvas.restore();
    }

    public void DrawTextAutoNewLine(String text, float x, float y, float rotation, Typeface typeface, float textSize, int nFontStyle, int outerwidth, float spacingmult, float spacingadd) {
        this.textpaint.setTypeface(typeface);
        this.textpaint.setTextSize(textSize);
        this.textpaint.setFakeBoldText((nFontStyle & 8) != 0);
        this.textpaint.setUnderlineText((nFontStyle & 128) != 0);
        StaticLayout m_layout = new StaticLayout(text, this.textpaint, outerwidth, Layout.Alignment.ALIGN_NORMAL, spacingmult, spacingadd, true);
        float w = (float) this.canvas.getWidth();
        float h = (float) this.canvas.getHeight();
        float tw = (float) m_layout.getWidth();
        float th = (float) m_layout.getHeight();
        DXDY dxdy = new DXDY();
        this.canvas.save();
        if (this.dir == 0) {
            this.canvas.translate(0.0f, 0.0f);
        } else if (this.dir == 1) {
            this.canvas.translate(0.0f, h);
        } else if (this.dir == 2) {
            this.canvas.translate(w, h);
        } else if (this.dir == 3) {
            this.canvas.translate(w, 0.0f);
        }
        this.canvas.rotate((float) (this.dir * -90));
        if (this.dir == 0 || this.dir == 2) {
            measureTranslate(w, h, x, y, tw, th, dxdy, rotation);
        } else {
            measureTranslate(h, w, x, y, tw, th, dxdy, rotation);
        }
        this.canvas.translate(dxdy.dx, dxdy.dy);
        this.canvas.rotate(rotation);
        m_layout.draw(this.canvas);
        this.canvas.restore();
    }

    public void DrawTextMultiLine(String text, float x, float y, float rotation, Typeface typeface, float textSize, int nFontStyle, float rowWidth, float lineSpacing) {
        TextPaint textPaint = new TextPaint();
        textPaint.setTypeface(typeface);
        textPaint.setTextSize(textSize);
        textPaint.setFakeBoldText((nFontStyle & 8) != 0);
        textPaint.setUnderlineText((nFontStyle & 128) != 0);
        float w = (float) this.canvas.getWidth();
        float h = (float) this.canvas.getHeight();
        StaticLayout layout = new StaticLayout(text, textPaint, (int) rowWidth, Layout.Alignment.ALIGN_NORMAL, 0.0f, lineSpacing, true);
        float tw = (float) layout.getWidth();
        float th = (float) layout.getHeight();
        DXDY dxdy = new DXDY();
        this.canvas.save();
        if (this.dir == 0) {
            this.canvas.translate(0.0f, 0.0f);
        } else if (this.dir == 1) {
            this.canvas.translate(0.0f, h);
        } else if (this.dir == 2) {
            this.canvas.translate(w, h);
        } else if (this.dir == 3) {
            this.canvas.translate(w, 0.0f);
        }
        this.canvas.rotate((float) (this.dir * -90));
        if (this.dir == 0 || this.dir == 2) {
            measureTranslate(w, h, x, y, tw, th, dxdy, rotation);
        } else {
            measureTranslate(h, w, x, y, tw, th, dxdy, rotation);
        }
        this.canvas.translate(dxdy.dx, dxdy.dy);
        this.canvas.rotate(rotation);
        layout.draw(this.canvas);
        this.canvas.restore();
    }

    public void DrawLine(float startX, float startY, float stopX, float stopY) {
        this.canvas.drawLine(startX, startY, stopX, stopY, this.paint);
    }

    public void DrawBox(float left, float top, float right, float bottom) {
        this.canvas.drawLine(left, top, right, top, this.paint);
        this.canvas.drawLine(right, top, right, bottom, this.paint);
        this.canvas.drawLine(right, bottom, left, bottom, this.paint);
        this.canvas.drawLine(left, bottom, left, top, this.paint);
    }

    public void DrawRect(float left, float top, float right, float bottom) {
        this.canvas.drawRect(left, top, right, bottom, this.paint);
    }

    public void DrawBitmap(Bitmap bitmap2, float x, float y, float rotation) {
        float w = (float) this.canvas.getWidth();
        float h = (float) this.canvas.getHeight();
        float tw = (float) bitmap2.getWidth();
        float th = (float) bitmap2.getHeight();
        DXDY dxdy = new DXDY();
        this.canvas.save();
        if (this.dir == 0) {
            this.canvas.translate(0.0f, 0.0f);
        } else if (this.dir == 1) {
            this.canvas.translate(0.0f, h);
        } else if (this.dir == 2) {
            this.canvas.translate(w, h);
        } else if (this.dir == 3) {
            this.canvas.translate(w, 0.0f);
        }
        this.canvas.rotate((float) (this.dir * -90));
        if (this.dir == 0 || this.dir == 2) {
            measureTranslate(w, h, x, y, tw, th, dxdy, rotation);
        } else {
            measureTranslate(h, w, x, y, tw, th, dxdy, rotation);
        }
        this.canvas.translate(dxdy.dx, dxdy.dy);
        this.canvas.rotate(rotation);
        this.canvas.drawBitmap(bitmap2, 0.0f, 0.0f, this.paint);
        this.canvas.restore();
    }

    public void DrawQRCode(String text, float x, float y, float rotation, int unitWidth, int version, int ecc) {
        Bitmap bitmap2 = null;
        int typeNumber = QRCode.getMinimumQRCodeTypeNumber(text, ecc - 1);
        if (version < typeNumber) {
            version = typeNumber;
        }
        QRCode codes = QRCode.getFixedSizeQRCode(text, ecc - 1, version);
        if (codes != null) {
            bitmap2 = QRModulesToBitmap(codes.getModules(), unitWidth);
        }
        DrawBitmap(bitmap2, x, y, rotation);
    }

    private Bitmap QRModulesToBitmap(Boolean[][] modules, int unitWidth) {
        int height = modules.length * unitWidth;
        int width = height;
        int[] pixels = new int[(width * height)];
        Bitmap bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[(width * y) + x] = modules[y / unitWidth][x / unitWidth].booleanValue() ? -16777216 : -1;
            }
        }
        bitmap2.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap2;
    }

    public void DrawBarcode(String text, float x, float y, float rotation, int unitWidth, int height, int barcodeType) {
        boolean[] bPattern;
        Bitmap bitmap2 = null;
        if (barcodeType == 73 && (bPattern = new DSCode128().Encode(text)) != null) {
            bitmap2 = BPatternToBitmap(bPattern, unitWidth, height);
        }
        DrawBitmap(bitmap2, x, y, rotation);
    }

    private Bitmap BPatternToBitmap(boolean[] bPattern, int unitWidth, int height) {
        int width = unitWidth * bPattern.length;
        int[] pixels = new int[(width * height)];
        Bitmap bitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[(width * y) + x] = bPattern[x / unitWidth] ? -16777216 : -1;
            }
        }
        bitmap2.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap2;
    }
}
