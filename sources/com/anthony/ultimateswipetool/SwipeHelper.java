package com.anthony.ultimateswipetool;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.anthony.ultimateswipetool.activity.SwipeBackLayout;
import java.lang.reflect.Method;

public class SwipeHelper {
    /* access modifiers changed from: private */
    public Activity mActivity;
    private SwipeBackLayout mSwipeBackLayout;

    public SwipeHelper(Activity activity) {
        this.mActivity = activity;
    }

    public static void replaceContentView(Window window, ViewGroup newContentView) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View content = decorView.getChildAt(0);
        decorView.removeView(content);
        newContentView.addView(content);
        decorView.addView(newContentView);
    }

    public static abstract class AnimationEndListener implements Animator.AnimatorListener {
        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    public void onActivityCreate() {
        this.mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mActivity.getWindow().getDecorView().setBackgroundDrawable((Drawable) null);
        this.mSwipeBackLayout = new SwipeBackLayout(this.mActivity);
        this.mSwipeBackLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            public void onScrollStateChange(int state, float scrollPercent) {
            }

            public void onEdgeTouch(int edgeFlag) {
                SwipeHelper.convertActivityToTranslucent(SwipeHelper.this.mActivity);
            }

            public void onScrollOverThreshold() {
            }
        });
    }

    public void onPostCreate() {
        this.mSwipeBackLayout.attachToActivity(this.mActivity);
    }

    public View findViewById(int id) {
        if (this.mSwipeBackLayout != null) {
            return this.mSwipeBackLayout.findViewById(id);
        }
        return null;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mSwipeBackLayout;
    }

    public static void convertActivityFromTranslucent(Activity activity) {
        try {
            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent", new Class[0]);
            method.setAccessible(true);
            method.invoke(activity, new Object[0]);
        } catch (Throwable th) {
        }
    }

    public static void convertActivityToTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            convertActivityToTranslucentAfterL(activity);
        } else {
            convertActivityToTranslucentBeforeL(activity);
        }
    }

    public static void convertActivityToTranslucentBeforeL(Activity activity) {
        try {
            Class<?> translucentConversionListenerClazz = null;
            for (Class<?> clazz : Activity.class.getDeclaredClasses()) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method method = Activity.class.getDeclaredMethod("convertToTranslucent", new Class[]{translucentConversionListenerClazz});
            method.setAccessible(true);
            method.invoke(activity, new Object[]{null});
        } catch (Throwable th) {
        }
    }

    private static void convertActivityToTranslucentAfterL(Activity activity) {
        try {
            Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions", new Class[0]);
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity, new Object[0]);
            Class<?> translucentConversionListenerClazz = null;
            for (Class<?> clazz : Activity.class.getDeclaredClasses()) {
                if (clazz.getSimpleName().contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent", new Class[]{translucentConversionListenerClazz, ActivityOptions.class});
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, new Object[]{null, options});
        } catch (Throwable th) {
        }
    }
}
