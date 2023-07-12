package org.dingyang.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 构造GuavaCache
 */

@Data
@Slf4j
public class GuavaCacheService {

    private final LoadingCache<Integer, String> loadingCache;


    private Cache<Integer, String> loadingCacheByCallback;


    public GuavaCacheService() {
        loadingCache = CacheBuilder.newBuilder().
                concurrencyLevel(8).
                initialCapacity(10).
                maximumSize(100).
                recordStats().
                expireAfterWrite(60, TimeUnit.SECONDS).
                removalListener(notification -> System.out.printf("%s:%s 被移除，原因:%s\n", notification.getKey(), notification.getValue(), notification.getCause())).
                build(new GuavaCacheLoader());


        loadingCacheByCallback = CacheBuilder.newBuilder().build();

    }

}
