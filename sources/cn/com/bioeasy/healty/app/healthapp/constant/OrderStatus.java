package cn.com.bioeasy.healty.app.healthapp.constant;

public interface OrderStatus {
    public static final int ALL = -1;
    public static final int CANCEL = 5;
    public static final int COMMENT = 3;
    public static final int CREATED = 0;
    public static final int END = 4;
    public static final int IN_EXPRESS = 2;
    public static final String[] ORDER_NAMES = {"待支付", "待发货", "待收货", "待评价", "已完成", "已取消", "已退货"};
    public static final int PAYED = 1;
    public static final int REFUND = 6;
}
