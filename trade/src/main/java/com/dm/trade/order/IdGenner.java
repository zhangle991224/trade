package com.dm.trade.order;

/**
 * @author zhongchao
 * @title IdGenner.java
 * @Date 2018-06-10
 * @since v1.0.0
 */
public class IdGenner {

    /**
     * 订单号（生成规则：取系统配置文件的instance号2位+12位currentTimeMillis/10(10毫秒)+2位流水号）
     *
     * @return
     */
    private static int i = 0;
    //    private static String instanceId = PropertiesUtil.getInstance().getProp(
//            "Instance.ID");
    private static String instanceId = "1";

    public static String getInstanceId() {
        return instanceId;
    }

    private static void setInstanceId(String instanceId) {
        if (instanceId == null || instanceId.length() != 2) {
            throw new IllegalArgumentException(
                    "Set instanceId error: the length should be 2 '"
                            + (instanceId == null ? "" : instanceId)
                            + "' is not correct.");
        }
        IdGenner.instanceId = instanceId;
    }

    /**
     * @return : String
     * @DESCRIPION :每10毫秒可生成100个序列号；优于每毫秒10个序列号
     * @Create on: 2013-4-16 下午5:27:05
     * @Author : "Jack"
     */
    public synchronized static Long genOrdId16() {
        i = i % 100;
        String index = (i < 10) ? ("0" + i) : "" + i;
        String orderNum = instanceId + System.currentTimeMillis() / 10 + index;
        i++;
        return Long.valueOf(orderNum);
    }
}
