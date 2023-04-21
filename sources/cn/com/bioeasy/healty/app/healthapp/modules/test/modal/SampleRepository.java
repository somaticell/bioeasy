package cn.com.bioeasy.healty.app.healthapp.modules.test.modal;

import android.content.Context;
import android.support.annotation.NonNull;
import cn.com.bioeasy.app.base.BaseRepository;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.BusinessOperator;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SampleItemResultData;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.SampleResultData;
import cn.com.bioeasy.healty.app.healthapp.modules.record.bean.ProductTypeItemCond;
import cn.com.bioeasy.healty.app.healthapp.modules.record.bean.ProductTypeItemData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.SampleData;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.UploadDataCallback;
import com.alibaba.fastjson.JSON;
import com.baidu.location.Address;
import com.facebook.common.util.UriUtil;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;

public class SampleRepository extends BaseRepository<SampleService> {
    @Inject
    public SampleRepository(Context context, Retrofit retrofit) {
        super(context, retrofit);
    }

    /* access modifiers changed from: protected */
    public Class<SampleService> getServiceClass() {
        return SampleService.class;
    }

    public Observable<HttpResult<UploadDataCallback>> addSampleData(SampleData sampleData, String[] images) {
        String evaStr = JSON.toJSONString(sampleData);
        Map<String, RequestBody> fileMaps = new HashMap<>();
        if (images != null && images.length > 0) {
            for (int i = 0; i < images.length; i++) {
                File file = new File(images[i]);
                fileMaps.put(UriUtil.LOCAL_FILE_SCHEME + i + "\"; fileName=\"" + file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
            }
        }
        startRequest();
        return ((SampleService) this.service).addSampleData(SampleService.SAMPLE_RESULT_ADD, evaStr, fileMaps.size(), fileMaps);
    }

    public Observable<HttpResult<List<ProductTypeItemData>>> getProductTypeItemDataList(ProductTypeItemCond cond) {
        startRequest();
        return ((SampleService) this.service).getProductTypeItemDataList(SampleService.SAMPLE_ITEM_RESULT_GET, cond.getDays(), cond.getProvince(), cond.getCity(), cond.getDistinct());
    }

    public Observable<HttpResult<PageResult<BusinessOperator>>> searchMarketList(String keyword, int pageNumber, Address address) {
        startRequest();
        if (address == null) {
            return ((SampleService) this.service).searchMarketList(SampleService.MAKET_LIST, keyword, pageNumber, (String) null, (String) null, (String) null);
        }
        return ((SampleService) this.service).searchMarketList(SampleService.MAKET_LIST, keyword, pageNumber, address.province, address.city, address.district);
    }

    public Observable<HttpResult<PageResult<SampleResultData>>> getSampleResultData(String operatorSn, int pageNumber, Address address, int result) {
        startRequest();
        String testResult = result == 0 ? null : String.valueOf(result);
        if (address == null) {
            return ((SampleService) this.service).getSampleResultData(SampleService.SAMPLE_DATA, operatorSn, pageNumber, (String) null, (String) null, (String) null, testResult);
        }
        return ((SampleService) this.service).getSampleResultData(SampleService.SAMPLE_DATA, operatorSn, pageNumber, address.province, address.city, address.district, testResult);
    }

    public Observable<HttpResult<PageResult<SampleItemResultData>>> getSampleItemResultData(String operatorSn, int pageNumber, Address address) {
        startRequest();
        if (address == null) {
            return ((SampleService) this.service).getSampleItemResultData(SampleService.SAMPLE_ITEM_DATA, operatorSn, pageNumber, (String) null, (String) null, (String) null);
        }
        return ((SampleService) this.service).getSampleItemResultData(SampleService.SAMPLE_ITEM_DATA, operatorSn, pageNumber, address.province, address.city, address.district);
    }

    public Observable<HttpResult<List<String>>> getMarketSuggestList(@NonNull String keyword) {
        startRequest();
        return ((SampleService) this.service).getMarketSuggestList(SampleService.MARKET_SUGGEST, keyword);
    }
}
