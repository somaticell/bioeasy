package com.mob.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.mob.tools.utils.ReflectHelper;
import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class FakeActivity {
    private static Class<? extends Activity> shellClass;
    /* access modifiers changed from: protected */
    public Activity activity;
    private View contentView;
    private HashMap<String, Object> result;
    private FakeActivity resultReceiver;

    public static void setShell(Class<? extends Activity> shellClass2) {
        shellClass = shellClass2;
    }

    public static void registerExecutor(String scheme, Object executor) {
        if (shellClass != null) {
            try {
                Method registerExecutor = shellClass.getMethod("registerExecutor", new Class[]{String.class, Object.class});
                registerExecutor.setAccessible(true);
                registerExecutor.invoke((Object) null, new Object[]{scheme, executor});
            } catch (Throwable t) {
                MobLog.getInstance().w(t);
            }
        } else {
            MobUIShell.registerExecutor(scheme, executor);
        }
    }

    public void setActivity(Activity activity2) {
        this.activity = activity2;
    }

    /* access modifiers changed from: protected */
    public boolean disableScreenCapture() {
        if (this.activity == null || Build.VERSION.SDK_INT < 11) {
            return false;
        }
        this.activity.getWindow().setFlags(8192, 8192);
        return true;
    }

    public void setContentViewLayoutResName(String name) {
        int resId;
        if (this.activity != null && (resId = ResHelper.getLayoutRes(this.activity, name)) > 0) {
            this.activity.setContentView(resId);
        }
    }

    public void setContentView(View view) {
        this.contentView = view;
    }

    public View getContentView() {
        return this.contentView;
    }

    public <T extends View> T findViewById(int id) {
        if (this.activity == null) {
            return null;
        }
        return this.activity.findViewById(id);
    }

    public <T extends View> T findViewByResName(View view, String name) {
        int resId;
        if (this.activity != null && (resId = ResHelper.getIdRes(this.activity, name)) > 0) {
            return view.findViewById(resId);
        }
        return null;
    }

    public <T extends View> T findViewByResName(String name) {
        int resId;
        if (this.activity != null && (resId = ResHelper.getIdRes(this.activity, name)) > 0) {
            return findViewById(resId);
        }
        return null;
    }

    public void onCreate() {
    }

    public void onNewIntent(Intent intent) {
    }

    public void onStart() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStop() {
    }

    public void onRestart() {
    }

    public boolean onFinish() {
        return false;
    }

    public void onDestroy() {
    }

    public final void finish() {
        if (this.activity != null) {
            this.activity.finish();
        }
    }

    public boolean onKeyEvent(int keyCode, KeyEvent event) {
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void startActivity(Intent intent) {
        startActivityForResult(intent, -1);
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        if (this.activity != null) {
            this.activity.startActivityForResult(intent, requestCode);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    }

    public Context getContext() {
        return this.activity;
    }

    public void show(Context context, Intent i) {
        showForResult(context, i, (FakeActivity) null);
    }

    public void show(Context context, Intent i, boolean forceNewTask) {
        showForResult(context, i, (FakeActivity) null, forceNewTask);
    }

    public void showForResult(Context context, Intent i, FakeActivity resultReceiver2) {
        showForResult(context, i, resultReceiver2, false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showForResult(android.content.Context r13, android.content.Intent r14, com.mob.tools.FakeActivity r15, boolean r16) {
        /*
            r12 = this;
            r12.resultReceiver = r15
            android.os.Message r4 = new android.os.Message
            r4.<init>()
            r3 = 0
            java.lang.Class<? extends android.app.Activity> r7 = shellClass
            if (r7 == 0) goto L_0x007f
            android.content.Intent r2 = new android.content.Intent
            java.lang.Class<? extends android.app.Activity> r7 = shellClass
            r2.<init>(r13, r7)
            java.lang.Class<? extends android.app.Activity> r7 = shellClass     // Catch:{ Throwable -> 0x0076 }
            java.lang.String r8 = "registerExecutor"
            r9 = 1
            java.lang.Class[] r9 = new java.lang.Class[r9]     // Catch:{ Throwable -> 0x0076 }
            r10 = 0
            java.lang.Class<java.lang.Object> r11 = java.lang.Object.class
            r9[r10] = r11     // Catch:{ Throwable -> 0x0076 }
            java.lang.reflect.Method r5 = r7.getMethod(r8, r9)     // Catch:{ Throwable -> 0x0076 }
            r7 = 1
            r5.setAccessible(r7)     // Catch:{ Throwable -> 0x0076 }
            r7 = 0
            r8 = 1
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x0076 }
            r9 = 0
            r8[r9] = r12     // Catch:{ Throwable -> 0x0076 }
            java.lang.Object r7 = r5.invoke(r7, r8)     // Catch:{ Throwable -> 0x0076 }
            r0 = r7
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x0076 }
            r3 = r0
        L_0x0036:
            java.lang.String r7 = "launch_time"
            r2.putExtra(r7, r3)
            java.lang.String r7 = "executor_name"
            java.lang.Class r8 = r12.getClass()
            java.lang.String r8 = r8.getName()
            r2.putExtra(r7, r8)
            if (r14 == 0) goto L_0x004d
            r2.putExtras(r14)
        L_0x004d:
            r7 = 2
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r8 = 0
            r7[r8] = r13
            r8 = 1
            r7[r8] = r2
            r4.obj = r7
            java.lang.Thread r7 = java.lang.Thread.currentThread()
            long r8 = r7.getId()
            android.os.Looper r7 = android.os.Looper.getMainLooper()
            java.lang.Thread r7 = r7.getThread()
            long r10 = r7.getId()
            int r7 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r7 != 0) goto L_0x008b
            r0 = r16
            r12.showActivity(r0, r4)
        L_0x0075:
            return
        L_0x0076:
            r6 = move-exception
            com.mob.tools.log.NLog r7 = com.mob.tools.MobLog.getInstance()
            r7.w(r6)
            goto L_0x0036
        L_0x007f:
            android.content.Intent r2 = new android.content.Intent
            java.lang.Class<com.mob.tools.MobUIShell> r7 = com.mob.tools.MobUIShell.class
            r2.<init>(r13, r7)
            java.lang.String r3 = com.mob.tools.MobUIShell.registerExecutor(r12)
            goto L_0x0036
        L_0x008b:
            com.mob.tools.FakeActivity$1 r7 = new com.mob.tools.FakeActivity$1
            r0 = r16
            r7.<init>(r0)
            com.mob.tools.utils.UIHandler.sendMessage(r4, r7)
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.tools.FakeActivity.showForResult(android.content.Context, android.content.Intent, com.mob.tools.FakeActivity, boolean):void");
    }

    /* access modifiers changed from: private */
    public void showActivity(boolean forceNewTask, Message msg) {
        Object[] objs = (Object[]) msg.obj;
        Context cxt = (Context) objs[0];
        Intent i = (Intent) objs[1];
        if (forceNewTask) {
            i.addFlags(268435456);
            i.addFlags(134217728);
        } else if (!(cxt instanceof Activity)) {
            i.addFlags(268435456);
        }
        cxt.startActivity(i);
    }

    public FakeActivity getParent() {
        return this.resultReceiver;
    }

    public final void setResult(HashMap<String, Object> result2) {
        this.result = result2;
    }

    public void sendResult() {
        if (this.resultReceiver != null) {
            this.resultReceiver.onResult(this.result);
        }
    }

    public void onResult(HashMap<String, Object> hashMap) {
    }

    public void runOnUIThread(final Runnable r) {
        UIHandler.sendEmptyMessage(0, new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                r.run();
                return false;
            }
        });
    }

    public void runOnUIThread(final Runnable r, long delayMillis) {
        UIHandler.sendEmptyMessageDelayed(0, delayMillis, new Handler.Callback() {
            public boolean handleMessage(Message msg) {
                r.run();
                return false;
            }
        });
    }

    public void setRequestedOrientation(int requestedOrientation) {
        if (this.activity != null) {
            this.activity.setRequestedOrientation(requestedOrientation);
        }
    }

    public void requestPortraitOrientation() {
        setRequestedOrientation(1);
    }

    public void requestLandscapeOrientation() {
        setRequestedOrientation(0);
    }

    public int getOrientation() {
        return this.activity.getResources().getConfiguration().orientation;
    }

    public void requestFullScreen(boolean fullScreen) {
        if (this.activity != null) {
            if (fullScreen) {
                this.activity.getWindow().addFlags(1024);
                this.activity.getWindow().clearFlags(2048);
            } else {
                this.activity.getWindow().addFlags(2048);
                this.activity.getWindow().clearFlags(1024);
            }
            this.activity.getWindow().getDecorView().requestLayout();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void requestPermissions(String[] permissions, int requestCode) {
        if (this.activity != null && Build.VERSION.SDK_INT >= 23) {
            try {
                ReflectHelper.invokeInstanceMethod(this.activity, "requestPermissions", permissions, Integer.valueOf(requestCode));
            } catch (Throwable t) {
                MobLog.getInstance().d(t);
            }
        }
    }
}
