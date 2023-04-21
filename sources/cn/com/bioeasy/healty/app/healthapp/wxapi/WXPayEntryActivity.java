package cn.com.bioeasy.healty.app.healthapp.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.constant.PayType;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        this.api = WXAPIFactory.createWXAPI(this, PayType.WX_APP_ID);
        this.api.handleIntent(getIntent(), this);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.api.handleIntent(intent, this);
    }

    public void onReq(BaseReq req) {
    }

    public void onResp(BaseResp resp) {
        Log.d(TAG, "pay response: " + resp.errCode);
        if (resp.getType() == 5) {
            finish();
            Intent intent = new Intent();
            intent.setAction(IntentActions.ORDER_PAY_SUCCESS);
            intent.putExtra("errCode", resp.errCode);
            intent.putExtra(IntentActions.ORDER_PAY_ID, Long.parseLong(((PayResp) resp).extData));
            sendBroadcast(intent);
        }
    }
}
