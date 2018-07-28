package com.dm.trade.common.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author zhongchao
 * @title CacheScheduledExecutorInstance.java
 * @Date 2018-04-12
 * @since v1.0.0
 */
public class CacheScheduledExecutorInstance {
    /**
     * 默认最大的异步并发线程
     */
    public static final int DEFAULT_THREAD_SIZE = 10;

    private static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(DEFAULT_THREAD_SIZE,
            new BasicThreadFactory.Builder().namingPattern("缓存线程池").daemon(true).build());

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
}
