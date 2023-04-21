package vi.com.gdi.bgl.android.java;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.SparseArray;
import com.baidu.mapapi.common.SysOSUtil;

public class EnvDrawText {
    private static final String a = EnvDrawText.class.getSimpleName();
    public static boolean bBmpChange = false;
    public static Bitmap bmp = null;
    public static int[] buffer = null;
    public static Canvas canvasTemp = null;
    public static SparseArray<a> fontCache = null;
    public static int iWordHightMax = 0;
    public static int iWordWidthMax = 0;
    public static Paint pt = null;

    public static synchronized int[] drawText(String str, int i, int i2, int[] iArr, int i3, int i4, int i5, int i6) {
        int measureText;
        int[] iArr2;
        a aVar;
        synchronized (EnvDrawText.class) {
            if (pt == null) {
                pt = new Paint();
            } else {
                pt.reset();
            }
            String phoneType = SysOSUtil.getPhoneType();
            if (phoneType != null && phoneType.equals("vivo X3L")) {
                i2 = 0;
            }
            switch (i2) {
                case 1:
                    pt.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
                    break;
                case 2:
                    pt.setTypeface(Typeface.create(Typeface.DEFAULT, 2));
                    break;
                default:
                    pt.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
                    break;
            }
            pt.setSubpixelText(true);
            pt.setAntiAlias(true);
            if (!(i2 == 0 || fontCache == null || (aVar = fontCache.get(i2)) == null)) {
                pt.setTypeface(aVar.a);
            }
            pt.setTextSize((float) i);
            int indexOf = str.indexOf(92, 0);
            if (indexOf == -1) {
                Paint.FontMetrics fontMetrics = pt.getFontMetrics();
                int measureText2 = (int) pt.measureText(str);
                int ceil = (int) Math.ceil((double) (fontMetrics.descent - fontMetrics.ascent));
                iArr[0] = measureText2;
                iArr[1] = ceil;
                int pow = (int) Math.pow(2.0d, (double) ((int) Math.ceil(Math.log((double) measureText2) / Math.log(2.0d))));
                int pow2 = (int) Math.pow(2.0d, (double) ((int) Math.ceil(Math.log((double) ceil) / Math.log(2.0d))));
                if (iWordWidthMax < pow || iWordHightMax < pow2) {
                    bBmpChange = true;
                    iWordWidthMax = pow;
                    iWordHightMax = pow2;
                }
                iArr[2] = iWordWidthMax;
                iArr[3] = iWordHightMax;
                if (bBmpChange) {
                    if (bmp != null && !bmp.isRecycled()) {
                        bmp.recycle();
                    }
                    bmp = Bitmap.createBitmap(iWordWidthMax, iWordHightMax, Bitmap.Config.ARGB_8888);
                    if (canvasTemp == null) {
                        canvasTemp = new Canvas();
                    }
                    canvasTemp.setBitmap(bmp);
                } else {
                    bmp.eraseColor(0);
                }
                if ((-16777216 & i5) == 0) {
                    canvasTemp.drawColor(33554431);
                } else {
                    canvasTemp.drawColor(i5);
                }
                if (i6 != 0) {
                    pt.setStrokeWidth((float) i6);
                    pt.setStrokeCap(Paint.Cap.ROUND);
                    pt.setStrokeJoin(Paint.Join.ROUND);
                    pt.setStyle(Paint.Style.STROKE);
                    pt.setColor(i4);
                    canvasTemp.drawText(str, 0.0f, 0.0f - fontMetrics.ascent, pt);
                }
                pt.setStyle(Paint.Style.FILL);
                pt.setColor(i3);
                canvasTemp.drawText(str, 0.0f, 0.0f - fontMetrics.ascent, pt);
            } else {
                int i7 = indexOf + 1;
                int measureText3 = (int) pt.measureText(str.substring(0, indexOf));
                int i8 = i7;
                int i9 = 2;
                while (true) {
                    int indexOf2 = str.indexOf(92, i8);
                    if (indexOf2 > 0) {
                        int measureText4 = (int) pt.measureText(str.substring(i8, indexOf2));
                        if (measureText4 <= measureText3) {
                            measureText4 = measureText3;
                        }
                        i9++;
                        measureText3 = measureText4;
                        i8 = indexOf2 + 1;
                    } else {
                        if (i8 != str.length() && (measureText = (int) pt.measureText(str.substring(i8, str.length()))) > measureText3) {
                            measureText3 = measureText;
                        }
                        Paint.FontMetrics fontMetrics2 = pt.getFontMetrics();
                        int ceil2 = (int) Math.ceil((double) (fontMetrics2.descent - fontMetrics2.ascent));
                        int i10 = ceil2 * i9;
                        iArr[0] = measureText3;
                        iArr[1] = i10;
                        int pow3 = (int) Math.pow(2.0d, (double) ((int) Math.ceil(Math.log((double) measureText3) / Math.log(2.0d))));
                        int pow4 = (int) Math.pow(2.0d, (double) ((int) Math.ceil(Math.log((double) i10) / Math.log(2.0d))));
                        if (iWordWidthMax < pow3 || iWordHightMax < pow4) {
                            bBmpChange = true;
                            iWordWidthMax = pow3;
                            iWordHightMax = pow4;
                        }
                        iArr[2] = iWordWidthMax;
                        iArr[3] = iWordHightMax;
                        if (bBmpChange) {
                            if (bmp != null && !bmp.isRecycled()) {
                                bmp.recycle();
                            }
                            bmp = Bitmap.createBitmap(iWordWidthMax, iWordHightMax, Bitmap.Config.ARGB_8888);
                            if (canvasTemp == null) {
                                canvasTemp = new Canvas();
                            }
                            canvasTemp.setBitmap(bmp);
                        } else {
                            bmp.eraseColor(0);
                        }
                        if ((-16777216 & i5) == 0) {
                            canvasTemp.drawColor(33554431);
                        } else {
                            canvasTemp.drawColor(i5);
                        }
                        int i11 = 0;
                        int i12 = 0;
                        while (true) {
                            int indexOf3 = str.indexOf(92, i11);
                            if (indexOf3 > 0) {
                                String substring = str.substring(i11, indexOf3);
                                int measureText5 = (int) pt.measureText(substring);
                                i11 = indexOf3 + 1;
                                if (i6 != 0) {
                                    pt.setStrokeWidth((float) i6);
                                    pt.setStrokeCap(Paint.Cap.ROUND);
                                    pt.setStrokeJoin(Paint.Join.ROUND);
                                    pt.setStyle(Paint.Style.STROKE);
                                    pt.setColor(i4);
                                    canvasTemp.drawText(substring, (float) ((iArr[0] - measureText5) / 2), ((float) (i12 * ceil2)) - fontMetrics2.ascent, pt);
                                }
                                pt.setStyle(Paint.Style.FILL);
                                pt.setColor(i3);
                                canvasTemp.drawText(substring, (float) ((iArr[0] - measureText5) / 2), ((float) (i12 * ceil2)) - fontMetrics2.ascent, pt);
                                i12++;
                            } else if (i11 != str.length()) {
                                String substring2 = str.substring(i11, str.length());
                                int measureText6 = (int) pt.measureText(substring2);
                                if (i6 != 0) {
                                    pt.setStrokeWidth((float) i6);
                                    pt.setStrokeCap(Paint.Cap.ROUND);
                                    pt.setStrokeJoin(Paint.Join.ROUND);
                                    pt.setStyle(Paint.Style.STROKE);
                                    pt.setColor(i4);
                                    canvasTemp.drawText(substring2, (float) ((iArr[0] - measureText6) / 2), ((float) (i12 * ceil2)) - fontMetrics2.ascent, pt);
                                }
                                pt.setStyle(Paint.Style.FILL);
                                pt.setColor(i3);
                                canvasTemp.drawText(substring2, (float) ((iArr[0] - measureText6) / 2), ((float) (i12 * ceil2)) - fontMetrics2.ascent, pt);
                            }
                        }
                    }
                }
            }
            int i13 = iWordWidthMax * iWordHightMax;
            if (bBmpChange) {
                buffer = new int[i13];
            }
            bmp.getPixels(buffer, 0, iWordWidthMax, 0, 0, iWordWidthMax, iWordHightMax);
            bBmpChange = false;
            iArr2 = buffer;
        }
        return iArr2;
    }

