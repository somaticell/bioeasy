package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.widget.Button;
import cn.com.bioeasy.app.base.BaseView;

public interface BindView extends BaseView {
    Button backBtn();

    void bindSuccess();

    String getCode();

    String getPhone();
}
