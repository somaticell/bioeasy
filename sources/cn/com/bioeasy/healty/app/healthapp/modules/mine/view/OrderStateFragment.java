package cn.com.bioeasy.healty.app.healthapp.modules.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Bind;
import cn.com.bioeasy.app.base.BasePresenter;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.app.widgets.dialog.DialogManager;
import cn.com.bioeasy.healty.app.healthapp.R;
import cn.com.bioeasy.healty.app.healthapp.adapter.OrderStateAdapter;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseActivity;
import cn.com.bioeasy.healty.app.healthapp.base.BIBaseFragment;
import cn.com.bioeasy.healty.app.healthapp.constant.ActionConstant;
import cn.com.bioeasy.healty.app.healthapp.constant.IntentActions;
import cn.com.bioeasy.healty.app.healthapp.injection.component.FragmentComponent;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.OrderPresenter;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.OrderItem;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.ShopCardItem;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderDetailActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderEvaluationActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderExpressActivity;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.OrderView;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.view.PayActivity;
import cn.com.bioeasy.healty.app.healthapp.widgets.BioeasyLoadMoreViewFooter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class OrderStateFragment extends BIBaseFragment implements OrderView {
    private static final String ORDER_STATUS = "ORDER_STATUS";
    private OrderStateAdapter mAdapter;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Order order = (Order) msg.obj;
            switch (msg.what) {
                case 11:
                    OrderDetailActivity.startAction(OrderStateFragment.this.getActivity(), order);
                    return;
                case 666:
                    OrderStateFragment.this.cancelOrder(order);
                    return;
                case ActionConstant.tagPay:
                    OrderStateFragment.this.gotoPay(order);
                    return;
                case ActionConstant.tagReceive:
                    OrderStateFragment.this.confirmReceive(order);
                    return;
                case ActionConstant.tagShopping:
                    OrderStateFragment.this.viewShipping(order);
                    return;
                case ActionConstant.tagComment:
                    OrderStateFragment.this.commentOrder(order);
                    return;
                case ActionConstant.tagBuyAgain:
                    OrderStateFragment.this.againBuy(order);
                    return;
                default:
                    return;
            }
        }
    };
    @Bind({2131231036})
    RecyclerView mOrderLv;
    private boolean maxIndex;
    @Inject
    OrderPresenter orderPresenter;
    private int orderStatus = -1;
    private int pageIndex;
    @Bind({2131231030})
    PtrClassicFrameLayout ptrClassicFrameLayout;

    public static OrderStateFragment newInstance(int orderStatus2) {
        OrderStateFragment fragment = new OrderStateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ORDER_STATUS", orderStatus2);
        fragment.setArguments(bundle);
        return fragment;
    }

    public BasePresenter getPresenter() {
        return this.orderPresenter;
    }

    /* access modifiers changed from: protected */
    public int getContentViewID() {
        return R.layout.activity_order_state;
    }

    /* access modifiers changed from: protected */
    public void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    /* access modifiers changed from: protected */
    public void initViewsAndEvents(View rootView) {
        this.orderStatus = getArguments().getInt("ORDER_STATUS");
        this.mOrderLv.setLayoutManager(new LinearLayoutManager(this.context));
        this.mAdapter = new OrderStateAdapter(getActivity(), this.mHandler);
        this.mOrderLv.setAdapter(this.mAdapter);
        initEvent();
        refreshData();
    }

    public void onResume() {
        super.onResume();
    }

    private void initEvent() {
        this.ptrClassicFrameLayout.setFooterView(new BioeasyLoadMoreViewFooter());
        this.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            public void onRefreshBegin(PtrFrameLayout frame) {
                OrderStateFragment.this.refreshData();
            }
        });
        this.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void loadMore() {
                OrderStateFragment.this.loadMoreData();
            }
        });
    }

    /* access modifiers changed from: private */
    public void againBuy(Order order) {
        List<OrderItem> orderItems = JSONArray.parseArray(JSON.parseObject(order.getProps()).getJSONArray("items").toJSONString(), OrderItem.class);
        if (orderItems != null) {
            List<ShopCardItem> items = new ArrayList<>();
            for (int i = 0; i < orderItems.size(); i++) {
                OrderItem item = orderItems.get(i);
                ShopCardItem cardItem = new ShopCardItem();
                cardItem.setAmount(item.getAmount().intValue());
                cardItem.setGoods(item.getGoods());
                cardItem.setGoodId(item.getGoodsId().intValue());
                cardItem.setGoodSpecKeyNames(item.getGetGoodsSpecName());
                items.add(cardItem);
            }
            OrderActivity.startAction(getActivity(), items, Double.parseDouble(order.getTotalPrice()));
        }
    }

    /* access modifiers changed from: private */
    public void gotoPay(Order order) {
        PayActivity.startAction(getActivity(), order);
    }

    public static void startAction(Activity activity) {
        activity.startActivity(new Intent(activity, OrderStateFragment.class));
    }

    /* access modifiers changed from: private */
    public void commentOrder(Order order) {
        Intent intent = new Intent(getActivity(), OrderEvaluationActivity.class);
        intent.putExtra(IntentActions.ORDER_INFO, order);
        getActivity().startActivity(intent);
    }

    public void cancelOrder(final Order order) {
        ((BIBaseActivity) getActivity()).showWarnMessage(getString(R.string.order_reminding), getString(R.string.decide_cancel_order), new DialogManager.ConfirmDialogListener() {
            public void clickOk(int actionType) {
                OrderStateFragment.this.orderPresenter.cancelOrder(order);
            }
        }, 666);
    }

    /* access modifiers changed from: private */
    public void viewShipping(Order order) {
        Intent intent = new Intent(getActivity(), OrderExpressActivity.class);
        intent.putExtra(IntentActions.ORDER_INFO, order);
        getActivity().startActivity(intent);
    }

    public void confirmReceive(Order order) {
        this.orderPresenter.confirmOrder(order);
    }

    public void refreshData() {
        this.pageIndex = 1;
        this.maxIndex = false;
        this.orderPresenter.getOrderList(this.orderStatus, this.pageIndex, true);
    }

    public void loadMoreData() {
        if (!this.maxIndex) {
            this.pageIndex++;
            this.orderPresenter.getOrderList(this.orderStatus, this.pageIndex, false);
        }
    }

    public void updateOrderStatus(Order order, int status) {
        if (status == 5) {
            showMessage((int) R.string.order_has_been_cancelled);
        }
        refreshData();
    }

    public void updateOrderList(PageResult<Order> orderList, boolean refresh) {
        if (orderList.getRows().size() > 0) {
            this.mAdapter.addOrders(orderList.getRows());
        } else {
            this.ptrClassicFrameLayout.setLoadMoreEnable(false);
        }
        this.ptrClassicFrameLayout.refreshComplete();
    }
}