    public static short[] getTextSize(String str, int i, int i2) {
        int length = str.length();
        if (length == 0) {
            return null;
        }
        Paint paint = new Paint();
        paint.setSubpixelText(true);
        paint.setAntiAlias(true);
        paint.setTextSize((float) i);
        switch (i2) {
            case 1:
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
                break;
            case 2:
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, 2));
                break;
            default:
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
                break;
        }
        short[] sArr = new short[length];
        for (int i3 = 0; i3 < length; i3++) {
            sArr[i3] = (short) ((int) paint.measureText(str.substring(0, i3 + 1)));
        }
        return sArr;
    }

    public static synchronized void registFontCache(int i, Typeface typeface) {
        synchronized (EnvDrawText.class) {
            if (!(i == 0 || typeface == null)) {
                if (fontCache == null) {
                    fontCache = new SparseArray<>();
                }
                a aVar = fontCache.get(i);
                if (aVar == null) {
                    a aVar2 = new a();
                    aVar2.a = typeface;
                    aVar2.b++;
                    fontCache.put(i, aVar2);
                } else {
                    aVar.b++;
                }
            }
        }
    }

    public static synchronized void removeFontCache(int i) {
        synchronized (EnvDrawText.class) {
            a aVar = fontCache.get(i);
            if (aVar != null) {
                aVar.b--;
                if (aVar.b == 0) {
                    fontCache.remove(i);
                }
            }
        }
    }
}
