package com.baidu.mapapi.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.v7.widget.ActivityChooserView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CategoryDepth;
import com.alipay.sdk.data.a;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.platform.comapi.map.ac;
import com.baidu.platform.comapi.map.an;
import com.baidu.platform.comapi.map.e;
import com.baidu.platform.comapi.map.i;
import com.baidu.platform.comapi.map.j;
import com.baidu.platform.comapi.map.l;
import java.io.File;

public final class MapView extends ViewGroup {
    private static final String a = MapView.class.getSimpleName();
    private static String b;
    private static int c = 0;
    /* access modifiers changed from: private */
    public static final SparseArray<Integer> p = new SparseArray<>();
    private int A;
    /* access modifiers changed from: private */
    public j d;
    private BaiduMap e;
    private ImageView f;
    private Bitmap g;
    private an h;
    private Point i;
    private Point j;
    private RelativeLayout k;
    /* access modifiers changed from: private */
    public TextView l;
    /* access modifiers changed from: private */
    public TextView m;
    /* access modifiers changed from: private */
    public ImageView n;
    private Context o;
    private int q = LogoPosition.logoPostionleftBottom.ordinal();
    private boolean r = true;
    private boolean s = true;
    /* access modifiers changed from: private */
    public float t;
    private l u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    static {
        p.append(3, 2000000);
        p.append(4, Integer.valueOf(CategoryDepth.DEPTH_2));
        p.append(5, 500000);
        p.append(6, 200000);
        p.append(7, 100000);
        p.append(8, 50000);
        p.append(9, 25000);
        p.append(10, Integer.valueOf(a.d));
        p.append(11, 10000);
        p.append(12, 5000);
        p.append(13, 2000);
        p.append(14, 1000);
        p.append(15, 500);
        p.append(16, 200);
        p.append(17, 100);
        p.append(18, 50);
        p.append(19, 20);
        p.append(20, 10);
        p.append(21, 5);
        p.append(22, 2);
    }

