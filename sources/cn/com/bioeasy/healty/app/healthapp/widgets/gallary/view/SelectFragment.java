package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.device.DpUtil;
import cn.com.bioeasy.app.gallery.Image;
import cn.com.bioeasy.app.gallery.ImageFolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter.BaseRecyclerAdapter;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter.ImageAdapter;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.adapter.ImageFolderAdapter;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.modal.SelectImageContract;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.modal.SelectOptions;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.util.Util;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.ImageFolderPopupWindow;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.widget.SpaceGridItemDecoration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectFragment extends BIBaseFragment implements SelectImageContract.View, View.OnClickListener, ImageLoaderListener, BaseRecyclerAdapter.OnItemClickListener {
    /* access modifiers changed from: private */
    public static SelectOptions mOption;
    /* access modifiers changed from: private */
    public String mCamImageName;
    @Bind({2131231317})
    RecyclerView mContentView;
    private LoaderListener mCursorLoader = new LoaderListener();
    @Bind({2131231320})
    Button mDoneView;
    private ImageFolderPopupWindow mFolderPopupWindow;
    private ImageAdapter mImageAdapter;
    /* access modifiers changed from: private */
    public ImageFolderAdapter mImageFolderAdapter;
    private SelectImageContract.Operator mOperator;
    @Bind({2131231319})
    Button mPreviewView;
    @Bind({2131231315})
    ImageView mSelectFolderIcon;
    @Bind({2131231316})
    Button mSelectFolderView;
    /* access modifiers changed from: private */
    public List<Image> mSelectedImage;
    @Bind({2131231173})
    View mToolbar;

    public static SelectFragment newInstance(SelectOptions options) {
        SelectFragment fragment = new SelectFragment();
        mOption = options;
        return fragment;
    }

    public void onAttach(Context context) {
        this.mOperator = (SelectImageContract.Operator) context;
        this.mOperator.setDataView(this);
        super.onAttach(context);
    }

    @OnClick({2131231319, 2131231314, 2131231316, 2131231320})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_back:
                getActivity().finish();
                return;
            case R.id.btn_title_select:
                showPopupFolderList();
                return;
            case R.id.btn_preview:
                if (this.mSelectedImage.size() > 0) {
                    ImageGalleryActivity.show((Context) getActivity(), Util.toArray(this.mSelectedImage), 0, false);
                    return;
                }
                return;
            case R.id.btn_done:
                onSelectComplete();
                return;
            default:
                return;
        }
    }

    public void onItemClick(int position, long itemId) {
        if (!mOption.isHasCam()) {
            handleSelectChange(position);
        } else if (position != 0) {
            handleSelectChange(position);
        } else if (this.mSelectedImage.size() < mOption.getSelectCount()) {
            this.mOperator.requestCamera();
        } else {
            Toast.makeText(getActivity(), getString(R.string.best_choice) + mOption.getSelectCount() + getString(R.string.a_few_pictures), 0).show();
        }
    }

    /* access modifiers changed from: private */
    public void handleSelectSizeChange(int size) {
        if (size > 0) {
            this.mPreviewView.setEnabled(true);
            this.mDoneView.setEnabled(true);
            this.mDoneView.setText(String.format("%s(%s)", new Object[]{getText(R.string.image_select_opt_done), Integer.valueOf(size)}));
            return;
        }
        this.mPreviewView.setEnabled(false);
        this.mDoneView.setEnabled(false);
        this.mDoneView.setText(getText(R.string.image_select_opt_done));
    }

    private void handleSelectChange(int position) {
        Image image = (Image) this.mImageAdapter.getItem(position);
        int selectCount = mOption.getSelectCount();
        if (selectCount > 1) {
            if (image.isSelect()) {
                image.setSelect(false);
                this.mSelectedImage.remove(image);
                this.mImageAdapter.updateItem(position);
            } else if (this.mSelectedImage.size() == selectCount) {
                Toast.makeText(getActivity(), getString(R.string.best_choice) + selectCount + getString(R.string.a_few_pictures), 0).show();
            } else {
                image.setSelect(true);
                this.mSelectedImage.add(image);
                this.mImageAdapter.updateItem(position);
            }
            handleSelectSizeChange(this.mSelectedImage.size());
            return;
        }
        this.mSelectedImage.add(image);
        handleResult();
    }

    /* access modifiers changed from: private */
    public void handleResult() {
        if (this.mSelectedImage.size() != 0) {
            Intent intent = new Intent();
            intent.setAction(ActionConstant.ACTION_UPLOAD);
            intent.putExtra("selected", this.mSelectedImage.size());
            getActivity().sendBroadcast(intent);
            if (mOption.isCrop()) {
                List<String> selectedImage = mOption.getSelectedImages();
                selectedImage.clear();
                selectedImage.add(this.mSelectedImage.get(0).getPath());
                this.mSelectedImage.clear();
                CropActivity.show(this, mOption);
                return;
            }
            mOption.getCallback().doSelected(Util.toArray(this.mSelectedImage));
            getActivity().finish();
        }
    }

    public void onSelectComplete() {
        handleResult();
    }

    public void onOpenCameraSuccess() {
        toOpenCamera();
    }

    public void onCameraPermissionDenied() {
    }

    private void showPopupFolderList() {
        if (this.mFolderPopupWindow == null) {
            ImageFolderPopupWindow popupWindow = new ImageFolderPopupWindow(getActivity(), new ImageFolderPopupWindow.Callback() {
                public void onSelect(ImageFolderPopupWindow popupWindow, ImageFolder model) {
                    SelectFragment.this.addImagesToAdapter(model.getImages());
                    SelectFragment.this.mContentView.scrollToPosition(0);
                    popupWindow.dismiss();
                    SelectFragment.this.mSelectFolderView.setText(model.getName());
                }

                public void onDismiss() {
                    SelectFragment.this.mSelectFolderIcon.setImageResource(R.drawable.ic_arrow_bottom);
                }

                public void onShow() {
                    SelectFragment.this.mSelectFolderIcon.setImageResource(R.drawable.ic_arrow_top);
                }
            });
            popupWindow.setAdapter(this.mImageFolderAdapter);
            this.mFolderPopupWindow = popupWindow;
        }
        this.mFolderPopupWindow.showAsDropDown(this.mToolbar);
    }

    private void toOpenCamera() {
        this.mCamImageName = null;
        String savePath = "";
        if (Util.hasSDCard()) {
            savePath = Util.getCameraPath();
            File saveDir = new File(savePath);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
        }
        if (TextUtils.isEmpty(savePath)) {
            Toast.makeText(getActivity(), getString(R.string.unable_save_photo), 1).show();
            return;
        }
        this.mCamImageName = Util.getSaveImageFullName();
        File out = new File(savePath, this.mCamImageName);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", Uri.fromFile(out));
        startActivityForResult(intent, 3);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case 3:
                    if (this.mCamImageName != null) {
                        getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(Util.getCameraPath() + this.mCamImageName))));
                        return;
                    }
                    return;
                case 4:
                    if (data != null) {
                        mOption.getCallback().doSelected(new String[]{data.getStringExtra("crop_path")});
                        getActivity().finish();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void displayImage(ImageView iv, String path) {
        getImgLoader().load(path).asBitmap().centerCrop().into(iv);
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    private class LoaderListener implements LoaderManager.LoaderCallbacks<Cursor> {
        private final String[] IMAGE_PROJECTION;

        private LoaderListener() {
            this.IMAGE_PROJECTION = new String[]{"_data", "_display_name", "date_added", "_id", "mini_thumb_magic", "bucket_display_name"};
        }

        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == 0) {
                return new CursorLoader(SelectFragment.this.getContext(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.IMAGE_PROJECTION, (String) null, (String[]) null, this.IMAGE_PROJECTION[2] + " DESC");
            }
            return null;
        }

        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
                ArrayList<Image> images = new ArrayList<>();
                List<ImageFolder> imageFolders = new ArrayList<>();
                ImageFolder defaultFolder = new ImageFolder();
                defaultFolder.setName(SelectFragment.this.getString(R.string.all_photos));
                defaultFolder.setPath("");
                imageFolders.add(defaultFolder);
                if (data.getCount() > 0) {
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(this.IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(this.IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(this.IMAGE_PROJECTION[2]));
                        int id = data.getInt(data.getColumnIndexOrThrow(this.IMAGE_PROJECTION[3]));
                        String thumbPath = data.getString(data.getColumnIndexOrThrow(this.IMAGE_PROJECTION[4]));
                        String bucket = data.getString(data.getColumnIndexOrThrow(this.IMAGE_PROJECTION[5]));
                        Image image = new Image();
                        image.setPath(path);
                        image.setName(name);
                        image.setDate(dateTime);
                        image.setId(id);
                        image.setThumbPath(thumbPath);
                        image.setFolderName(bucket);
                        images.add(image);
                        if (SelectFragment.this.mCamImageName != null && SelectFragment.this.mCamImageName.equals(image.getName())) {
                            image.setSelect(true);
                            SelectFragment.this.mSelectedImage.add(image);
                        }
                        if (SelectFragment.this.mSelectedImage.size() > 0) {
                            for (Image i : SelectFragment.this.mSelectedImage) {
                                if (i.getPath().equals(image.getPath())) {
                                    image.setSelect(true);
                                }
                            }
                        }
                        File folderFile = new File(path).getParentFile();
                        ImageFolder folder = new ImageFolder();
                        folder.setName(folderFile.getName());
                        folder.setPath(folderFile.getAbsolutePath());
                        if (!imageFolders.contains(folder)) {
                            folder.getImages().add(image);
                            folder.setAlbumPath(image.getPath());
                            imageFolders.add(folder);
                        } else {
                            imageFolders.get(imageFolders.indexOf(folder)).getImages().add(image);
                        }
                    } while (data.moveToNext());
                }
                SelectFragment.this.addImagesToAdapter(images);
                defaultFolder.getImages().addAll(images);
                if (SelectFragment.mOption.isHasCam()) {
                    defaultFolder.setAlbumPath(images.size() > 1 ? images.get(1).getPath() : null);
                } else {
                    defaultFolder.setAlbumPath(images.size() > 0 ? images.get(0).getPath() : null);
                }
                SelectFragment.this.mImageFolderAdapter.resetItem(imageFolders);
                if (SelectFragment.this.mSelectedImage.size() > 0) {
                    ArrayList arrayList = new ArrayList();
                    for (Image i2 : SelectFragment.this.mSelectedImage) {
                        if (!new File(i2.getPath()).exists()) {
                            arrayList.add(i2);
                        }
                    }
                    SelectFragment.this.mSelectedImage.removeAll(arrayList);
                }
                if (SelectFragment.mOption.getSelectCount() == 1 && SelectFragment.this.mCamImageName != null) {
                    SelectFragment.this.handleResult();
                }
                SelectFragment.this.handleSelectSizeChange(SelectFragment.this.mSelectedImage.size());
            }
        }

        public void onLoaderReset(Loader<Cursor> loader) {
        }
    }

    /* access modifiers changed from: private */
    public void addImagesToAdapter(List<Image> images) {
        this.mImageAdapter.clear();
        if (mOption.isHasCam()) {
            this.mImageAdapter.addItem(new Image());
        }
        this.mImageAdapter.addAll(images);
    }

    public BasePresenter getPresenter() {
        return null;
    }

    public void onDestroy() {
        mOption = null;
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.fragment_select_image;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        this.mContentView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        this.mContentView.addItemDecoration(new SpaceGridItemDecoration(DpUtil.dp2px(getContext(), 1.0f)));
        this.mImageAdapter = new ImageAdapter(getContext(), this);
        this.mImageFolderAdapter = new ImageFolderAdapter(getActivity());
        this.mImageFolderAdapter.setLoader(this);
        this.mContentView.setAdapter(this.mImageAdapter);
        this.mContentView.setItemAnimator((RecyclerView.ItemAnimator) null);
        this.mImageAdapter.setOnItemClickListener(this);
        initData();
    }

    /* access modifiers changed from: protected */
    public void initData() {
        this.mSelectedImage = new ArrayList();
        if (mOption.getSelectCount() > 1 && mOption.getSelectedImages() != null) {
            for (String s : mOption.getSelectedImages()) {
                if (s != null && new File(s).exists()) {
                    Image image = new Image();
                    image.setSelect(true);
                    image.setPath(s);
                    this.mSelectedImage.add(image);
                }
            }
        }
        getLoaderManager().initLoader(0, (Bundle) null, this.mCursorLoader);
    }
}
