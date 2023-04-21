package pub.devrel.easypermissions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EasyPermissions {
    private static final String TAG = "EasyPermissions";

    public interface PermissionCallbacks extends ActivityCompat.OnRequestPermissionsResultCallback {
        void onPermissionsDenied(int i, List<String> list);

        void onPermissionsGranted(int i, List<String> list);
    }

    public static boolean hasPermissions(Context context, String... perms) {
        boolean hasPerm;
        if (Build.VERSION.SDK_INT < 23) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");
            return true;
        }
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) == 0) {
                hasPerm = true;
            } else {
                hasPerm = false;
            }
            if (!hasPerm) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Object object, String rationale, int requestCode, String... perms) {
        requestPermissions(object, rationale, 17039370, 17039360, requestCode, perms);
    }

    public static void requestPermissions(final Object object, String rationale, @StringRes int positiveButton, @StringRes int negativeButton, final int requestCode, final String... perms) {
        checkCallingObjectSuitability(object);
        final PermissionCallbacks callbacks = (PermissionCallbacks) object;
        boolean shouldShowRationale = false;
        for (String perm : perms) {
            shouldShowRationale = shouldShowRationale || shouldShowRequestPermissionRationale(object, perm);
        }
        if (shouldShowRationale) {
            Activity activity = getActivity(object);
            if (activity != null) {
                new AlertDialog.Builder(activity).setMessage((CharSequence) rationale).setPositiveButton(positiveButton, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EasyPermissions.executePermissionsRequest(object, perms, requestCode);
                    }
                }).setNegativeButton(negativeButton, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        callbacks.onPermissionsDenied(requestCode, Arrays.asList(perms));
                    }
                }).create().show();
                return;
            }
            return;
        }
        executePermissionsRequest(object, perms, requestCode);
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, Object object) {
        checkCallingObjectSuitability(object);
        PermissionCallbacks callbacks = (PermissionCallbacks) object;
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == 0) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }
        if (!granted.isEmpty()) {
            callbacks.onPermissionsGranted(requestCode, granted);
        }
        if (!denied.isEmpty()) {
            callbacks.onPermissionsDenied(requestCode, denied);
        }
        if (!granted.isEmpty() && denied.isEmpty()) {
            runAnnotatedMethods(object, requestCode);
        }
    }

    public static boolean checkDeniedPermissionsNeverAskAgain(Object object, String rationale, @StringRes int positiveButton, @StringRes int negativeButton, List<String> deniedPerms) {
        for (String perm : deniedPerms) {
            if (!shouldShowRequestPermissionRationale(object, perm)) {
                final Activity activity = getActivity(object);
                if (activity == null) {
                    return true;
                }
                new AlertDialog.Builder(activity).setMessage((CharSequence) rationale).setPositiveButton(positiveButton, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", activity.getPackageName(), (String) null));
                        activity.startActivity(intent);
                    }
                }).setNegativeButton(negativeButton, (DialogInterface.OnClickListener) null).create().show();
                return true;
            }
        }
        return false;
    }

    @TargetApi(23)
    private static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        }
        if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        }
        if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        }
        return false;
    }

    /* access modifiers changed from: private */
    @TargetApi(23)
    public static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);
        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    @TargetApi(11)
    private static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        }
        if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        }
        if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        }
        return null;
    }

    private static void runAnnotatedMethods(Object object, int requestCode) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(AfterPermissionGranted.class) && ((AfterPermissionGranted) method.getAnnotation(AfterPermissionGranted.class)).value() == requestCode) {
                if (method.getParameterTypes().length > 0) {
                    String valueOf = String.valueOf(method.getName());
                    throw new RuntimeException(valueOf.length() != 0 ? "Cannot execute non-void method ".concat(valueOf) : new String("Cannot execute non-void method "));
                }
                try {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    method.invoke(object, new Object[0]);
                } catch (IllegalAccessException e) {
                    Log.e(TAG, "runDefaultMethod:IllegalAccessException", e);
                } catch (InvocationTargetException e2) {
                    Log.e(TAG, "runDefaultMethod:InvocationTargetException", e2);
                }
            }
        }
    }

    private static void checkCallingObjectSuitability(Object object) {
        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        boolean isMinSdkM = Build.VERSION.SDK_INT >= 23;
        if (isSupportFragment || isActivity || (isAppFragment && isMinSdkM)) {
            if (!(object instanceof PermissionCallbacks)) {
                throw new IllegalArgumentException("Caller must implement PermissionCallbacks.");
            }
        } else if (isAppFragment) {
            throw new IllegalArgumentException("Target SDK needs to be greater than 23 if caller is android.app.Fragment");
        } else {
            throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
        }
    }
}
