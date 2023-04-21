package net.oschina.common.admin;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import net.oschina.common.R;
import net.oschina.common.utils.HashUtil;
import net.oschina.common.utils.NativeUtil;

public final class Boss extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private static boolean mAllowEnter = false;
    private static int mClickCount;
    private static long mClickLastTime = System.currentTimeMillis();
    private EditText mKey;
    private long mLastClick;
    private TextView mToken;

    private static native String native_checkKeyToGetToken(Application application, String str);

    public static void verifyApp(Context context) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mClickLastTime < 260) {
            mClickCount++;
        } else {
            mClickCount = 0;
        }
        mClickLastTime = currentTime;
        if (mClickCount >= 10) {
            mAllowEnter = true;
            context.startActivity(new Intent(context, Boss.class));
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);
        if (!mAllowEnter) {
            finish();
            return;
        }
        mAllowEnter = false;
        this.mKey = (EditText) findViewById(R.id.edt_key);
        this.mToken = (TextView) findViewById(R.id.txt_token);
        this.mToken.setOnClickListener(this);
        this.mToken.setOnLongClickListener(this);
    }

    public void onClick(View view) {
        this.mToken.clearFocus();
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.mToken.getWindowToken(), 2);
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.mLastClick < 260) {
            checkAndGetToken();
        }
        this.mLastClick = currentTime;
    }

    public boolean onLongClick(View view) {
        String token = this.mToken.getText().toString();
        if (TextUtils.isEmpty(token)) {
            return false;
        }
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("AppTokenKey", token));
        Toast.makeText(this, "Copy done!", 0).show();
        return true;
    }

    private void checkAndGetToken() {
        String key = this.mKey.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            Toast.makeText(this, "Your key is empty!", 0).show();
            return;
        }
        NativeUtil.doLoadNative();
        String token = native_checkKeyToGetToken(getApplication(), HashUtil.getMD5(key));
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(this, "Your key is error!", 0).show();
        } else {
            this.mToken.setText(token);
        }
    }
}
