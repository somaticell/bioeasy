package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStrip;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import java.util.List;

public interface ItemView extends BaseView {
    void updateItemCategoryList(List<TestStripCategory> list);

    void updateItemList(List<TestStrip> list);
}
