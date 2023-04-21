package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.widgets.recyclerview.adapter.CommonAdapter;
import cn.com.bioeasy.app.widgets.recyclerview.base.ViewHolder;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Express;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ExpressProgressInfo;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class OrderExpressActivity extends BIBaseActivity implements OrderView {
    /* access modifiers changed from: private */
    public CommonAdapter<ExpressProgressInfo> commonAdapter;
    private Express express;
    /* access modifiers changed from: private */
    public List<ExpressProgressInfo> expressProgressInfoList = new ArrayList();
    @Bind({2131230932})
    TextView mOrderExpressText;
    @Bind({2131230931})
    TextView mOrderSnText;
    private Order order;
    @Inject
    OrderPresenter orderPresenter;
    @Bind({2131230933})
    RecyclerView recyclerView;
    @Bind({2131231008})
    TextView tvTitle;

    /* access modifiers changed from: protected */
    public void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public BasePresenter getPresenter() {
        return this.orderPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_express;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getString(R.string.ORDER_EXPRESS_DETAIL));
        this.order = (Order) getIntent().getSerializableExtra(IntentActions.ORDER_INFO);
        this.express = (Express) JSON.parseObject(JSON.parseObject(this.order.getProps()).getJSONObject("express").toJSONString(), Express.class);
        this.mOrderSnText.setText(getString(R.string.ORDER_SN) + this.order.getId());
        this.mOrderExpressText.setText("");
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.commonAdapter = new CommonAdapter<ExpressProgressInfo>(this, this.expressProgressInfoList, R.layout.activity_express_timeline) {
            /* access modifiers changed from: protected */
            public void convert(ViewHolder holder, ExpressProgressInfo item, int position) {
                holder.setText((int) R.id.tv_express_progress, item.getContext());
                holder.setText((int) R.id.tv_express_time, item.getTime());
            }
        };
        this.recyclerView.setAdapter(this.commonAdapter);
        initData();
    }

    private void initData() {
        this.orderPresenter.getOrderExpressProgress(this.express.getCompany(), this.express.getTrackingNo(), new BasePresenter.ICallback<JSONObject>() {
            public void onResult(JSONObject data) {
                if (data.getInteger("showapi_res_code").intValue() != 0) {
                    OrderExpressActivity.this.showMessage(data.getString("showapi_res_error"));
                    return;
                }
                JSONObject body = data.getJSONObject("showapi_res_body");
                OrderExpressActivity.this.mOrderExpressText.setText(OrderExpressActivity.this.getString(R.string.ORDER_EXPRESS_TYPE) + body.getString("expTextName") + " " + body.getString("mailNo"));
                if (!body.getBoolean("flag").booleanValue()) {
                    OrderExpressActivity.this.showMessage((int) R.string.EXPRESS_QUERY_FAIL);
                    return;
                }
                List unused = OrderExpressActivity.this.expressProgressInfoList = JSONArray.parseArray(body.getJSONArray("data").toJSONString(), ExpressProgressInfo.class);
                OrderExpressActivity.this.commonAdapter.refresItems(OrderExpressActivity.this.expressProgressInfoList);
            }
        });
    }

    @OnClick({2131231050})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            default:
                return;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }

    public void updateOrderStatus(Order order2, int status) {
    }

    public void updateOrderList(PageResult<Order> pageResult, boolean refresh) {
    }
}
