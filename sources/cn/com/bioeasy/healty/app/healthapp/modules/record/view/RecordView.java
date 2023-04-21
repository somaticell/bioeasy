package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.healty.app.healthapp.modules.record.bean.ProductTypeItemData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;
import java.util.List;

public interface RecordView extends BaseView {
    void exportSampleDataList(List<SampleData> list);

    void updateNearbyRecord(List<ProductTypeItemData> list);

    void updateSampleData(PageResult<SampleData> pageResult, boolean z);

    void updateSampleStatic(int i, int i2);
}
