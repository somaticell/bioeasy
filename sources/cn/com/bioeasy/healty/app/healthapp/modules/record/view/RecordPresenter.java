package cn.com.bioeasy.healty.app.healthapp.modules.record.view;

import android.database.Cursor;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SampleResultData;
import cn.com.bioeasy.healty.app.healthapp.modules.record.bean.ProductTypeItemCond;
import cn.com.bioeasy.healty.app.healthapp.modules.record.bean.ProductTypeItemData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.SampleRepository;
import com.baidu.location.Address;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.litepal.crud.DataSupport;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class RecordPresenter extends BasePresenter<RecordView, SampleRepository> {
    @Inject
    public RecordPresenter(SampleRepository mRepository) {
        super(mRepository);
    }

    public void getSampleData(int pageSize, int pageNumber, int recordDays, boolean invalid, boolean refresh) {
        int recordDays2 = recordDays * -1;
        int offset = (pageNumber - 1) * pageSize;
        String daysCond = "date(time) between date('now','start of day','" + String.valueOf(recordDays2) + " day') and date('now','start of day','1 day')";
        if (invalid) {
            daysCond = daysCond + " and result > 0";
        }
        List<SampleData> list = DataSupport.select("*").where(daysCond).order("time desc").limit(pageSize).offset(offset).find(SampleData.class);
        int totalCount = DataSupport.where(daysCond).count((Class<?>) SampleData.class);
        PageResult<SampleData> pageResult = new PageResult<>();
        pageResult.setRows(list);
        pageResult.setTotal(totalCount);
        if (refresh) {
            updateSampleStatisData(recordDays2, totalCount);
        }
        ((RecordView) this.mUiView).updateSampleData(pageResult, refresh);
    }

    private void updateSampleStatisData(int recordDays, int totalCount) {
        ((RecordView) this.mUiView).updateSampleStatic(totalCount, DataSupport.where("result = 0 and date(time) between date('now','start of day','" + String.valueOf(recordDays) + " day') and date('now','start of day','1 day')").count((Class<?>) SampleData.class));
    }

    public void getLocalProductTypeItemDataList(int recordDays) {
        int recordDays2 = recordDays * -1;
        new ProductTypeItemCond().setDays(recordDays2);
        List<ProductTypeItemData> dataList = new ArrayList<>();
        Cursor cr = DataSupport.findBySQL("select producttype, result, count(result) from sampledata where " + ("result > 0 and date(time) between date('now','start of day','" + String.valueOf(recordDays2) + " day') and date('now','start of day','1 day')") + " group by producttype, result");
        if (cr.moveToFirst()) {
            for (int i = 0; i < cr.getCount(); i++) {
                ProductTypeItemData itemData = new ProductTypeItemData();
                itemData.setProductType(cr.getInt(0));
                itemData.setInvalidCount(cr.getInt(2));
                dataList.add(itemData);
                cr.moveToNext();
            }
        }
        ((RecordView) this.mUiView).updateNearbyRecord(dataList);
    }

    public void getServerSampleResultData(int pageNumber, int result, final BasePresenter.ICallback<PageResult<SampleResultData>> cb) {
        ((SampleRepository) this.mRepository).getSampleResultData((String) null, pageNumber, HealthApp.x.getDataManager().getAddress(), result).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<SampleResultData>>>() {
            public void onNext(HttpResult<PageResult<SampleResultData>> result) {
                ((RecordView) RecordPresenter.this.mUiView).hideProgress();
                if (result.result == 0 && cb != null) {
                    cb.onResult(result.data);
                }
            }
        });
    }

    public void getProductTypeItemDataList(int recordDays) {
        Address address = HealthApp.x.getDataManager().getAddress();
        ProductTypeItemCond cond = new ProductTypeItemCond();
        if (address != null) {
            cond.setCity(address.city);
            cond.setDistinct(address.district);
            cond.setProvince(address.province);
        }
        cond.setDays(recordDays);
        ((SampleRepository) this.mRepository).getProductTypeItemDataList(cond).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<List<ProductTypeItemData>>>() {
            public void onNext(HttpResult<List<ProductTypeItemData>> result) {
                if (result.result == 0) {
                    ((RecordView) RecordPresenter.this.mUiView).updateNearbyRecord((List) result.data);
                }
            }
        });
    }

    public void exportSampleData(int recordDays, boolean invalid) {
        String daysCond = "date(time) between date('now','start of day','" + String.valueOf(recordDays * -1) + " day') and date('now','start of day','1 day')";
        if (invalid) {
            daysCond = daysCond + " and result > 0";
        }
        ((RecordView) this.mUiView).exportSampleDataList(DataSupport.select("*").where(daysCond).order("time desc").find(SampleData.class));
    }
}
