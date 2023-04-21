package com.baidu.mapapi.map;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.ActivityChooserView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CategoryDepth;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.platform.comapi.map.ac;
import com.baidu.platform.comapi.map.an;
import com.baidu.platform.comapi.map.i;
import com.baidu.platform.comapi.map.j;
import com.baidu.platform.comapi.map.l;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

@TargetApi(20)
public class WearMapView extends ViewGroup implements View.OnApplyWindowInsetsListener {
    public static final int BT_INVIEW = 1;
    private static final String b = MapView.class.getSimpleName();
    private static String c;
    private static int d = 0;
    private static int r = 0;
    private static int s = 0;
    private static int t = 10;
    /* access modifiers changed from: private */
    public static final SparseArray<Integer> w = new SparseArray<>();
    private l A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    ScreenShape a = ScreenShape.ROUND;
    /* access modifiers changed from: private */
    public j e;
    private BaiduMap f;
    private ImageView g;
    private Bitmap h;
    /* access modifiers changed from: private */
    public an i;
    private boolean j = true;
    private Point k;
    private Point l;
    private RelativeLayout m;
    public AnimationTask mTask;
    public Timer mTimer;
    public a mTimerHandler;
    private SwipeDismissView n;
    /* access modifiers changed from: private */
    public TextView o;
    /* access modifiers changed from: private */
    public TextView p;
    /* access modifiers changed from: private */
    public ImageView q;
    private boolean u = true;
    private Context v;
    private boolean x = true;
    private boolean y = true;
    /* access modifiers changed from: private */
    public float z;

    public class AnimationTask extends TimerTask {
        public AnimationTask() {
        }

        public void run() {
            Message message = new Message();
            message.what = 1;
            WearMapView.this.mTimerHandler.sendMessage(message);
        }
    }

    public interface OnDismissCallback {
        void onDismiss();

        void onNotify();
    }

    public enum ScreenShape {
        ROUND,
        RECTANGLE,
        UNDETECTED
    }

    private class a extends Handler {
        private final WeakReference<Context> b;

        public a(Context context) {
            this.b = new WeakReference<>(context);
        }

