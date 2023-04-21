package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.app.widgets.CircleImageView;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.SelectPhotosDialog;
import cn.com.bioeasy.healty.app.healthapp.modules.user.MainPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.user.bean.UserInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.user.view.ForgetPwdActivity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Inject;
import org.achartengine.ChartFactory;

public class InfoActivity extends BIBaseActivity implements BaseView {
    private String HEAD_PATH = "/sdcard/headPhoto";
    private SelectPhotosDialog dialog;
    @Bind({2131230879})
    CircleImageView mCvHead;
    @Bind({2131230971})
    TextView mTvNickName;
    @Bind({2131231008})
    TextView mTvTitle;
    @Inject
    MainPresenter mainPresenter;
    private Bitmap myBitmap;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.mainPresenter;
    }

    @OnClick({2131231050, 2131230970, 2131230969, 2131230972, 2131230974, 2131230975, 2131230976})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.rl_header:
                this.dialog = new SelectPhotosDialog(this);
                this.dialog.showDialog();
                return;
            case R.id.rl_nickname:
                switchPage(EditNickNameActivity.class);
                return;
            case R.id.rl_bind_phone:
                switchPage(BindingPhoneActivity.class);
                return;
            case R.id.rl_bind_other:
                switchPage(BindOtherActivity.class);
                return;
            case R.id.rl_edit_pwd:
                Intent pwdIntent = new Intent(this, ForgetPwdActivity.class);
                pwdIntent.putExtra(ChartFactory.TITLE, getString(R.string.edit_pwd));
                startActivity(pwdIntent);
                return;
            case R.id.bt_exit_count:
                if (HealthApp.x.isLogin()) {
                    this.mainPresenter.logout(new BasePresenter.ICallback<Integer>() {
                        public void onResult(Integer result) {
                            if (result.intValue() == 0) {
                                InfoActivity.this.finish();
                            } else {
                                InfoActivity.this.showMessage((int) R.string.exit_logon_failure);
                            }
                        }
                    });
                    return;
                }
                return;
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_info;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.mTvTitle.setText(getResources().getString(R.string.info));
        SelectPhotosDialog.setImg(new SelectPhotosDialog.Binding() {
            public void bindType(int type) {
                if (type == 1) {
                    InfoActivity.this.dealAlbum(1);
                } else {
                    InfoActivity.this.dealCamera(0);
                }
            }
        });
        this.myBitmap = BitmapFactory.decodeFile(this.HEAD_PATH + "/head.jpg");
        if (this.myBitmap != null) {
            RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), this.myBitmap);
            circularBitmapDrawable.setCornerRadius(getResources().getDimension(R.dimen.head_corner_35));
            this.mCvHead.setImageDrawable(circularBitmapDrawable);
        }
        UserInfo userInfo = HealthApp.x.getUserInfo();
        if (userInfo != null && !StringUtil.isNullOrEmpty(userInfo.getNickName())) {
            this.mTvNickName.setText(userInfo.getNickName());
        }
    }

    /* access modifiers changed from: private */
    public void dealCamera(int i) {
        Intent intent_pat = new Intent("android.media.action.IMAGE_CAPTURE");
        intent_pat.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
        startActivityForResult(intent_pat, i);
    }

    /* access modifiers changed from: private */
    public void dealAlbum(int i) {
        Intent intent_photo = new Intent("android.intent.action.PICK", (Uri) null);
        intent_photo.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent_photo, i);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == -1) {
                    shearPhoto(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/head.jpg")));
                    return;
                }
                return;
            case 1:
                if (resultCode == -1) {
                    shearPhoto(data.getData());
                    return;
                }
                return;
            case 3:
                if (data != null) {
                    this.myBitmap = (Bitmap) data.getExtras().getParcelable("data");
                    if (this.myBitmap != null) {
                        setPictureToSD(this.myBitmap);
                        this.mCvHead.setImageBitmap(this.myBitmap);
                        return;
                    }
                    return;
                }
                return;
            case 103:
                onCaptureImageResult(data);
                return;
            case 104:
                onSelectFromGalleryResult(data);
                return;
            default:
                return;
        }
    }

    private void setPictureToSD(Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            FileOutputStream fos = null;
            File file = new File(this.HEAD_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                FileOutputStream fos2 = new FileOutputStream(this.HEAD_PATH + "/head.jpg");
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos2);
                    try {
                        fos2.flush();
                        fos2.close();
                        FileOutputStream fileOutputStream = fos2;
                    } catch (IOException e) {
                        e.printStackTrace();
                        FileOutputStream fileOutputStream2 = fos2;
                    }
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fos = fos2;
                    try {
                        e.printStackTrace();
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    } catch (Throwable th) {
                        th = th;
                        try {
                            fos.flush();
                            fos.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fos = fos2;
                    fos.flush();
                    fos.close();
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                e.printStackTrace();
                fos.flush();
                fos.close();
            }
        }
    }

    private void shearPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            this.mCvHead.setImageBitmap((Bitmap) extras.getParcelable("data"));
        }
    }

    private void onCaptureImageResult(Intent data) {
        if (data.getExtras() != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().getParcelable("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            try {
                destination.createNewFile();
                FileOutputStream fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.mCvHead.setImageBitmap(thumbnail);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }
}
