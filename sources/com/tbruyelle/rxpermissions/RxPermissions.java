package com.tbruyelle.rxpermissions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class RxPermissions {
    public static final String TAG = "RxPermissions";
    static RxPermissions sSingleton;
    private Context mCtx;
    private boolean mLogging;
    private Map<String, PublishSubject<Permission>> mSubjects = new HashMap();

    public static RxPermissions getInstance(Context ctx) {
        if (sSingleton == null) {
            sSingleton = new RxPermissions(ctx.getApplicationContext());
        }
        return sSingleton;
    }

    RxPermissions(Context ctx) {
        this.mCtx = ctx;
    }

    public void setLogging(boolean logging) {
        this.mLogging = logging;
    }

    private void log(String message) {
        if (this.mLogging) {
            Log.d(TAG, message);
        }
    }

    public Observable.Transformer<Object, Boolean> ensure(final String... permissions) {
        return new Observable.Transformer<Object, Boolean>() {
            public Observable<Boolean> call(Observable<Object> o) {
                return RxPermissions.this.request(o, permissions).buffer(permissions.length).flatMap(new Func1<List<Permission>, Observable<Boolean>>() {
                    public Observable<Boolean> call(List<Permission> permissions) {
                        if (permissions.isEmpty()) {
                            return Observable.empty();
                        }
                        for (Permission p : permissions) {
                            if (!p.granted) {
                                return Observable.just(false);
                            }
                        }
                        return Observable.just(true);
                    }
                });
            }
        };
    }

    public Observable.Transformer<Object, Permission> ensureEach(final String... permissions) {
        return new Observable.Transformer<Object, Permission>() {
            public Observable<Permission> call(Observable<Object> o) {
                return RxPermissions.this.request(o, permissions);
            }
        };
    }

    public Observable<Boolean> request(String... permissions) {
        return Observable.just(null).compose(ensure(permissions));
    }

    public Observable<Permission> requestEach(String... permissions) {
        return Observable.just(null).compose(ensureEach(permissions));
    }

    /* access modifiers changed from: private */
    public Observable<Permission> request(Observable<?> trigger, final String... permissions) {
        if (permissions != null && permissions.length != 0) {
            return oneOf(trigger, pending(permissions)).flatMap(new Func1<Object, Observable<Permission>>() {
                public Observable<Permission> call(Object o) {
                    return RxPermissions.this.request_(permissions);
                }
            });
        }
        throw new IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission");
    }

    private Observable<?> pending(String... permissions) {
        for (String p : permissions) {
            if (!this.mSubjects.containsKey(p)) {
                return Observable.empty();
            }
        }
        return Observable.just(null);
    }

    private Observable<?> oneOf(Observable<?> trigger, Observable<?> pending) {
        if (trigger == null) {
            return Observable.just(null);
        }
        return Observable.merge(trigger, pending);
    }

    /* access modifiers changed from: private */
    @TargetApi(23)
    public Observable<Permission> request_(String... permissions) {
        List<Observable<Permission>> list = new ArrayList<>(permissions.length);
        List<String> unrequestedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            log("Requesting permission " + permission);
            if (isGranted(permission)) {
                list.add(Observable.just(new Permission(permission, true)));
            } else if (isRevoked(permission)) {
                list.add(Observable.just(new Permission(permission, false)));
            } else {
                PublishSubject<Permission> subject = this.mSubjects.get(permission);
                if (subject == null) {
                    unrequestedPermissions.add(permission);
                    subject = PublishSubject.create();
                    this.mSubjects.put(permission, subject);
                }
                list.add(subject);
            }
        }
        if (!unrequestedPermissions.isEmpty()) {
            startShadowActivity((String[]) unrequestedPermissions.toArray(new String[unrequestedPermissions.size()]));
        }
        return Observable.concat(Observable.from(list));
    }

    public Observable<Boolean> shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        if (!isMarshmallow()) {
            return Observable.just(false);
        }
        return Observable.just(Boolean.valueOf(shouldShowRequestPermissionRationale_(activity, permissions)));
    }

    @TargetApi(23)
    private boolean shouldShowRequestPermissionRationale_(Activity activity, String... permissions) {
        for (String p : permissions) {
            if (!isGranted(p) && !activity.shouldShowRequestPermissionRationale(p)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public void startShadowActivity(String[] permissions) {
        log("startShadowActivity " + TextUtils.join(", ", permissions));
        Intent intent = new Intent(this.mCtx, ShadowActivity.class);
        intent.putExtra("permissions", permissions);
        intent.addFlags(268435456);
        this.mCtx.startActivity(intent);
    }

    public boolean isGranted(String permission) {
        return !isMarshmallow() || isGranted_(permission);
    }

    public boolean isRevoked(String permission) {
        return isMarshmallow() && isRevoked_(permission);
    }

    /* access modifiers changed from: package-private */
    public boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= 23;
    }

    @TargetApi(23)
    private boolean isGranted_(String permission) {
        return this.mCtx.checkSelfPermission(permission) == 0;
    }

    @TargetApi(23)
    private boolean isRevoked_(String permission) {
        return this.mCtx.getPackageManager().isPermissionRevokedByPolicy(permission, this.mCtx.getPackageName());
    }

    /* access modifiers changed from: package-private */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        int size = permissions.length;
        for (int i = 0; i < size; i++) {
            log("onRequestPermissionsResult  " + permissions[i]);
            PublishSubject<Permission> subject = this.mSubjects.get(permissions[i]);
            if (subject == null) {
                throw new IllegalStateException("RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
            }
            this.mSubjects.remove(permissions[i]);
            subject.onNext(new Permission(permissions[i], grantResults[i] == 0));
            subject.onCompleted();
        }
    }
}
