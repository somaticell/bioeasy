package cn.com.bioeasy.healty.app.healthapp.modules.home.view;

import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;

public interface HomeView extends BaseView {
    void showErrorLayout(int i);

    void updateContentList(PageResult<Content> pageResult, boolean z);
}
