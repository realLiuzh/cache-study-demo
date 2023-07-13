package org.dingyang.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 构造GuavaCache
 */

@SuppressWarnings("NullableProblems")
@Data
@Slf4j
public class GuavaCacheService {

    private final LoadingCache<Integer, String> loadingCache;


    private final Cache<Integer, String> loadingCacheByCallback;

    private final LoadingCache<Integer, String> asyncLoadingCache;


    public GuavaCacheService() {
        loadingCache = CacheBuilder.newBuilder().
                concurrencyLevel(8).
                initialCapacity(10).
                maximumSize(100).
                recordStats().
                expireAfterWrite(60, TimeUnit.SECONDS).
                removalListener(notification -> System.out.printf("%s:%s 被移除，原因:%s", notification.getKey(), notification.getValue(), notification.getCause())).
                build(new GuavaCacheLoader());


        loadingCacheByCallback = CacheBuilder.newBuilder().build();


        asyncLoadingCache = CacheBuilder.newBuilder().
                expireAfterWrite(2, TimeUnit.SECONDS).
                refreshAfterWrite(1, TimeUnit.SECONDS).
                build(CacheLoader.asyncReloading(new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer key) {
                        log.info("[load] start load value");
                        return "load:" + key;
                    }

                    @Override
                    public ListenableFuture<String> reload(Integer key, String oldValue) throws Exception {
                        log.info("[reload] start reload value");
                        return super.reload(key, oldValue);
                    }
                }, new ThreadPoolExecutor(5, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>())));
    }

}
