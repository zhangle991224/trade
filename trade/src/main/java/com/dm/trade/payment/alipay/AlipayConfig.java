package com.dm.trade.payment.alipay;

/**
 * Created by Intellij idea
 * User: liu.y
 * Date: 2017/12/12
 * Description:
 * To change this template use File | Setting | File and Code Templates
 */
public class AlipayConfig {

    public static final String URL = "https://openapi.alipay.com/gateway.do";
    public static final String APP_ID = "2017120900493692";
    public static final String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDukrSiDehbFyZq48mUjFono/k4FKXTHLi2TfzL7zuYul2fBLp11da4buRawVP09shnuJdvOgHFgUCQJrsxUyeSa8uJX/YEa/tbuitDtiQQ1sQ710PyBbIhzrRxCG/1vWnwl8T3ZeEftyUECEokNynx9mKBb3iZUYjpSItO/9DEqs7IxUKosY/AmYkiAdGZ57a+8zE1RDqW9TuGrLdkv5ZfS0+Xr+So4EhPr8aaxHWbZvEihb7c6rF9lTap5QwM/BRT9efdgIDbMoXnZ9Ox2BhMvIcr+zl9LI+Lh6uRti2ufDLagxGBvPoyNlmqW7xeoGUlPGFO1hTCnDEVrrDV66jAgMBAAECggEAR7jP8m9ucrrQdG8fowrPkhn7WO/2qCBc0TzaJNPSN62tVfDaT24LRxVvDg9KNhuwzU54C/fGEc2gboiEkfQe8nRGKIcMY7XFpy5jC47wxSOXWtIpRmV4WjifAwWaJpzA5lWDdQqK6/4y1X8uvqqZKdU7kdXsfG8N0Gyh/iUTqA2dgu2pZ8cUgA6iH0Easuz0d7UChO49MeDYYMwYcBCUaXBCnmK0mZaphq2jD/QuRvbC4IjAYNl8S35YT44sXREwYO57d0vcxMFgiNssKQI+ZlyprBPMS6ke+1PxWo0XV/mOEYoZ/JepLICowcp+OCz7k3MS0jnaE05VQUWJO/ILsQKBgQDo0AcgLK7OfMNdhj2rjJBzmeORnQEoN6Et+lMhWdUSECfsruldqHgJ6WPelmOaOyKmzNqQeaU+G8tJ9fPk7NvtHFzhLLIAMWbY6/AWKF1J8RBiopC1mRey/yLmvyOHj+48uoBN/nhM1f+IG9TJWFqTdJrbRTyKdkYaWydMQe5B6wKBgQCQ2Oy7pEovtcTpZYpNk/okChQtDSX5hzd/ScgPz0BIk4n7lt/ZVfRQ7wrAvJFl+nWb8mae95DnaOJ9i9v4BAiwwH1qllODjG59p+GVxob2Egs3ECIC5V8IlX6XJ6WH8OrlmL4ZLDfz/NsWHZZ7SzQQIL8/qGXaeT/f7FjYXaVgKQKBgGFy8gidq23y+zOihR3RcIVy3LaOqXVUE7tTFIjZOT6eVJfVfwkdA2Hit1LsUDS2nAKFrNbclDYzHByRPs2L92KAhMAcHUXHZ+1UlOIxcxsINHuU4E2jDzB1av568zxDGs2Ao0QCSAihapxmSeMdgIxIOqmdiYHLj/xoO0LINhqRAoGAOpo5NTWj+ieNcmxthzOOaZRWU1/VdF47mtksAxFzzak7kzmYeWLoRPJhpFbmcJ7jhGOPF1PMIYPy1p6GMZLYPUKzuzL0uO1RKq+IekNnCRmFxhJV4FwSlfN90wPsWdKkBp5EkwSr4p/zt7/iySFFAB1vov6rrreNmzgRPgCtTvkCgYB/oVH2K0jpyT1ud7jtucdBLTc5xe8ZFhIOWd93UriGLQjyQdnwRFwxU6/bw9BQmb0HsTmjvywL7xzBiuv4ZmPnpmBJAw87YAkIKDqiuasR5NaKyVqRdTdc0eGLL3BSg9CzjPLQ6ZmEz5YCyeT5Yh2dqqkmY/iMB3JCfqMF6BMkkA==";
    public static final String FORMAT = "json";
    public static final String CHARSET = "UTF-8";
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu2bp0UqJUac0xs7oXfXmYWrOaV9M2XB73SBxSHXefp9uRqsmyJ0QwwheViDbW82QL1JAAyu/BIp2xKE4KMU1QbDXr2ez1F489LctycI0zbtRC510RGLHLQ9aG3pnkQQDAWUANyHwWE5hmk4CWVhikFLE2q1O9pD3BEte1f8J1BAZGcn8sSFYbNE90wD8gEkghsLSDz5XbDjv4UJFsCOArTkw8pDMZNIBKbDRdjMfopyl/3MQy18e9ZccCchQEpMaHMNR1l9la6DxYh9lG5e1oh4D4RZTKJ3R+StkUiiPpT6VbBVSoAe44B9yd8Aul5B7Di92enz5xAxpNx7qSlroqQIDAQAB";


    public static final String SIGN_TYPE = "RSA2";

    public static final String ALIPAY_ASNYC = "";

    /**
     * 商户交易号 --验证
     */
    public static final String OUT_TRADE_NO = "out_trade_no";

    /**
     * 交易状态
     */
    public static final String TRADE_STATUS = "trade_status";
}

enum TradStatusEnum {
    /**
     * 交易创建，等待买家付款
     */
    WAIT_BUYER_PAY,
    /**
     * 未付款交易超时关闭，或支付完成后全额退款
     */
    TRADE_CLOSED,
    /**
     * 交易支付成功
     */
    TRADE_SUCCESS,
    /**
     * 交易结束，不可退款
     */
    TRADE_FINISHED;
}
