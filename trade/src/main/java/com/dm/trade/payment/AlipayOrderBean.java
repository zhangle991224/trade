package com.dm.trade.payment;

/**
 * @author zhongchao
 * @title AlipayOrderBean.java
 * @Date 2017-12-13
 * @since v1.0.0
 */
public class AlipayOrderBean extends AbstractOrderBean {

    /**
     * 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
     */
    private String body;

    /**
     * 商品的标题/交易标题/订单标题/订单关键字等。
     */
    private String subject;

    /**
     * 商户网站唯一订单号
     */
    private String out_trade_no;

    /**
     * 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
     */
    private String total_amount;

    /**
     * 销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
     */
    private String product_code;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
}
