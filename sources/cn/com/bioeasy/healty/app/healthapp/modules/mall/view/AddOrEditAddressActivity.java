package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.utils.NumberUtils;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderAddressPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AddressBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AreaBean;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.SelectAreaDialog;
import com.kyleduo.switchbutton.SwitchButton;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class AddOrEditAddressActivity extends BIBaseActivity implements AddressView {
    public static final int ADDRESS_ADD = 1;
    public static final int ADDRESS_DELETE = 3;
    public static final int ADDRESS_EDIT = 2;
    /* access modifiers changed from: private */
    public AreaBean areaBean;
    SelectAreaDialog areaDialog;
    private int edit;
    private AddressBean editAddressBean;
    @Bind({2131231099})
    TextView mArea;
    @Bind({2131231102})
    SwitchButton mSb;
    @Bind({2131231101})
    TextView metDetailAddress;
    @Bind({2131231096})
    TextView metPerson;
    @Bind({2131230940})
    TextView metPhone;
    @Bind({2131231179})
    TextView mtvSave;
    @Inject
    OrderAddressPresenter orderAddressPresenter;
    @Bind({2131231008})
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.orderAddressPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_shop_addoredit_adderss;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getResources().getString(R.string.shopping_order_address_title));
        this.mtvSave.setVisibility(0);
        this.mtvSave.setText(getResources().getString(R.string.shopping_address_save));
        initEdit();
    }

    private void initEdit() {
        this.editAddressBean = (AddressBean) getIntent().getSerializableExtra(IntentActions.ADDRESS_EDIT_BEAN);
        this.edit = getIntent().getIntExtra(IntentActions.ADDRESS_ACTION, 1);
        if (this.editAddressBean != null) {
            this.metPerson.setText(this.editAddressBean.getConsignee());
            this.metPhone.setText(this.editAddressBean.getMobile());
            this.mArea.setText(this.editAddressBean.getProvince() + this.editAddressBean.getCity() + this.editAddressBean.getDistrict());
            this.metDetailAddress.setText(this.editAddressBean.getAddress());
            if (this.editAddressBean.getIsDefault() == 0) {
                this.mSb.setChecked(true);
            } else {
                this.mSb.setChecked(false);
            }
        }
    }

    @OnClick({2131231179, 2131231050, 2131231097})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.rl_select_address:
                this.areaDialog = new SelectAreaDialog(this);
                this.areaDialog.showDialog();
                this.areaDialog.obtainData(new SelectAreaDialog.SendData() {
                    public void send(AreaBean bean) {
                        if (bean != null) {
                            AreaBean unused = AddOrEditAddressActivity.this.areaBean = bean;
                            StringBuilder sb = new StringBuilder();
                            sb.append(bean.getProvice()).append(" ").append(bean.getCity()).append(" ").append(bean.getDistrict()).append(" ");
                            AddOrEditAddressActivity.this.mArea.setText(sb.toString());
                            sb.delete(0, sb.length());
                        }
                    }
                });
                return;
            case R.id.id_tv_edit_all:
                saveData();
                return;
            default:
                return;
        }
    }

    private void saveData() {
        if (!checkData()) {
            return;
        }
        if (this.edit == 1) {
            this.orderAddressPresenter.addOrderAddress(createAddressBean());
        } else {
            this.orderAddressPresenter.updateOrderAddress(createAddressBean());
        }
    }

    private AddressBean createAddressBean() {
        AddressBean addressBean = new AddressBean();
        addressBean.setConsignee(mConsignee());
        addressBean.setMobile(mMobile());
        addressBean.setAddress(mAddress());
        addressBean.setIsDefault(isDefault());
        addressBean.setCtime(new Date().getTime());
        addressBean.setProvince(mAreaBean().getProvice());
        addressBean.setCity(mAreaBean().getCity());
        addressBean.setDistrict(mAreaBean().getDistrict());
        addressBean.setId(addressId());
        return addressBean;
    }

    public boolean checkData() {
        if (this.metPerson.getText().toString().isEmpty()) {
            showMessage((int) R.string.person_not_null);
            return false;
        } else if (this.metPhone.getText().toString().isEmpty()) {
            showMessage((int) R.string.phone_not_null);
            return false;
        } else if (!NumberUtils.isMobileNO(this.metPhone.getText().toString())) {
            showMessage((int) R.string.phone_type);
            return false;
        } else if (this.mArea.getText().toString().isEmpty()) {
            showMessage((int) R.string.area_not_null);
            return false;
        } else if (!this.metDetailAddress.getText().toString().isEmpty()) {
            return true;
        } else {
            showMessage((int) R.string.detailaddress_not_null);
            return false;
        }
    }

    private String mConsignee() {
        return this.metPerson.getText().toString();
    }

    private String mMobile() {
        return this.metPhone.getText().toString();
    }

    private String mAddress() {
        return this.metDetailAddress.getText().toString();
    }

    private AreaBean mAreaBean() {
        if (this.areaBean != null) {
            return this.areaBean;
        }
        AreaBean area = new AreaBean();
        area.setProvice(this.editAddressBean.getProvince());
        area.setCity((String) this.editAddressBean.getCity());
        area.setDistrict((String) this.editAddressBean.getDistrict());
        return area;
    }

    private int isDefault() {
        if (this.mSb.isChecked()) {
            return 0;
        }
        return 1;
    }

    private int addressId() {
        if (this.edit != 2 || this.editAddressBean == null) {
            return 0;
        }
        return this.editAddressBean.getId();
    }

    public void updateAddressStatus(int addressId, int status) {
        if (status == 2) {
            showMessage((int) R.string.save_success);
        }
        setResult(1);
        finish();
    }

    public void updateUserAddressList(List<AddressBean> list) {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.areaDialog != null) {
            this.areaDialog.dismiss();
        }
    }
}
