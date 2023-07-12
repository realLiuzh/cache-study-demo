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


    @Before
    public void init() {
        GuavaCacheService guavaCacheService = new GuavaCacheService();
        loadingCache = guavaCacheService.getLoadingCache();
        loadingCacheByCallable = guavaCacheService.getLoadingCacheByCallback();
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
}
