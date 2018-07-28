package com.dm.trade.common.cache;

import com.dm.trade.common.thread.CacheScheduledExecutorInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhongchao
 * @title AbstractCache.java
 * @Date 2018-04-12
 * @since v1.0.0
 */
public abstract class AbstractCache<T> {
    private final static ScheduledExecutorService scheduleExecutor = CacheScheduledExecutorInstance.getScheduledExecutorService();

    public static final Logger logger = LoggerFactory.getLogger(AbstractCache.class);

    /**
     * 默认缓存有效时间
     */
    public static Long defaultCacheTime = 1000 * 60 * 3L;

    private Long cacheTime;

    private T t;

    public AbstractCache(Long cacheTime) {
        this.cacheTime = cacheTime;
    }

    public void init() {
        updateCache();
    }

    /**
     * 获取缓存数据
     *
     * @return
     */
    abstract T getCacheData();

    private void updateCache() {
        scheduleExecutor.scheduleAtFixedRate(
                () -> {
                    try {
                        t = getCacheData();
                    } catch (Exception e) {
                        logger.error("定时获取缓存异常", e);
                    }
                }, 0, cacheTime, TimeUnit.MILLISECONDS
        );
    }

    public T getCache() {
        return t;
    }
}
