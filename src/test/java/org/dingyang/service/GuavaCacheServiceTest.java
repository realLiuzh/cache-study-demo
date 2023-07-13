package org.dingyang.service;

import com.google.common.cache.Cache;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Slf4j
public class GuavaCacheServiceTest {

    private LoadingCache<Integer, String> loadingCache;
    private Cache<Integer, String> loadingCacheByCallable;

    private LoadingCache<Integer, String> asyncLoadingCache;


    @Before
    public void init() {
        GuavaCacheService guavaCacheService = new GuavaCacheService();
        loadingCache = guavaCacheService.getLoadingCache();
        loadingCacheByCallable = guavaCacheService.getLoadingCacheByCallback();
        asyncLoadingCache = guavaCacheService.getAsyncLoadingCache();
    }


    @Test
    public void test_loadingCache() {
        try {
            Integer key = 594;
            String value = loadingCache.get(key);
            log.info("key:{},value:{}", key, value);
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Test
    public void test_loadingCacheCallable() {
        try {
            Integer key = 594;
            String value = loadingCacheByCallable.get(key, () -> {
                TimeUnit.SECONDS.sleep(3);
                return String.valueOf(key * 1000);
            });
            log.info("key:{},value:{}", key, value);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void test_reload_value() {
        asyncLoadingCache.put(594, "test_reload");
        try {
            log.info("{}:{}", 594, asyncLoadingCache.get(594));
            TimeUnit.SECONDS.sleep(3);
            log.info("{}:{}", 594, asyncLoadingCache.get(594));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void test_refresh_value(){
        asyncLoadingCache.put(594, "test_refresh");
        try {
            log.info("{}:{}", 594, asyncLoadingCache.get(594));
            TimeUnit.SECONDS.sleep(3);
            log.info("{}:{}", 594, asyncLoadingCache.get(594));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
