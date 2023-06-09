package com.baidu.mapapi.map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.baidu.mapapi.map.WearMapView;

public class SwipeDismissView extends RelativeLayout {
    WearMapView.OnDismissCallback a = null;

    public SwipeDismissView(Context context, AttributeSet attributeSet, int i, View view) {
        super(context, attributeSet, i);
        a(context, view);
    }

    public SwipeDismissView(Context context, AttributeSet attributeSet, View view) {
        super(context, attributeSet);
        a(context, view);
    }

    public SwipeDismissView(Context context, View view) {
        super(context);
        a(context, view);
    }

    /* access modifiers changed from: package-private */
    public void a(Context context, View view) {
        setOnTouchListener(new SwipeDismissTouchListener(view, new Object(), new p(this)));
    }

    public void setCallback(WearMapView.OnDismissCallback onDismissCallback) {
        this.a = onDismissCallback;
    }
}
