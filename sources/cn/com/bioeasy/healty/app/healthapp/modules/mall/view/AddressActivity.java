package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.AddressAdapter;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderAddressPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.AddressBean;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class AddressActivity extends BIBaseActivity implements AdapterView.OnItemClickListener, AddressView {
    private AddressAdapter addressAdapter;
    @Bind({2131231092})
    ListView mLv;
    @Bind({2131231091})
    FrameLayout mRlExitAddress;
    @Bind({2131231094})
    RelativeLayout mRlNoAddress;
    @Inject
    OrderAddressPresenter orderAddressPresenter;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Bind({2131231179})
    TextView tvAddAddress;
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
        return R.layout.activity_shop_adderss;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getResources().getString(R.string.shopping_order_address_title));
        this.mLv.setOnItemClickListener(this);
        this.addressAdapter = new AddressAdapter(this, new ArrayList());
        this.addressAdapter.setAddressListener(new AddressAdapter.AddressListener() {
            public void deleteAddress(int id) {
                AddressActivity.this.orderAddressPresenter.deleteAddress(id);
            }

            public void updateAddress(AddressBean addressBean) {
                Intent intent = new Intent(AddressActivity.this, AddOrEditAddressActivity.class);
                intent.putExtra(IntentActions.ADDRESS_ACTION, 2);
                intent.putExtra(IntentActions.ADDRESS_EDIT_BEAN, addressBean);
                AddressActivity.this.startActivityForResult(intent, 0);
            }
        });
        this.mLv.setAdapter(this.addressAdapter);
        refreshData();
        initEvent();
    }

    @OnClick({2131231095, 2131231093, 2131231050, 2131231179})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.btn_add_address2:
            case R.id.btn_add_address:
                Intent intent = new Intent(this, AddOrEditAddressActivity.class);
                intent.putExtra(IntentActions.ADDRESS_ACTION, 1);
                startActivityForResult(intent, 0);
                return;
            case R.id.id_tv_edit_all:
                if (view instanceof TextView) {
                    TextView tv = (TextView) view;
                    if ("编辑".equals(tv.getText())) {
                        if (this.addressAdapter.getCount() > 0) {
                            this.addressAdapter.refreshType(1);
                        }
                        tv.setText("完成");
                        changeFootShowDeleteView(true);
                        return;
                    }
                    this.addressAdapter.refreshType(0);
                    tv.setText("编辑");
                    changeFootShowDeleteView(false);
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                refreshData();
                return;
            default:
                return;
        }
    }

    public void updateAddressStatus(int addressId, int status) {
        if (3 == status) {
            showMessage((int) R.string.delete_success);
        } else {
            showMessage((int) R.string.save_success);
        }
        refreshData();
    }

    public void updateUserAddressList(List<AddressBean> lists) {
        if (lists != null) {
            this.addressAdapter.addData(lists);
        } else {
            this.ptrClassicFrameLayout.setLoadMoreEnable(false);
        }
        this.ptrClassicFrameLayout.refreshComplete();
        if (this.addressAdapter.getCount() > 0) {
            this.tvAddAddress.setVisibility(0);
            this.tvAddAddress.setText(getResources().getString(R.string.shopping_order_edit_address));
            this.mRlNoAddress.setVisibility(8);
            this.mRlExitAddress.setVisibility(0);
            return;
        }
        this.mRlNoAddress.setVisibility(0);
        this.mRlExitAddress.setVisibility(8);
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                AddressActivity.this.refreshData();
            }
        });
    }

    /* access modifiers changed from: private */
    public void refreshData() {
        this.orderAddressPresenter.getAllAddress();
    }

    public void changeFootShowDeleteView(boolean showDeleteView) {
        if (showDeleteView) {
            this.tvAddAddress.setText("完成");
        } else {
            this.tvAddAddress.setText("编辑");
        }
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("ORDER_ADDRESS", (AddressBean) this.addressAdapter.getItem(position));
        setResult(1, intent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }
}
