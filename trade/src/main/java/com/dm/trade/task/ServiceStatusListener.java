package com.dm.trade.task;

import com.dm.trade.order.dao.OrderCartDao;
import com.dm.trade.order.domain.OrderCartDO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhongchao
 * @title ServiceStatusListener.java
 * @Date 2018-04-15
 * @since v1.0.0
 */
@Component
public class ServiceStatusListener {

    @Resource
    private OrderCartDao orderCartDao;

    @PostConstruct
    public void start() {

    }

    private void statusListener() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    OrderCartDO orderCartDO = orderCartDao.get(1);
                } catch (Exception e) {

                }

            }
        }, 0, 1000 * 60 * 5);
    }
}
