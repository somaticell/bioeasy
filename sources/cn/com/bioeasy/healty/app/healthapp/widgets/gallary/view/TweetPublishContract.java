package cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view;

import android.content.Context;
import android.os.Bundle;

public interface TweetPublishContract {

    public interface Operator {
        void loadData();

        void onBack();

        void onRestoreInstanceState(Bundle bundle);

        void onSaveInstanceState(Bundle bundle);

        void publish();

        void setDataView(View view, String str, String[] strArr);
    }

    public interface View {
        void finish();

        String getContent();

        Context getContext();

        String[] getImages();

        Operator getOperator();

        boolean needCommit();

        boolean onBackPressed();

        void setContent(String str, boolean z);

        void setImages(String[] strArr);
    }
}
