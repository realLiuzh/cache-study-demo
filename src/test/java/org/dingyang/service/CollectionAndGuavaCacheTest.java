package org.dingyang.service;

import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;


@SuppressWarnings("InfiniteLoopStatement")
@Slf4j
public class CollectionAndGuavaCacheTest {

    private ConcurrentHashMap<Integer, String> collectionCache;

    private LoadingCache<Integer, String> guavaCache;

    @Before
    public void init() {
        collectionCache = new ConcurrentHashMap<>();
        guavaCache = new GuavaCacheService().getLoadingCache();
    }

    /**
     * VM options:-Xms24m -Xmx24m
     * java.lang.OutOfMemoryError: GC overhead limit exceeded
     */
    @Test
    public void test_collection_heap_overflow() {
        int count = 0;
        try {
            while (true) {
                collectionCache.put(count++, "HeapOverflowDemo");
                log.info("count:{}", count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 由于内存淘汰策略的缘故，不存在堆内存溢出异常
     */
    @Test
    public void test_guava_cache_overflow() {
        int count = 0;
        try {
            while (true) {
                guavaCache.put(count++, "HeapOverflowDemo");
                log.info("count:{}", count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
