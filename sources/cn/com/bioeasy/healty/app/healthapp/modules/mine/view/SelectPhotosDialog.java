package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.content.Context;
import android.view.View;
import butterknife.OnClick;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BaseDialog;

public class SelectPhotosDialog extends BaseDialog {
    public static Binding bindings;
    Context cxt;

    public interface Binding {
        void bindType(int i);
    }

    public static void setImg(Binding binding) {
        bindings = binding;
    }

    public SelectPhotosDialog(Context context) {
        super(context, R.style.my_dialog);
        this.cxt = context;
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.dialog_select_pic;
    }

    /* access modifiers changed from: protected */
    public void initView(View view, Context context) {
    }

    @OnClick({2131231227, 2131230923, 2131231228})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                return;
            case R.id.tv_album:
                dealAlbum();
                return;
            case R.id.tv_carmer:
                dealCarmer();
                return;
            default:
                return;
        }
    }

    private void dealCarmer() {
        if (bindings != null) {
            bindings.bindType(0);
        }
        dismiss();
    }

    private void dealAlbum() {
        if (bindings != null) {
            bindings.bindType(1);
        }
        dismiss();
    }
}