        public void handleMessage(Message message) {
            if (((Context) this.b.get()) != null) {
                super.handleMessage(message);
                switch (message.what) {
                    case 1:
                        if (WearMapView.this.i != null) {
                            WearMapView.this.a(true);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    static {
        w.append(3, 2000000);
        w.append(4, Integer.valueOf(CategoryDepth.DEPTH_2));
        w.append(5, 500000);
        w.append(6, 200000);
        w.append(7, 100000);
        w.append(8, 50000);
        w.append(9, 25000);
        w.append(10, Integer.valueOf(com.alipay.sdk.data.a.d));
        w.append(11, 10000);
        w.append(12, 5000);
        w.append(13, 2000);
        w.append(14, 1000);
        w.append(15, 500);
        w.append(16, 200);
        w.append(17, 100);
        w.append(18, 50);
        w.append(19, 20);
        w.append(20, 10);
        w.append(21, 5);
        w.append(22, 2);
    }

    public WearMapView(Context context) {
        super(context);
        a(context, (BaiduMapOptions) null);
    }

    public WearMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, (BaiduMapOptions) null);
    }

    public WearMapView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(context, (BaiduMapOptions) null);
    }

    public WearMapView(Context context, BaiduMapOptions baiduMapOptions) {
        super(context);
        a(context, baiduMapOptions);
    }

    private int a(int i2, int i3) {
        return i2 - ((int) Math.sqrt(Math.pow((double) i2, 2.0d) - Math.pow((double) i3, 2.0d)));
    }

    private void a(int i2) {
        if (this.e != null) {
            switch (i2) {
                case 0:
                    this.e.onPause();
                    b();
                    return;
                case 1:
                    this.e.onResume();
                    c();
                    return;
                default:
                    return;
            }
        }
    }

    private static void a(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        r = point.x;
        s = point.y;
    }

    private void a(Context context, BaiduMapOptions baiduMapOptions) {
        a(context);
        setOnApplyWindowInsetsListener(this);
        this.v = context;
        this.mTimerHandler = new a(context);
        this.mTimer = new Timer();
        if (!(this.mTimer == null || this.mTask == null)) {
            this.mTask.cancel();
        }
        this.mTask = new AnimationTask();
        this.mTimer.schedule(this.mTask, 5000);
        i.a();
        BMapManager.init();
        a(context, baiduMapOptions, c);
        this.f = new BaiduMap(this.e);
        this.e.a().q(false);
        this.e.a().p(false);
        c(context);
        d(context);
        b(context);
        if (baiduMapOptions != null && !baiduMapOptions.h) {
            this.i.setVisibility(4);
        }
        e(context);
        if (baiduMapOptions != null && !baiduMapOptions.i) {
            this.m.setVisibility(4);
        }
        if (!(baiduMapOptions == null || baiduMapOptions.l == null)) {
            this.l = baiduMapOptions.l;
        }
        if (baiduMapOptions != null && baiduMapOptions.k != null) {
            this.k = baiduMapOptions.k;
        }
    }

    private void a(Context context, BaiduMapOptions baiduMapOptions, String str) {
        if (baiduMapOptions == null) {
            this.e = new j(context, (ac) null, str, d);
        } else {
            this.e = new j(context, baiduMapOptions.a(), str, d);
        }
        addView(this.e);
        this.A = new u(this);
        this.e.a().a(this.A);
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

    private void a(View view, boolean z2) {
        if (z2) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(view, "TranslationY", new float[]{0.0f, -50.0f}), ObjectAnimator.ofFloat(view, "alpha", new float[]{1.0f, 0.0f})});
            animatorSet.addListener(new x(this, view));
            animatorSet.setDuration(1200);
            animatorSet.start();
            return;
        }
        view.setVisibility(0);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(new Animator[]{ObjectAnimator.ofFloat(view, "TranslationY", new float[]{-50.0f, 0.0f}), ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f, 1.0f})});
        animatorSet2.setDuration(1200);
        animatorSet2.start();
    }

    /* access modifiers changed from: private */
    public void a(boolean z2) {
        if (this.j) {
            a((View) this.i, z2);
        }
    }

    private void b() {
        if (this.e != null && !this.u) {
            d();
            this.u = true;
        }
    }

    private void b(Context context) {
        this.n = new SwipeDismissView(context, this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((int) ((context.getResources().getDisplayMetrics().density * 34.0f) + 0.5f), s);
        this.n.setBackgroundColor(Color.argb(0, 0, 0, 0));
        this.n.setLayoutParams(layoutParams);
        addView(this.n);
    }

    private void c() {
        if (this.e != null && this.u) {
            e();
            this.u = false;
        }
    }

    private void c(Context context) {
        String str = "logo_h.png";
        int densityDpi = SysOSUtil.getDensityDpi();
        if (densityDpi < 180) {
            str = "logo_l.png";
        }
        Bitmap a2 = com.baidu.platform.comapi.commonutils.a.a(str, context);
        if (densityDpi > 480) {
            Matrix matrix = new Matrix();
            matrix.postScale(2.0f, 2.0f);
            this.h = Bitmap.createBitmap(a2, 0, 0, a2.getWidth(), a2.getHeight(), matrix, true);
        } else if (densityDpi <= 320 || densityDpi > 480) {
            this.h = a2;
        } else {
            Matrix matrix2 = new Matrix();
            matrix2.postScale(1.5f, 1.5f);
            this.h = Bitmap.createBitmap(a2, 0, 0, a2.getWidth(), a2.getHeight(), matrix2, true);
        }
        if (this.h != null) {
            this.g = new ImageView(context);
            this.g.setImageBitmap(this.h);
            addView(this.g);
        }
    }

    private void d() {
        if (this.e != null) {
            this.e.b();
        }
    }

    private void d(Context context) {
        this.i = new an(context, true);
        if (this.i.a()) {
            this.i.b((View.OnClickListener) new v(this));
            this.i.a((View.OnClickListener) new w(this));
            addView(this.i);
        }
    }

    private void e() {
        if (this.e != null) {
            this.e.c();
        }
    }

    private void e(Context context) {
        this.m = new RelativeLayout(context);
        this.m.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.o = new TextView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        this.o.setTextColor(Color.parseColor("#FFFFFF"));
        this.o.setTextSize(2, 11.0f);
        this.o.setTypeface(this.o.getTypeface(), 1);
        this.o.setLayoutParams(layoutParams);
        this.o.setId(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        this.m.addView(this.o);
        this.p = new TextView(context);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.width = -2;
        layoutParams2.height = -2;
        layoutParams2.addRule(14);
        this.p.setTextColor(Color.parseColor("#000000"));
        this.p.setTextSize(2, 11.0f);
        this.p.setLayoutParams(layoutParams2);
        this.m.addView(this.p);
        this.q = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.width = -2;
        layoutParams3.height = -2;
        layoutParams3.addRule(14);
        layoutParams3.addRule(3, this.o.getId());
        this.q.setLayoutParams(layoutParams3);
        Bitmap a2 = com.baidu.platform.comapi.commonutils.a.a("icon_scale.9.png", context);
        byte[] ninePatchChunk = a2.getNinePatchChunk();
        NinePatch.isNinePatchChunk(ninePatchChunk);
        this.q.setBackgroundDrawable(new NinePatchDrawable(a2, ninePatchChunk, new Rect(), (String) null));
        this.m.addView(this.q);
        addView(this.m);
    }

    public static void setCustomMapStylePath(String str) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("customMapStylePath String is illegal");
        } else if (!new File(str).exists()) {
            throw new RuntimeException("please check whether the customMapStylePath file exits");
        } else {
            c = str;
        }
    }

    public static void setIconCustom(int i2) {
        d = i2;
    }

    public static void setMapCustomEnable(boolean z2) {
        i.a(z2);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof MapViewLayoutParams) {
            super.addView(view, layoutParams);
        }
    }

    public final BaiduMap getMap() {
        this.f.c = this;
        return this.f;
    }

    public final int getMapLevel() {
        return w.get((int) this.e.a().E().a).intValue();
    }

    public int getScaleControlViewHeight() {
        return this.F;
    }

    public int getScaleControlViewWidth() {
        return this.G;
    }

    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        if (windowInsets.isRound()) {
            this.a = ScreenShape.ROUND;
        } else {
            this.a = ScreenShape.RECTANGLE;
        }
        return windowInsets;
    }

    public void onCreate(Context context, Bundle bundle) {
        if (bundle != null) {
            c = bundle.getString("customMapPath");
            if (bundle == null) {
                a(context, new BaiduMapOptions());
                return;
            }
            MapStatus mapStatus = (MapStatus) bundle.getParcelable("mapstatus");
            if (this.k != null) {
                this.k = (Point) bundle.getParcelable("scalePosition");
            }
            if (this.l != null) {
                this.l = (Point) bundle.getParcelable("zoomPosition");
            }
            this.x = bundle.getBoolean("mZoomControlEnabled");
            this.y = bundle.getBoolean("mScaleControlEnabled");
            setPadding(bundle.getInt("paddingLeft"), bundle.getInt("paddingTop"), bundle.getInt("paddingRight"), bundle.getInt("paddingBottom"));
            a(context, new BaiduMapOptions().mapStatus(mapStatus));
        }
    }

    public final void onDestroy() {
        if (this.v != null) {
            this.e.b(this.v.hashCode());
        }
        if (this.h != null && !this.h.isRecycled()) {
            this.h.recycle();
            this.h = null;
        }
        this.i.b();
        BMapManager.destroy();
        i.b();
        if (this.mTask != null) {
            this.mTask.cancel();
        }
        this.v = null;
    }

    public final void onDismiss() {
        removeAllViews();
    }

    public final void onEnterAmbient(Bundle bundle) {
        a(0);
    }

    public void onExitAmbient() {
        a(1);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                if (this.i.getVisibility() != 0) {
                    if (this.i.getVisibility() == 4) {
                        if (this.mTimer != null) {
                            if (this.mTask != null) {
                                this.mTask.cancel();
                            }
                            this.mTimer.cancel();
                            this.mTask = null;
                            this.mTimer = null;
                        }
                        a(false);
                        break;
                    }
                } else if (this.mTimer != null) {
                    if (this.mTask != null) {
                        this.mTimer.cancel();
                        this.mTask.cancel();
                    }
                    this.mTimer = null;
                    this.mTask = null;
                    break;
                }
                break;
            case 1:
                this.mTimer = new Timer();
                if (!(this.mTimer == null || this.mTask == null)) {
                    this.mTask.cancel();
                }
                this.mTask = new AnimationTask();
                this.mTimer.schedule(this.mTask, 5000);
                break;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    @TargetApi(20)
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        float f2;
        float f3;
        Point a2;
        int childCount = getChildCount();
        a((View) this.g);
        if (((getWidth() - this.B) - this.C) - this.g.getMeasuredWidth() <= 0 || ((getHeight() - this.D) - this.E) - this.g.getMeasuredHeight() <= 0) {
            this.B = 0;
            this.C = 0;
            this.E = 0;
            this.D = 0;
            f2 = 1.0f;
            f3 = 1.0f;
        } else {
            f2 = ((float) ((getWidth() - this.B) - this.C)) / ((float) getWidth());
            f3 = ((float) ((getHeight() - this.D) - this.E)) / ((float) getHeight());
        }
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt == this.e) {
                this.e.layout(0, 0, getWidth(), getHeight());
            } else if (childAt == this.g) {
                int i7 = (int) (((float) this.E) + (12.0f * f3));
                int i8 = 0;
                int i9 = 0;
                if (this.a == ScreenShape.ROUND) {
                    a((View) this.i);
                    int i10 = r / 2;
                    i9 = a(i10, this.i.getMeasuredWidth() / 2);
                    i8 = ((r / 2) - a(i10, i10 - i9)) + t;
                }
                int i11 = (s - i9) - i7;
                int i12 = r - i8;
                this.g.layout(i12 - this.g.getMeasuredWidth(), i11 - this.g.getMeasuredHeight(), i12, i11);
            } else if (childAt == this.i) {
                if (this.i.a()) {
                    a((View) this.i);
                    if (this.l == null) {
                        int i13 = 0;
                        if (this.a == ScreenShape.ROUND) {
                            i13 = a(s / 2, this.i.getMeasuredWidth() / 2);
                        }
                        int i14 = (int) (((float) i13) + (12.0f * f3) + ((float) this.D));
                        int measuredWidth = (r - this.i.getMeasuredWidth()) / 2;
                        this.i.layout(measuredWidth, i14, this.i.getMeasuredWidth() + measuredWidth, this.i.getMeasuredHeight() + i14);
                    } else {
                        this.i.layout(this.l.x, this.l.y, this.l.x + this.i.getMeasuredWidth(), this.l.y + this.i.getMeasuredHeight());
                    }
                }
            } else if (childAt == this.m) {
                int i15 = 0;
                int i16 = 0;
                if (this.a == ScreenShape.ROUND) {
                    a((View) this.i);
                    int i17 = r / 2;
                    i16 = a(i17, this.i.getMeasuredWidth() / 2);
                    i15 = ((r / 2) - a(i17, i17 - i16)) + t;
                }
                a((View) this.m);
                if (this.k == null) {
                    this.G = this.m.getMeasuredWidth();
                    this.F = this.m.getMeasuredHeight();
                    int i18 = (int) (((float) i15) + ((float) this.B) + (5.0f * f2));
                    int i19 = (s - ((int) (((float) this.E) + (12.0f * f3)))) - i16;
                    this.m.layout(i18, i19 - this.m.getMeasuredHeight(), this.G + i18, i19);
                } else {
                    this.m.layout(this.k.x, this.k.y, this.k.x + this.m.getMeasuredWidth(), this.k.y + this.m.getMeasuredHeight());
                }
            } else if (childAt == this.n) {
                a((View) this.n);
                this.n.layout(0, 0, this.n.getMeasuredWidth(), s);
            } else {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof MapViewLayoutParams) {
                    MapViewLayoutParams mapViewLayoutParams = (MapViewLayoutParams) layoutParams;
                    if (mapViewLayoutParams.c == MapViewLayoutParams.ELayoutMode.absoluteMode) {
                        a2 = mapViewLayoutParams.b;
                    } else {
                        a2 = this.e.a().a(CoordUtil.ll2mc(mapViewLayoutParams.a));
                    }
                    a(childAt);
                    int measuredWidth2 = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    float f4 = mapViewLayoutParams.d;
                    float f5 = mapViewLayoutParams.e;
                    int i20 = (int) (((float) a2.x) - (f4 * ((float) measuredWidth2)));
                    int i21 = mapViewLayoutParams.f + ((int) (((float) a2.y) - (f5 * ((float) measuredHeight))));
                    childAt.layout(i20, i21, i20 + measuredWidth2, i21 + measuredHeight);
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null && this.f != null) {
            bundle.putParcelable("mapstatus", this.f.getMapStatus());
            if (this.k != null) {
                bundle.putParcelable("scalePosition", this.k);
            }
            if (this.l != null) {
                bundle.putParcelable("zoomPosition", this.l);
            }
            bundle.putBoolean("mZoomControlEnabled", this.x);
            bundle.putBoolean("mScaleControlEnabled", this.y);
            bundle.putInt("paddingLeft", this.B);
            bundle.putInt("paddingTop", this.D);
            bundle.putInt("paddingRight", this.C);
            bundle.putInt("paddingBottom", this.E);
            bundle.putString("customMapPath", c);
        }
    }

    public void removeView(View view) {
        if (view != this.g) {
            super.removeView(view);
        }
    }

    public void setOnDismissCallbackListener(OnDismissCallback onDismissCallback) {
        if (this.n != null) {
            this.n.setCallback(onDismissCallback);
        }
    }

    public void setPadding(int i2, int i3, int i4, int i5) {
        this.B = i2;
        this.D = i3;
        this.C = i4;
        this.E = i5;
    }

    public void setScaleControlPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.k = point;
            requestLayout();
        }
    }

    public void setShape(ScreenShape screenShape) {
        this.a = screenShape;
    }

    public void setViewAnimitionEnable(boolean z2) {
        this.j = z2;
    }

    public void setZoomControlsPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.l = point;
            requestLayout();
        }
    }

    public void showScaleControl(boolean z2) {
        this.m.setVisibility(z2 ? 0 : 8);
        this.y = z2;
    }

    public void showZoomControls(boolean z2) {
        if (this.i.a()) {
            this.i.setVisibility(z2 ? 0 : 8);
            this.x = z2;
        }
    }
}
