package org.dingyang.service;

import com.google.common.cache.CacheLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCacheLoader extends CacheLoader<Integer, String> {

    @SuppressWarnings({"NullableProblems"})
    @Override
    public String load(Integer key) throws Exception {
        log.info("{} start load cache", Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(3);
        log.info("{} load cache success", Thread.currentThread().getName());
        return String.format("value-%d", key * 1000);
    }
}
