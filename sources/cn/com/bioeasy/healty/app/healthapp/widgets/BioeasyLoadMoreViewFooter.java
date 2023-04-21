package cn.com.bioeasy.healty.app.healthapp.widgets;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.com.bioeasy.healty.app.healthapp.R;
import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;

public class BioeasyLoadMoreViewFooter implements ILoadMoreViewFactory {
    public ILoadMoreViewFactory.ILoadMoreView madeLoadMoreView() {
        return new LoadMoreHelper();
    }

    private class LoadMoreHelper implements ILoadMoreViewFactory.ILoadMoreView {
        protected ProgressBar footerBar;
        protected TextView footerTv;
        protected View footerView;
        protected View.OnClickListener onClickRefreshListener;

        private LoadMoreHelper() {
        }

        public void init(ILoadMoreViewFactory.FootViewAdder footViewHolder, View.OnClickListener onClickRefreshListener2) {
            this.footerView = footViewHolder.addFootView((int) R.layout.loadmore_default_footer);
            this.footerTv = (TextView) this.footerView.findViewById(R.id.loadmore_default_footer_tv);
            this.footerBar = (ProgressBar) this.footerView.findViewById(R.id.loadmore_default_footer_progressbar);
            this.onClickRefreshListener = onClickRefreshListener2;
            showNormal();
        }

        public void showNormal() {
            this.footerTv.setText(R.string.loading_more);
            this.footerBar.setVisibility(8);
            this.footerView.setOnClickListener(this.onClickRefreshListener);
        }

        public void showLoading() {
            this.footerTv.setText(R.string.loading);
            this.footerBar.setVisibility(0);
            this.footerView.setOnClickListener((View.OnClickListener) null);
        }

        public void showFail(Exception exception) {
            this.footerTv.setText(R.string.error_message);
            this.footerBar.setVisibility(8);
            this.footerView.setOnClickListener(this.onClickRefreshListener);
        }

        public void showNomore() {
            this.footerTv.setText(R.string.load_finsh);
            this.footerBar.setVisibility(8);
            this.footerView.setOnClickListener((View.OnClickListener) null);
        }

        public void setFooterVisibility(boolean isVisible) {
            this.footerView.setVisibility(isVisible ? 0 : 8);
        }
    }
}
