package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AddressBean;
import java.util.List;

public interface AddressView extends BaseView {
    void updateAddressStatus(int i, int i2);

    void updateUserAddressList(List<AddressBean> list);
}
