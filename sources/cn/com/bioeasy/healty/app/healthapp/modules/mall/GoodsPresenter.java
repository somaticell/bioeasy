package cn.com.bioeasy.healty.app.healthapp.modules.mall;

import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.exception.HttpServerError;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.retrofit.HttpResult;
import cn.com.bioeasy.app.retrofit.HttpSubscriber;
import cn.com.bioeasy.healty.app.healthapp.HealthApp;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.CategoryNode;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Goods;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsEvaluation;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.GoodsWithBLOBs;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.modal.GoodsRepository;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.GoodsView;
import java.util.List;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class GoodsPresenter extends BasePresenter<GoodsView, GoodsRepository> {
    @Inject
    public GoodsPresenter(GoodsRepository mRepository) {
        super(mRepository);
    }

    public void getGoodsList(int category, int pageSize, int pageNumber, final BasePresenter.ICallback<PageResult<Goods>> cb) {
        ((GoodsRepository) this.mRepository).getGoodsList(category, pageSize, pageNumber).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<Goods>>>() {
            public void onNext(HttpResult<PageResult<Goods>> result) {
                if (result.result == 0) {
                    cb.onResult(result.data);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                ((GoodsView) GoodsPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void getRecommendGoodsList(final BasePresenter.ICallback<List<Goods>> callback) {
        ((GoodsRepository) this.mRepository).getRecommendGoodsList().subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<List<Goods>>>() {
            public void onNext(HttpResult<List<Goods>> result) {
                if (result.result == 0 && callback != null) {
                    callback.onResult(result.data);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                ((GoodsView) GoodsPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void getGoodsDetail(int goodId, final BasePresenter.ICallback<GoodsWithBLOBs> cb) {
        ((GoodsRepository) this.mRepository).getGoodsDetail(goodId).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<GoodsWithBLOBs>>() {
            public void onNext(HttpResult<GoodsWithBLOBs> result) {
                if (result.result == 0) {
                    cb.onResult(result.data);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                ((GoodsView) GoodsPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void getGoodsListByIds(String goodIds, final BasePresenter.ICallback<List<GoodsWithBLOBs>> cb) {
        ((GoodsRepository) this.mRepository).getGoodsListByIds(goodIds).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
                ((GoodsView) GoodsPresenter.this.mUiView).showProgress((int) R.string.error_view_loading);
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<List<GoodsWithBLOBs>>>() {
            public void onNext(HttpResult<List<GoodsWithBLOBs>> result) {
                ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                if (result.result == 0) {
                    cb.onResult(result.data);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                ((GoodsView) GoodsPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void getGoodsEvaluationList(int goodsId, int stars, int pageSize, int pageNumber, final BasePresenter.ICallback<PageResult<GoodsEvaluation>> callback) {
        ((GoodsRepository) this.mRepository).getGoodsEvaluationList(goodsId, stars, pageSize, pageNumber).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<GoodsEvaluation>>>() {
            public void onNext(HttpResult<PageResult<GoodsEvaluation>> cb) {
                if (cb.result == 0 && callback != null) {
                    callback.onResult(cb.data);
                }
            }

            public void OnServerError(HttpServerError e) {
                ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                ((GoodsView) GoodsPresenter.this.mUiView).showErrorMessage(e.getMessage(), "");
            }
        });
    }

    public void getSearchGoodsList(String keyword, String category, int pageNumber, final BasePresenter.ICallback<PageResult<Goods>> cb) {
        ((GoodsRepository) this.mRepository).getSearchGoodsList(keyword, category, pageNumber).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<PageResult<Goods>>>() {
            public void onNext(HttpResult<PageResult<Goods>> httpResult) {
                if (GoodsPresenter.this.mUiView != null) {
                    ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                }
                if (httpResult.result == 0) {
                    cb.onResult(httpResult.data);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (GoodsPresenter.this.mUiView != null) {
                    ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                }
            }
        });
    }

    public void getGoodsSuggestList(String keyword, String category, final BasePresenter.ICallback<List<String>> cb) {
        ((GoodsRepository) this.mRepository).getGoodsSuggestList(keyword, category).subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
            public void call() {
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<List<String>>>() {
            public void onNext(HttpResult<List<String>> httpResult) {
                if (GoodsPresenter.this.mUiView != null) {
                    ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                }
                if (httpResult.result == 0) {
                    cb.onResult(httpResult.data);
                }
            }

            public void onError(Throwable e) {
                super.onError(e);
                if (GoodsPresenter.this.mUiView != null) {
                    ((GoodsView) GoodsPresenter.this.mUiView).hideProgress();
                }
            }
        });
    }

    public void getGoodsCategoryList(final BasePresenter.ICallback<List<CategoryNode>> cb) {
        List<CategoryNode> list = HealthApp.x.getDataManager().getGoodsCategoryList();
        if (list == null || list.size() <= 0) {
            ((GoodsRepository) this.mRepository).getGoodsCategoryList().subscribeOn(Schedulers.io()).doOnSubscribe(new Action0() {
                public void call() {
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpSubscriber<HttpResult<List<CategoryNode>>>() {
                public void onNext(HttpResult<List<CategoryNode>> httpResult) {
                    if (httpResult.result == 0) {
                        cb.onResult(httpResult.data);
                        HealthApp.x.getDataManager().setGoodsCategoryList((List) httpResult.data);
                    }
                }

                public void onError(Throwable e) {
                    super.onError(e);
                }
            });
        } else {
            cb.onResult(list);
        }
    }
}
