package com.dm.trade.common.config;

public class Constant {
    //自动去除表前缀
    public static String AUTO_REOMVE_PRE = "true";
    //停止计划任务
    public static String STATUS_RUNNING_STOP = "stop";
    //开启计划任务
    public static String STATUS_RUNNING_START = "start";
    //通知公告阅读状态-未读
    public static String OA_NOTIFY_READ_NO = "0";
    //通知公告阅读状态-已读
    public static int OA_NOTIFY_READ_YES = 1;


    /**
     * token
     */
    public static final String TOKEN = "tk";

    /**
     * 时间戳
     */
    public static final String TIMEMILLIS = "mil";

    /**
     * customer
     */
    public static final String USER = "customer";

    /**
     * userId 简称
     */
    public static final String USERID = "uid";


    /**
     * 失效时间 name
     */
    public static final String FAILURE_NAME = "fn";

    /**
     * 验证码 code
     */
    public static final String V_CODE = "v_code";

    /**
     * 移动号段正则表达式
     */
    public static final String CM_NUM = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";

    /**
     * 联通号段正则表达式
     */
    public static final String CU_NUM = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";


    /**
     * 电信号段正则表达式
     */
    public static final String CT_NUM = "^((133)|(153)|(177)|(173)|(18[0,1,9]))\\d{8}$";

    /**
     * 通用电话号码正则
     */
    public static final String COMMON = "^((13[0-9])|(16[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(17[7])|(18[0,0-9]))\\d{8}$";

    /**
     * 支付宝支付
     */
    public static final Integer ALIPAY = 1;

    /**
     * 微信支付
     */
    public static final Integer WEIXINPAY = 2;


    /**
     * 未支付
     */
    public static final Integer NOT_PAY = 1;

    /**
     * 已支付
     */
    public static final Integer PAIED = 2;

//    /**
//     * 资源文件地址
//     * todo
//     */
//    public static final String QINIU_HOST = "http://rms.&.com/";
    /**
     * 资源文件地址
     */
    public static final String QINIU_HOST = "http://p90gcwbel.bkt.clouddn.com/";


}
