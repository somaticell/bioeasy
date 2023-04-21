package cn.com.bioeasy.healty.app.healthapp.modules.home;

import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.home.bean.Content;
import cn.com.bioeasy.healty.app.healthapp.modules.home.modal.ContentRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.home.view.HomeView;
import cn.com.bioeasy.healty.app.healthapp.modules.test.bean.TestStripCategory;
import java.util.List;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class HomePresenter extends BasePresenter<HomeView, ContentRepository> {
    @Inject
    public HomePresenter(ContentRepository mRepository) {
        super(mRepository);
    }

    public void getContentList(int pageSize, String category, int pageNumber, final boolean refresh) {
        ((ContentRepository) this.mRepository).getContentList(pageSize, category, pageNumber).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<Content>>>() {
            public void onNext(HttpResult<PageResult<Content>> httpResult) {
                if (HomePresenter.this.mUiView != null) {
                    ((HomeView) HomePresenter.this.mUiView).hideProgress();
                }
                if (httpResult.result == 0) {
                    ((HomeView) HomePresenter.this.mUiView).updateContentList((PageResult) httpResult.data, refresh);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (HomePresenter.this.mUiView != null) {
                    ((HomeView) HomePresenter.this.mUiView).hideProgress();
                }
            }
        });
    }

    public void getSearchContentList(String keyword, String category, int pageNumber, final BasePresenter.ICallback<PageResult<Content>> cb) {
        ((ContentRepository) this.mRepository).getSearchContentList(keyword, category, pageNumber).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<Content>>>() {
            public void onNext(HttpResult<PageResult<Content>> httpResult) {
                if (HomePresenter.this.mUiView != null) {
                    ((HomeView) HomePresenter.this.mUiView).hideProgress();
                }
                if (httpResult.result == 0) {
                    cb.onResult(httpResult.data);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (HomePresenter.this.mUiView != null) {
                    ((HomeView) HomePresenter.this.mUiView).hideProgress();
                }
            }
        });
    }

    public void getContentSuggestList(String keyword, String category, final BasePresenter.ICallback<List<String>> cb) {
        ((ContentRepository) this.mRepository).getContentSuggestList(keyword, category).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<List<String>>>() {
            public void onNext(HttpResult<List<String>> httpResult) {
                if (HomePresenter.this.mUiView != null) {
                    ((HomeView) HomePresenter.this.mUiView).hideProgress();
                }
                if (httpResult.result == 0) {
                    cb.onResult(httpResult.data);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (HomePresenter.this.mUiView != null) {
                    ((HomeView) HomePresenter.this.mUiView).hideProgress();
                }
            }
        });
    }

    public void getItemCategoryList() {
        List<TestStripCategory> categoryList = HealthApp.x.getDataManager().getCategoryList();
        if (categoryList == null || categoryList.size() <= 0) {
            ((ContentRepository) this.mRepository).getItemCategoryList().subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
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
                    }
                }
            });
        }
    }
}
