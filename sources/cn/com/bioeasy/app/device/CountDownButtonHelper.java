package cn.com.bioeasy.app.device;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;

public class CountDownButtonHelper {
    private Button button;
    public CountDownTimer countDownTimer;
    public OnFinishListener listener;

    public interface OnFinishListener {
        void finish();
    }

    public CountDownButtonHelper(Button button2, String defaultString, int max, int interval) {
        this.button = button2;
        final Button button3 = button2;
        final String str = defaultString;
        this.countDownTimer = new CountDownTimer((long) (max * 1000), (long) ((interval * 1000) - 10)) {
            public void onTick(long time) {
                button3.setText(str + "(" + ((time + 15) / 1000) + "秒)");
                Log.d("CountDownButtonHelper", "time = " + time + " text = " + ((time + 15) / 1000));
            }

            public void onFinish() {
                button3.setEnabled(true);
                button3.setText(str);
                if (CountDownButtonHelper.this.listener != null) {
                    CountDownButtonHelper.this.listener.finish();
                }
            }
        };
    }

    public void start() {
        this.button.setEnabled(false);
        this.countDownTimer.start();
    }

    public void setOnFinishListener(OnFinishListener listener2) {
        this.listener = listener2;
    }
}
