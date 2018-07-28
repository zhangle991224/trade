package com.dm.trade.common.config.constant;

/**
 * @author zhongchao
 * @title OrderConstant.java
 * @Date 2018-04-15
 * @since v1.0.0
 */
public class OrderConstant {

    /**
     * 散装
     */
    public static final Integer UNIT_BULK = 0;

    /**
     * 整装
     */
    public static final Integer UNIT_CONTAINER = 1;

    /**
     * 未支付
     */
    public static final Integer PAID_NO = 0;

    /**
     * 已支付
     */
    public static final int PAID = 1;

    /**
     * 已过期
     */
    public static final int PAID_LOSE = 2;

    /**
     * 支付宝支付
     */
    public static final Integer ALI_PAY = 1;

    /**
     * 微信支付
     */
    public static final Integer WEI_PAY = 2;

    /**
     * 线下支付
     */
    public static final Integer OFFLINE_PAY = 3;

    /**
     * 下单未支付
     */
    public static final int ORDER_STATUS_CREATE = 0;

    /**
     * 等待派送
     */
    public static final int ORDER_STATUS_WAIT_SEND = 1;

    /**
     * 派送中
     */
    public static final int ORDER_STATUS_SENDING = 2;

    /**
     * 已收货
     */
    public static final int ORDER_STATUS_ACCEPT = 3;


    /**
     * 需要支付
     */
    public static final Integer NEED_PAY = 1;

    /**
     * 不需要支付
     */
    public static final Integer NOT_NEED_PAY = 0;
}
