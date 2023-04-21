package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.modal.SelectImageContract;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.modal.SelectOptions;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.util.DialogHelper;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SelectImageActivity extends BIBaseActivity implements EasyPermissions.PermissionCallbacks, SelectImageContract.Operator {
    public static final String KEY_CONFIG = "config";
    private static final int RC_CAMERA_PERM = 3;
    private static final int RC_EXTERNAL_STORAGE = 4;
    private static SelectOptions mOption;
    private SelectImageContract.View mView;

    public static void show(Context context, SelectOptions options) {
        mOption = options;
        context.startActivity(new Intent(context, SelectImageActivity.class));
    }

    @AfterPermissionGranted(3)
    public void requestCamera() {
        if (!EasyPermissions.hasPermissions(this, "android.permission.CAMERA")) {
            EasyPermissions.requestPermissions(this, "", 3, "android.permission.CAMERA");
        } else if (this.mView != null) {
            this.mView.onOpenCameraSuccess();
        }
    }

    @AfterPermissionGranted(4)
    public void requestExternalStorage() {
        if (!EasyPermissions.hasPermissions(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            EasyPermissions.requestPermissions(this, "", 4, "android.permission.READ_EXTERNAL_STORAGE");
        } else if (this.mView == null) {
            handleView();
        }
    }

    public void onBack() {
        onSupportNavigateUp();
    }

    public void setDataView(SelectImageContract.View view) {
        this.mView = view;
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        mOption = null;
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_select_image;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        requestExternalStorage();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public boolean shouldShowRequestPermissionRationale(String permission) {
        return false;
    }

    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    public void onPermissionsDenied(int requestCode, List<String> list) {
        if (requestCode == 4) {
            removeView();
            DialogHelper.getConfirmDialog(this, "", getString(R.string.without_permission), getString(R.string.to_set_up), getString(R.string.cancel), false, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SelectImageActivity.this.startActivity(new Intent("android.settings.APPLICATION_SETTINGS"));
                    SelectImageActivity.this.finish();
                }
            }, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SelectImageActivity.this.finish();
                }
            }).show();
            return;
        }
        if (this.mView != null) {
            this.mView.onCameraPermissionDenied();
        }
        DialogHelper.getConfirmDialog(this, "", getString(R.string.camera_privileges), getString(R.string.to_set_up), getString(R.string.cancel), false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SelectImageActivity.this.startActivity(new Intent("android.settings.APPLICATION_SETTINGS"));
            }
        }, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    private void removeView() {
        SelectImageContract.View view = this.mView;
        if (view != null) {
            try {
                getSupportFragmentManager().beginTransaction().remove((Fragment) view).commitNowAllowingStateLoss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleView() {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, SelectFragment.newInstance(mOption)).commitNowAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }
}
