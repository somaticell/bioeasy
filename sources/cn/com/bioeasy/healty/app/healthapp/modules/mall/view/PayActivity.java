package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.utils.StringUtil;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.constant.PayType;
import cn.com.bioeasy.healty.app.healthapp.injection.component.ActivityComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.PayResult;
import cn.com.bioeasy.healty.app.healthapp.modules.mine.view.MyOrderActivity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import java.util.Date;
import java.util.Map;
import javax.inject.Inject;

public class PayActivity extends BIBaseActivity implements OrderView {
    private static final int SDK_AUTH_FLAG = 2;
    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI api;
    @Bind({2131231129})
    CheckBox cbWeixin;
    @Bind({2131231133})
    CheckBox cbZhifubao;
    /* access modifiers changed from: private */
    @SuppressLint({"HandlerLeak"})
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    PayResult payResult = new PayResult((Map) msg.obj);
                    String result = payResult.getResult();
                    if (TextUtils.equals(payResult.getResultStatus(), "9000")) {
                        PayActivity.this.showMessage((int) R.string.PAY_SUCCESS);
                        PayActivity.this.orderPresenter.getOrder(PayActivity.this.order.getId().longValue(), new BasePresenter.ICallback<Order>() {
                            public void onResult(Order result) {
                                OrderDetailActivity.startAction(PayActivity.this, result);
                                PayActivity.this.finish();
                            }
                        });
                        return;
                    }
                    PayActivity.this.showMessage((int) R.string.PAY_FAIL);
                    return;
                default:
                    return;
            }
        }
    };
    @Bind({2131231125})
    TextView mTvMoney;
    @Bind({2131231134})
    RelativeLayout mrlPay;
    @Bind({2131231126})
    RelativeLayout mrlWeixin;
    @Bind({2131231130})
    RelativeLayout mrlZhifubao;
    /* access modifiers changed from: private */
    public Order order;
    @Inject
    OrderPresenter orderPresenter;
    private int payType = 1;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (IntentActions.ORDER_PAY_SUCCESS.equals(intent.getAction())) {
                switch (intent.getIntExtra("errCode", 0)) {
                    case -2:
                        PayActivity.this.showMessage((int) R.string.USER_CANCEL_PAY);
                        break;
                    case -1:
                        PayActivity.this.showMessage((int) R.string.PAY_FAIL);
                        break;
                    case 0:
                        PayActivity.this.showMessage((int) R.string.PAY_SUCCESS);
                        break;
                }
                long orderId = intent.getLongExtra(IntentActions.ORDER_PAY_ID, 0);
                if (orderId > 0) {
                    PayActivity.this.orderPresenter.getOrder(orderId, new BasePresenter.ICallback<Order>() {
                        public void onResult(Order result) {
                            OrderDetailActivity.startAction(PayActivity.this, result);
                            PayActivity.this.finish();
                        }
                    });
                }
            }
        }
    };
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
        return R.layout.activity_shop_pay;
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents() {
        this.tvTitle.setText(getResources().getString(R.string.shopping_pay_title));
        this.order = (Order) getIntent().getSerializableExtra("order");
        if (this.order != null) {
            String totalFeeFmt = getString(R.string.PAY_AMOUNT) + this.order.getTotalPrice();
            int endIndex = totalFeeFmt.length();
            SpannableString totalFeeSpanStr = new SpannableString(totalFeeFmt);
            totalFeeSpanStr.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_red)), 6, endIndex, 33);
            this.mTvMoney.setText(totalFeeSpanStr);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentActions.ORDER_PAY_SUCCESS);
        registerReceiver(this.receiver, intentFilter);
    }

    @OnClick({2131231050, 2131231134, 2131231126, 2131231130})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                return;
            case R.id.rl_weixin:
                this.cbWeixin.setChecked(true);
                this.cbZhifubao.setChecked(false);
                initBg();
                this.payType = 1;
                return;
            case R.id.rl_zhifubao:
                this.cbWeixin.setChecked(false);
                this.cbZhifubao.setChecked(true);
                initBg();
                this.payType = 2;
                return;
            case R.id.rl_submit_pay:
                goPay();
                return;
            default:
                return;
        }
    }

    private void initBg() {
        if (this.cbWeixin.isChecked() || this.cbZhifubao.isChecked()) {
            this.mrlPay.setClickable(true);
            this.mrlPay.setBackgroundColor(getResources().getColor(R.color.buying));
        }
    }

    private void goPay() {
        if (this.payType == 1) {
            if (this.api == null) {
                this.api = WXAPIFactory.createWXAPI(this, PayType.WX_APP_ID);
            }
            this.orderPresenter.createWXOrderPay(this.order, this.payType, new BasePresenter.ICallback<String>() {
                public void onResult(String result) {
                    JSONObject wePayObj = JSON.parseObject(result);
                    if (!"SUCCESS".equals(wePayObj.getString("return_code"))) {
                        PayActivity.this.showErrorMessage((int) R.string.PAY_FAIL, (int) R.string.PAY_FAIL);
                    } else if ("SUCCESS".equals(wePayObj.get("result_code"))) {
                        PayActivity.this.weixinPay(wePayObj, PayActivity.this.order);
                    } else {
                        PayActivity.this.showErrorMessage(PayActivity.this.getString(R.string.PAY_SUCCESS), wePayObj.getString("err_code_des"));
                    }
                }
            });
        } else if (this.payType == 2) {
            this.orderPresenter.createAliOrderPay(this.order, this.payType, new BasePresenter.ICallback<String>() {
                public void onResult(final String result) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Map<String, String> payResult = new PayTask(PayActivity.this).payV2(result, true);
                                Logger.e("msp:" + payResult.toString(), new Object[0]);
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = payResult;
                                PayActivity.this.mHandler.sendMessage(msg);
                            } catch (Exception e) {
                            }
                        }
                    }).start();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void weixinPay(JSONObject wePayObj, Order order2) {
        PayReq req = new PayReq();
        req.appId = PayType.WX_APP_ID;
        req.partnerId = PayType.WX_PARTNER_ID;
        req.prepayId = wePayObj.getString("prepay_id");
        req.nonceStr = StringUtil.getRandomStringByLength(16);
        req.timeStamp = String.valueOf(new Date().getTime());
        req.packageValue = "Sign=WXPay";
        req.extData = String.valueOf(order2.getId());
        req.sign = StringUtil.getMessageDigest((("appid=" + req.appId) + "&noncestr=" + req.nonceStr + "&package=" + req.packageValue + "&partnerid=" + req.partnerId + "&prepayid=" + req.prepayId + "&timestamp=" + req.timeStamp) + "&key=d6d5cdf3a46c80a399ce51613392b1fd").toUpperCase();
        this.api.sendReq(req);
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

    public static void startAction(Activity activity, Order order2) {
        Intent intent = new Intent(activity, PayActivity.class);
        intent.putExtra("order", order2);
        activity.startActivity(intent);
    }

    public void updateOrderStatus(Order order2, int status) {
        if (status == 1) {
            showMessage((int) R.string.PAY_SUCCESS);
            switchPage(MyOrderActivity.class);
            finish();
        }
    }

    public void updateOrderList(PageResult<Order> pageResult, boolean refresh) {
    }
}
