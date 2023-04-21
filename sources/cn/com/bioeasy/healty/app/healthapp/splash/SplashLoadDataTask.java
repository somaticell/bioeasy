package cn.com.bioeasy.healty.app.healthapp.splash;

import android.os.AsyncTask;

public class SplashLoadDataTask extends AsyncTask<Void, Void, Integer> {
    private LoadDataCallback callback;

    public interface LoadDataCallback {
        void loadError();

        void loaded();
    }

    public SplashLoadDataTask(LoadDataCallback callback2) {
        this.callback = callback2;
    }

    /* access modifiers changed from: protected */
    public Integer doInBackground(Void... params) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Integer status) {
        super.onPostExecute(status);
        if (status.intValue() == 0) {
            this.callback.loaded();
        } else if (status.intValue() == 1) {
            this.callback.loadError();
        }
    }
}
