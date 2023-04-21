package cn.com.bioeasy.healty.app.healthapp.modules.test.view;

import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStrip;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import cn.com.bioeasy.healty.app.healthapp.modules.test.modal.TestItemRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class TestItemPresenter extends BasePresenter<ItemView, TestItemRepository> {
    @Inject
    public TestItemPresenter(TestItemRepository mRepository) {
        super(mRepository);
    }

    public void getItemCategoryList() {
        List<TestStripCategory> categoryList = HealthApp.x.getDataManager().getCategoryList();
        if (categoryList == null || categoryList.size() <= 0) {
            ((TestItemRepository) this.mRepository).getItemCategoryList().subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
                public void call() {
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<List<TestStripCategory>>>() {
                public void onNext(HttpResult<List<TestStripCategory>> result) {
                    if (result.result == 0) {
                        int[] iconRes = {0, R.drawable.milk, R.drawable.livestock, R.drawable.pesticide, R.drawable.oil};
                        String[] colorStr = {"", "#1cb663", "#fa7a53", "#3a98e0", "#f5c010", "#a051b0"};
                        List<TestStripCategory> resultList = result.data;
                        for (TestStripCategory category : resultList) {
                            if (category.getId() < iconRes.length) {
                                category.setIcon(iconRes[category.getId()]);
                                category.setColor(colorStr[category.getId()]);
                            } else {
                                category.setIcon(R.drawable.heath_vegetables_check);
                                category.setColor("#1cb663");
                            }
                        }
                        HealthApp.x.getDataManager().setCategoryList(resultList);
                        ((ItemView) TestItemPresenter.this.mUiView).updateItemCategoryList(resultList);
                    }
                }
            });
        } else {
            ((ItemView) this.mUiView).updateItemCategoryList(categoryList);
        }
    }

    public void getItemList(final int categoryId, int pageNumber) {
        Map<Integer, List<TestStrip>> categoryItemMap = HealthApp.x.getDataManager().getCategoryItemMap();
        if (categoryItemMap == null || !categoryItemMap.containsKey(Integer.valueOf(categoryId))) {
            ((TestItemRepository) this.mRepository).getItemInfoList(categoryId, 0).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
                public void call() {
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<TestStrip>>>() {
                public void onNext(HttpResult<PageResult<TestStrip>> result) {
                    if (result.result == 0) {
                        Map<Integer, List<TestStrip>> categoryItemMap = HealthApp.x.getDataManager().getCategoryItemMap();
                        Map<Integer, TestStrip> testStripMap = HealthApp.x.getDataManager().getStripMap();
                        if (categoryItemMap == null) {
                            categoryItemMap = new HashMap<>();
                        }
                        if (((PageResult) result.data).getRows().size() > 0) {
                            categoryItemMap.put(Integer.valueOf(categoryId), ((PageResult) result.data).getRows());
                            if (testStripMap == null) {
                                testStripMap = new HashMap<>();
                            }
                            for (TestStrip strip : ((PageResult) result.data).getRows()) {
                                if (!testStripMap.containsKey(strip.getId())) {
                                    testStripMap.put(strip.getId(), strip);
                                }
                            }
                            HealthApp.x.getDataManager().setStripMap(testStripMap);
                            HealthApp.x.getDataManager().setCategoryItemMap(categoryItemMap);
                            ((ItemView) TestItemPresenter.this.mUiView).updateItemList(categoryItemMap.get(Integer.valueOf(categoryId)));
                        }
                    }
                }
            });
        } else {
            ((ItemView) this.mUiView).updateItemList(categoryItemMap.get(Integer.valueOf(categoryId)));
        }
    }

    public void onDestroy() {
    }
}