    public MapView(Context context) {
        super(context);
        a(context, (BaiduMapOptions) null);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, (BaiduMapOptions) null);
    }

    public MapView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(context, (BaiduMapOptions) null);
    }

    public MapView(Context context, BaiduMapOptions baiduMapOptions) {
        super(context);
        a(context, baiduMapOptions);
    }

    private void a(Context context) {
        String str = "logo_h.png";
        int densityDpi = SysOSUtil.getDensityDpi();
        if (densityDpi < 180) {
            str = "logo_l.png";
        }
        Bitmap a2 = com.baidu.platform.comapi.commonutils.a.a(str, context);
        if (densityDpi > 480) {
            Matrix matrix = new Matrix();
            matrix.postScale(2.0f, 2.0f);
            this.g = Bitmap.createBitmap(a2, 0, 0, a2.getWidth(), a2.getHeight(), matrix, true);
        } else if (densityDpi <= 320 || densityDpi > 480) {
            this.g = a2;
        } else {
            Matrix matrix2 = new Matrix();
            matrix2.postScale(1.5f, 1.5f);
            this.g = Bitmap.createBitmap(a2, 0, 0, a2.getWidth(), a2.getHeight(), matrix2, true);
        }
        if (this.g != null) {
            this.f = new ImageView(context);
            this.f.setImageBitmap(this.g);
            addView(this.f);
        }
    }

    private void a(Context context, BaiduMapOptions baiduMapOptions) {
        this.o = context;
        i.a();
        BMapManager.init();
        a(context, baiduMapOptions, b, c);
        this.e = new BaiduMap(this.d);
        a(context);
        b(context);
        if (baiduMapOptions != null && !baiduMapOptions.h) {
            this.h.setVisibility(4);
        }
        c(context);
        if (baiduMapOptions != null && !baiduMapOptions.i) {
            this.k.setVisibility(4);
        }
        if (!(baiduMapOptions == null || baiduMapOptions.j == null)) {
            this.q = baiduMapOptions.j.ordinal();
        }
        if (!(baiduMapOptions == null || baiduMapOptions.l == null)) {
            this.j = baiduMapOptions.l;
        }
        if (baiduMapOptions != null && baiduMapOptions.k != null) {
            this.i = baiduMapOptions.k;
        }
    }

    private void a(Context context, BaiduMapOptions baiduMapOptions, String str, int i2) {
        if (baiduMapOptions == null) {
            this.d = new j(context, (ac) null, str, i2);
        } else {
            this.d = new j(context, baiduMapOptions.a(), str, i2);
        }
        addView(this.d);
        this.u = new i(this);
        this.d.a().a(this.u);
    }

    private void a(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-2, -2);
        }
        int i2 = layoutParams.width;
        int makeMeasureSpec = i2 > 0 ? View.MeasureSpec.makeMeasureSpec(i2, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0);
        int i3 = layoutParams.height;
        view.measure(makeMeasureSpec, i3 > 0 ? View.MeasureSpec.makeMeasureSpec(i3, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    /* access modifiers changed from: private */
    public void b() {
        boolean z2 = false;
        float f2 = this.d.a().E().a;
        if (this.h.a()) {
            this.h.b(f2 > this.d.a().b);
            an anVar = this.h;
            if (f2 < this.d.a().a) {
                z2 = true;
            }
            anVar.a(z2);
        }
    }

    private void b(Context context) {
        this.h = new an(context, false);
        if (this.h.a()) {
            this.h.b((View.OnClickListener) new j(this));
            this.h.a((View.OnClickListener) new k(this));
            addView(this.h);
        }
    }

    private void c(Context context) {
        this.k = new RelativeLayout(context);
        this.k.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.l = new TextView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        this.l.setTextColor(Color.parseColor("#FFFFFF"));
        this.l.setTextSize(2, 11.0f);
        this.l.setTypeface(this.l.getTypeface(), 1);
        this.l.setLayoutParams(layoutParams);
        this.l.setId(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        this.k.addView(this.l);
        this.m = new TextView(context);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.width = -2;
        layoutParams2.height = -2;
        layoutParams2.addRule(14);
        this.m.setTextColor(Color.parseColor("#000000"));
        this.m.setTextSize(2, 11.0f);
        this.m.setLayoutParams(layoutParams2);
        this.k.addView(this.m);
        this.n = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.width = -2;
        layoutParams3.height = -2;
        layoutParams3.addRule(14);
        layoutParams3.addRule(3, this.l.getId());
        this.n.setLayoutParams(layoutParams3);
        Bitmap a2 = com.baidu.platform.comapi.commonutils.a.a("icon_scale.9.png", context);
        byte[] ninePatchChunk = a2.getNinePatchChunk();
        NinePatch.isNinePatchChunk(ninePatchChunk);
        this.n.setBackgroundDrawable(new NinePatchDrawable(a2, ninePatchChunk, new Rect(), (String) null));
        this.k.addView(this.n);
        addView(this.k);
    }

    public static void setCustomMapStylePath(String str) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("customMapStylePath String is illegal");
        } else if (!new File(str).exists()) {
            throw new RuntimeException("please check whether the customMapStylePath file exits");
        } else {
            b = str;
        }
    }

    public static void setIconCustom(int i2) {
        c = i2;
    }

    public static void setMapCustomEnable(boolean z2) {
        i.a(z2);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof MapViewLayoutParams) {
            super.addView(view, layoutParams);
        }
    }

    public void cancelRenderMap() {
        this.d.a().t(false);
        this.d.a().N().clear();
    }

    public final LogoPosition getLogoPosition() {
        switch (this.q) {
            case 1:
                return LogoPosition.logoPostionleftTop;
            case 2:
                return LogoPosition.logoPostionCenterBottom;
            case 3:
                return LogoPosition.logoPostionCenterTop;
            case 4:
                return LogoPosition.logoPostionRightBottom;
            case 5:
                return LogoPosition.logoPostionRightTop;
            default:
                return LogoPosition.logoPostionleftBottom;
        }
    }

    public final BaiduMap getMap() {
        this.e.a = this;
        return this.e;
    }

    public final int getMapLevel() {
        return p.get((int) this.d.a().E().a).intValue();
    }

    public int getScaleControlViewHeight() {
        return this.z;
    }

    public int getScaleControlViewWidth() {
        return this.A;
    }

    public boolean handleMultiTouch(float f2, float f3, float f4, float f5) {
        return this.d != null && this.d.a(f2, f3, f4, f5);
    }

    public void handleTouchDown(float f2, float f3) {
        if (this.d != null) {
            this.d.a(f2, f3);
        }
    }

    public boolean handleTouchMove(float f2, float f3) {
        return this.d != null && this.d.c(f2, f3);
    }

    public boolean handleTouchUp(float f2, float f3) {
        if (this.d == null) {
            return false;
        }
        return this.d.b(f2, f3);
    }

    public boolean inRangeOfView(float f2, float f3) {
        return this.d != null && this.d.d(f2, f3);
    }

    public void onCreate(Context context, Bundle bundle) {
        if (bundle != null) {
            b = bundle.getString("customMapPath");
            if (bundle == null) {
                a(context, new BaiduMapOptions());
                return;
            }
            MapStatus mapStatus = (MapStatus) bundle.getParcelable("mapstatus");
            if (this.i != null) {
                this.i = (Point) bundle.getParcelable("scalePosition");
            }
            if (this.j != null) {
                this.j = (Point) bundle.getParcelable("zoomPosition");
            }
            this.r = bundle.getBoolean("mZoomControlEnabled");
            this.s = bundle.getBoolean("mScaleControlEnabled");
            this.q = bundle.getInt("logoPosition");
            setPadding(bundle.getInt("paddingLeft"), bundle.getInt("paddingTop"), bundle.getInt("paddingRight"), bundle.getInt("paddingBottom"));
            a(context, new BaiduMapOptions().mapStatus(mapStatus));
        }
    }

    public final void onDestroy() {
        if (this.o != null) {
            this.d.b(this.o.hashCode());
        }
        if (this.g != null && !this.g.isRecycled()) {
            this.g.recycle();
            this.g = null;
        }
        if (b != null) {
            b = null;
        }
        this.h.b();
        BMapManager.destroy();
        i.b();
        this.o = null;
    }

    /* access modifiers changed from: protected */
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        float f2;
        float f3;
        Point a2;
        int measuredHeight;
        int width;
        int childCount = getChildCount();
        a((View) this.f);
        if (((getWidth() - this.v) - this.w) - this.f.getMeasuredWidth() <= 0 || ((getHeight() - this.x) - this.y) - this.f.getMeasuredHeight() <= 0) {
            this.v = 0;
            this.w = 0;
            this.y = 0;
            this.x = 0;
            f2 = 1.0f;
            f3 = 1.0f;
        } else {
            f2 = ((float) ((getWidth() - this.v) - this.w)) / ((float) getWidth());
            f3 = ((float) ((getHeight() - this.x) - this.y)) / ((float) getHeight());
        }
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt != null) {
                if (childAt == this.d) {
                    this.d.layout(0, 0, getWidth(), getHeight());
                } else if (childAt == this.f) {
                    int i7 = (int) (((float) this.v) + (5.0f * f2));
                    int i8 = (int) (((float) this.w) + (5.0f * f2));
                    int i9 = (int) (((float) this.x) + (5.0f * f3));
                    int i10 = (int) (((float) this.y) + (5.0f * f3));
                    switch (this.q) {
                        case 1:
                            measuredHeight = i9 + this.f.getMeasuredHeight();
                            width = this.f.getMeasuredWidth() + i7;
                            break;
                        case 2:
                            measuredHeight = getHeight() - i10;
                            i9 = measuredHeight - this.f.getMeasuredHeight();
                            i7 = (((getWidth() - this.f.getMeasuredWidth()) + this.v) - this.w) / 2;
                            width = (((getWidth() + this.f.getMeasuredWidth()) + this.v) - this.w) / 2;
                            break;
                        case 3:
                            measuredHeight = i9 + this.f.getMeasuredHeight();
                            i7 = (((getWidth() - this.f.getMeasuredWidth()) + this.v) - this.w) / 2;
                            width = (((getWidth() + this.f.getMeasuredWidth()) + this.v) - this.w) / 2;
                            break;
                        case 4:
                            measuredHeight = getHeight() - i10;
                            i9 = measuredHeight - this.f.getMeasuredHeight();
                            width = getWidth() - i8;
                            i7 = width - this.f.getMeasuredWidth();
                            break;
                        case 5:
                            measuredHeight = i9 + this.f.getMeasuredHeight();
                            width = getWidth() - i8;
                            i7 = width - this.f.getMeasuredWidth();
                            break;
                        default:
                            measuredHeight = getHeight() - i10;
                            width = this.f.getMeasuredWidth() + i7;
                            i9 = measuredHeight - this.f.getMeasuredHeight();
                            break;
                    }
                    this.f.layout(i7, i9, width, measuredHeight);
                } else if (childAt == this.h) {
                    if (this.h.a()) {
                        a((View) this.h);
                        if (this.j == null) {
                            int height = (int) ((((float) (getHeight() - 15)) * f3) + ((float) this.x));
                            int width2 = (int) ((((float) (getWidth() - 15)) * f2) + ((float) this.v));
                            int measuredWidth = width2 - this.h.getMeasuredWidth();
                            int measuredHeight2 = height - this.h.getMeasuredHeight();
                            if (this.q == 4) {
                                height -= this.f.getMeasuredHeight();
                                measuredHeight2 -= this.f.getMeasuredHeight();
                            }
                            this.h.layout(measuredWidth, measuredHeight2, width2, height);
                        } else {
                            this.h.layout(this.j.x, this.j.y, this.j.x + this.h.getMeasuredWidth(), this.j.y + this.h.getMeasuredHeight());
                        }
                    }
                } else if (childAt == this.k) {
                    a((View) this.k);
                    if (this.i == null) {
                        this.A = this.k.getMeasuredWidth();
                        this.z = this.k.getMeasuredHeight();
                        int i11 = (int) (((float) this.v) + (5.0f * f2));
                        int height2 = (getHeight() - ((int) ((((float) this.y) + (5.0f * f3)) + 56.0f))) - this.f.getMeasuredHeight();
                        this.k.layout(i11, height2, this.A + i11, this.z + height2);
                    } else {
                        this.k.layout(this.i.x, this.i.y, this.i.x + this.k.getMeasuredWidth(), this.i.y + this.k.getMeasuredHeight());
                    }
                } else {
                    ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                    if (layoutParams == null) {
                        Log.e("test", "lp == null");
                    }
                    if (layoutParams instanceof MapViewLayoutParams) {
                        MapViewLayoutParams mapViewLayoutParams = (MapViewLayoutParams) layoutParams;
                        if (mapViewLayoutParams.c == MapViewLayoutParams.ELayoutMode.absoluteMode) {
                            a2 = mapViewLayoutParams.b;
                        } else {
                            a2 = this.d.a().a(CoordUtil.ll2mc(mapViewLayoutParams.a));
                        }
                        a(childAt);
                        int measuredWidth2 = childAt.getMeasuredWidth();
                        int measuredHeight3 = childAt.getMeasuredHeight();
                        float f4 = mapViewLayoutParams.d;
                        float f5 = mapViewLayoutParams.e;
                        int i12 = (int) (((float) a2.x) - (f4 * ((float) measuredWidth2)));
                        int i13 = mapViewLayoutParams.f + ((int) (((float) a2.y) - (f5 * ((float) measuredHeight3))));
                        childAt.layout(i12, i13, i12 + measuredWidth2, i13 + measuredHeight3);
                    }
                }
            }
        }
    }

    public final void onPause() {
        this.d.onPause();
    }

    public final void onResume() {
        this.d.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null && this.e != null) {
            bundle.putParcelable("mapstatus", this.e.getMapStatus());
            if (this.i != null) {
                bundle.putParcelable("scalePosition", this.i);
            }
            if (this.j != null) {
                bundle.putParcelable("zoomPosition", this.j);
            }
            bundle.putBoolean("mZoomControlEnabled", this.r);
            bundle.putBoolean("mScaleControlEnabled", this.s);
            bundle.putInt("logoPosition", this.q);
            bundle.putInt("paddingLeft", this.v);
            bundle.putInt("paddingTop", this.x);
            bundle.putInt("paddingRight", this.w);
            bundle.putInt("paddingBottom", this.y);
            bundle.putString("customMapPath", b);
        }
    }

    public void removeView(View view) {
        if (view != this.f) {
            super.removeView(view);
        }
    }

    public void renderMap() {
        e a2 = this.d.a();
        a2.t(true);
        a2.O();
    }

    public final void setLogoPosition(LogoPosition logoPosition) {
        if (logoPosition == null) {
            this.q = LogoPosition.logoPostionleftBottom.ordinal();
        }
        this.q = logoPosition.ordinal();
        requestLayout();
    }

    public void setPadding(int i2, int i3, int i4, int i5) {
        this.v = i2;
        this.x = i3;
        this.w = i4;
        this.y = i5;
    }

    public void setScaleControlPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.i = point;
            requestLayout();
        }
    }

    public void setUpViewEventToMapView(MotionEvent motionEvent) {
        this.d.onTouchEvent(motionEvent);
    }

    public final void setZOrderMediaOverlay(boolean z2) {
        if (this.d != null) {
            this.d.setZOrderMediaOverlay(z2);
        }
    }

    public void setZoomControlsPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.j = point;
            requestLayout();
        }
    }

    public void showScaleControl(boolean z2) {
        this.k.setVisibility(z2 ? 0 : 8);
        this.s = z2;
    }

    public void showZoomControls(boolean z2) {
        if (this.h.a()) {
            this.h.setVisibility(z2 ? 0 : 8);
            this.r = z2;
        }
    }
}
