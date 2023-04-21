package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import android.content.ContentValues;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.app.utils.DateUtil;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.ble.BLERepository;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.BusinessOperator;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.MarketSearchItem;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SampleItemResultData;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SampleResultData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.Item;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleItemData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestResultItem;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.UploadDataCallback;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.SampleRepository;
import cn.com.bioeasy.healty.app.healthapp.widgets.gallary.view.ImageGalleryActivity;
import com.alipay.sdk.cons.a;
import com.baidu.location.Address;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.litepal.crud.DataSupport;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class SamplePresenter extends BasePresenter<SampleView, SampleRepository> {
    BLERepository bleRepository;

    @Inject
    public SamplePresenter(BLERepository bleRepository2, SampleRepository mRepository) {
        super(mRepository);
        this.bleRepository = bleRepository2;
    }

    public int saveData(int testResult, int categoryId, List<TestResultItem> resultItemList, String dataStr, String sampleName, String stripName) {
        SampleData sampleData = new SampleData();
        int userId = HealthApp.x.getUserInfo() != null ? HealthApp.x.getUserInfo().getUserId() : 0;
        String deviceName = this.bleRepository.getDeviceMac();
        sampleData.setUserId(userId);
        sampleData.setDeviceName(deviceName);
        sampleData.setSampleName(sampleName);
        sampleData.setStripName(stripName);
        sampleData.setTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss", new Date().getTime()));
        Address address = HealthApp.x.getDataManager().getAddress();
        if (address != null) {
            sampleData.setAddress(address.address);
            sampleData.setCity(address.city);
            sampleData.setDistinct(address.district);
            sampleData.setProvince(address.province);
        }
        double latitude = HealthApp.x.getDataManager().getLatitude();
        double longitude = HealthApp.x.getDataManager().getLongitude();
        sampleData.setLatitude(latitude);
        sampleData.setLongitude(longitude);
        sampleData.setProductType(categoryId);
        sampleData.setResult(testResult);
        if (resultItemList != null && resultItemList.size() > 0) {
            List<SampleItemData> itemDataList = new ArrayList<>();
            for (int i = 0; i < resultItemList.size(); i++) {
                SampleItemData itemData = new SampleItemData();
                TestResultItem resultItem = resultItemList.get(i);
                Item item = resultItem.getItem();
                itemData.setDate(new Date());
                itemData.setItemId(item.getId().intValue());
                itemData.setItemName(item.getName());
                itemData.setUnit(item.getValueUnit());
                itemData.setResult(resultItem.getResult().intValue());
                itemData.setValue(resultItem.getValue());
                itemData.setItemType(item.getType().intValue());
                itemDataList.add(itemData);
            }
            if (itemDataList.size() > 0) {
                SampleItemData.saveAll(itemDataList);
            }
            sampleData.setItemDataList(itemDataList);
        }
        sampleData.setResultList(dataStr);
        if (sampleData.save()) {
            return sampleData.getId();
        }
        return 0;
    }

    public void uploadData(int localSampleId, String marketName, String sampleName, String remark, String[] images) {
        SampleData sampleData;
        ContentValues values = new ContentValues();
        values.put("marketName", marketName);
        values.put("sampleName", sampleName);
        values.put("remark", remark);
        String imgPaths = "";
        if (images != null) {
            for (int i = 0; i < images.length; i++) {
                imgPaths = imgPaths + images[i];
                if (i < images.length - 1) {
                    imgPaths = imgPaths + "|";
                }
            }
        }
        values.put(ImageGalleryActivity.KEY_IMAGE, imgPaths);
        if (DataSupport.update(SampleData.class, values, (long) localSampleId) > 0 && (sampleData = (SampleData) DataSupport.find(SampleData.class, (long) localSampleId)) != null) {
            sampleData.setItemDataList(DataSupport.where("sampledata_id = ? ", String.valueOf(localSampleId)).find(SampleItemData.class));
        }
    }

    public void updateData(final SampleData sampleData, String[] images) {
        ((SampleRepository) this.mRepository).addSampleData(sampleData, images).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((SampleView) SamplePresenter.this.mUiView).showProgress((int) R.string.uploading_data);
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<UploadDataCallback>>() {
            public void onNext(HttpResult<UploadDataCallback> result) {
                ((SampleView) SamplePresenter.this.mUiView).hideProgress();
                if (result.result == 0) {
                    ContentValues values = new ContentValues();
                    values.put("status", a.e);
                    values.put("sampleId", Integer.valueOf(((UploadDataCallback) result.data).getSampleId()));
                    DataSupport.update(SampleData.class, values, (long) ((UploadDataCallback) result.data).getUploadId());
                    UploadDataCallback callback = (UploadDataCallback) result.data;
                    if (callback != null) {
                        callback.setSampleData(sampleData);
                    }
                    ((SampleView) SamplePresenter.this.mUiView).uploadDateResult(callback);
                }
            }
        });
    }

    public void searchMarketList(String keyword, int pageNumber, final BasePresenter.ICallback<PageResult<BusinessOperator>> cb) {
        ((SampleRepository) this.mRepository).searchMarketList(keyword, pageNumber, HealthApp.x.getDataManager().getAddress()).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<BusinessOperator>>>() {
            public void onNext(HttpResult<PageResult<BusinessOperator>> result) {
                ((SampleView) SamplePresenter.this.mUiView).hideProgress();
                if (result.result == 0 && cb != null) {
                    cb.onResult(result.data);
                }
            }
        });
    }

    public void getSampleResultData(String opeartorSn, int pageNumber, final BasePresenter.ICallback<PageResult<SampleResultData>> cb) {
        ((SampleRepository) this.mRepository).getSampleResultData(opeartorSn, pageNumber, HealthApp.x.getDataManager().getAddress(), 0).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<SampleResultData>>>() {
            public void onNext(HttpResult<PageResult<SampleResultData>> result) {
                ((SampleView) SamplePresenter.this.mUiView).hideProgress();
                if (result.result == 0 && cb != null) {
                    cb.onResult(result.data);
                }
            }
        });
    }

    public void getSampleItemResultData(String opeartorSn, int pageNumber, final BasePresenter.ICallback<PageResult<SampleItemResultData>> cb) {
        ((SampleRepository) this.mRepository).getSampleItemResultData(opeartorSn, pageNumber, HealthApp.x.getDataManager().getAddress()).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<SampleItemResultData>>>() {
            public void onNext(HttpResult<PageResult<SampleItemResultData>> result) {
                ((SampleView) SamplePresenter.this.mUiView).hideProgress();
                if (result.result == 0 && cb != null) {
                    cb.onResult(result.data);
                }
            }
        });
    }

    public void getMarketSuggestList(String keyword, final BasePresenter.ICallback<List<String>> cb) {
        ((SampleRepository) this.mRepository).getMarketSuggestList(keyword).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<List<String>>>() {
            public void onNext(HttpResult<List<String>> httpResult) {
                if (httpResult.result == 0) {
                    cb.onResult(httpResult.data);
                }
            }
        });
    }

    public void getSearchMarketHistory(BasePresenter.ICallback<List<String>> cb) {
        List<MarketSearchItem> list = DataSupport.select("*").order("time desc").limit(10).offset(0).find(MarketSearchItem.class);
        if (cb != null) {
            List<String> searchList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                searchList.add(list.get(i).getName());
            }
            cb.onResult(searchList);
        }
    }

    public void clearSearchMarketHistory() {
        DataSupport.deleteAll((Class<?>) MarketSearchItem.class, new String[0]);
    }

    public void saveResultList(int localSampleId, String resultList) {
        ContentValues values = new ContentValues();
        values.put("resultList", resultList);
        DataSupport.update(SampleData.class, values, (long) localSampleId);
    }
}
