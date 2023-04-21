package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.modal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SelectOptions {
    /* access modifiers changed from: private */
    public boolean hasCam;
    /* access modifiers changed from: private */
    public boolean isCrop;
    /* access modifiers changed from: private */
    public Callback mCallback;
    /* access modifiers changed from: private */
    public int mCropHeight;
    /* access modifiers changed from: private */
    public int mCropWidth;
    /* access modifiers changed from: private */
    public int mSelectCount;
    /* access modifiers changed from: private */
    public List<String> mSelectedImages;

    public interface Callback {
        void doSelected(String[] strArr);
    }

    private SelectOptions() {
    }

    public boolean isCrop() {
        return this.isCrop;
    }

    public int getCropWidth() {
        return this.mCropWidth;
    }

    public int getCropHeight() {
        return this.mCropHeight;
    }

    public Callback getCallback() {
        return this.mCallback;
    }

    public boolean isHasCam() {
        return this.hasCam;
    }

    public int getSelectCount() {
        return this.mSelectCount;
    }

    public List<String> getSelectedImages() {
        return this.mSelectedImages;
    }

    public static class Builder {
        private Callback callback;
        private int cropHeight;
        private int cropWidth;
        private boolean hasCam = true;
        private boolean isCrop;
        private int selectCount = 1;
        private List<String> selectedImages = new ArrayList();

        public Builder setCrop(int cropWidth2, int cropHeight2) {
            if (cropWidth2 <= 0 || cropHeight2 <= 0) {
                throw new IllegalArgumentException("cropWidth or cropHeight mast be greater than 0 ");
            }
            this.isCrop = true;
            this.cropWidth = cropWidth2;
            this.cropHeight = cropHeight2;
            return this;
        }

        public Builder setCallback(Callback callback2) {
            this.callback = callback2;
            return this;
        }

        public Builder setHasCam(boolean hasCam2) {
            this.hasCam = hasCam2;
            return this;
        }

        public Builder setSelectCount(int selectCount2) {
            if (selectCount2 <= 0) {
                throw new IllegalArgumentException("selectCount mast be greater than 0 ");
            }
            this.selectCount = selectCount2;
            return this;
        }

        public Builder setSelectedImages(List<String> selectedImages2) {
            if (!(selectedImages2 == null || selectedImages2.size() == 0)) {
                this.selectedImages.addAll(selectedImages2);
            }
            return this;
        }

        public Builder setSelectedImages(String[] selectedImages2) {
            if (!(selectedImages2 == null || selectedImages2.length == 0)) {
                if (this.selectedImages == null) {
                    this.selectedImages = new ArrayList();
                }
                this.selectedImages.addAll(Arrays.asList(selectedImages2));
            }
            return this;
        }

        public SelectOptions build() {
            SelectOptions options = new SelectOptions();
            boolean unused = options.hasCam = this.hasCam;
            boolean unused2 = options.isCrop = this.isCrop;
            int unused3 = options.mCropHeight = this.cropHeight;
            int unused4 = options.mCropWidth = this.cropWidth;
            Callback unused5 = options.mCallback = this.callback;
            int unused6 = options.mSelectCount = this.selectCount;
            List unused7 = options.mSelectedImages = this.selectedImages;
            return options;
        }
    }
}
