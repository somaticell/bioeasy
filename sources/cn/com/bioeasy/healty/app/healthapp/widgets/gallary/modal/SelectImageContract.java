package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.modal;

public interface SelectImageContract {

    public interface Operator {
        void onBack();

        void requestCamera();

        void requestExternalStorage();

        void setDataView(View view);
    }

    public interface View {
        void onCameraPermissionDenied();

        void onOpenCameraSuccess();
    }
}
