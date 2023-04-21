package cn.com.bioeasy.healty.app.healthapp.modules.mall.view;

import cn.com.bioeasy.app.base.BaseView;
import cn.com.bioeasy.app.page.PageResult;
import cn.com.bioeasy.healty.app.healthapp.modules.mall.bean.Order;

public interface OrderView extends BaseView {
    void updateOrderList(PageResult<Order> pageResult, boolean z);

    void updateOrderStatus(Order order, int i);
}
