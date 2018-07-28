package com.dm.trade.payment.miniWechatPay;

/**
 * description:
 * Created by zhangle on 2016/12/22.
 * qq:475166369
 */
public class PayConstant {
    /**
     * 设备号
     */
    public final static String DEVICE_INFO = "WEB";
    /**
     * 签名类型
     */
    public final static String SIGN_TYPE = "MD5";
    /**
     * 商品描述
     */
    public final static String BODY = "儿童医院微信小程序挂号";
    /**
     * 货币类型
     */
    public final static String FEE_TYPE = "CNY";
    /**
     * 通知地址
     */
    public final static String NOTIFY_URL = "https://wx.cqkqinfo.com/applet/noticeController/wx/notice";

    /****************************住院预存*******************************/
    /**
     * 商品描述
     */
    public final static String HOS_BODY = "儿童医院微信小程序住院预存";
    /**
     * 通知地址
     */
    public final static String HOS_NOTIFY_URL = "https://wx.cqkqinfo.com/applet/noticeController/wx/hosNotice";

    /****************************门诊缴费*******************************/
    /**
     * 商品描述
     */
    public final static String OUT_BODY = "儿童医院微信小程序门诊缴费";
    /**
     * 通知地址
     */
    public final static String OUT_NOTIFY_URL = "https://wx.cqkqinfo.com/applet/noticeController/wx/outNotice";


    /****************************收银台*******************************/
    public final static String SYT_APPID = "wx5ece3985f87ab8e3";

    public final static String SYT_MIC_ID = "1287487001";

    public final static String SYT_SUB_APPNAME = "chcmu";

    public final static String SYT_PAY_TYPE = "wechatPay";

    public final static String SYT_PAY_CHANNEL = "chcmu_wechat_app";
    /**
     * 收银台支付通知
     */
    public final static String ORDER_PAY_URL = "http://wx.cqkqinfo.com/wechat/sync/payOrder/save";
    /**
     * 收银台退款通知
     */
    public final static String ORDER_REFOUND_URL = "http://wx.cqkqinfo.com/wechat/sync/refundOrder/save";
}
