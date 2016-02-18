package pl.java.scalatech.rate;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.MapMaker;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheTest {
    ConcurrentMap<String, String> names = new MapMaker().concurrencyLevel(2).weakKeys().makeMap();

    @Test
    public void shouldNamesWork() {
        names.put("slawek", "borowiec");
        log.info("{}", names);
    }

    @Test
    @SneakyThrows
    public void shouldCacheWork() {
        LoadingCache<String, String> stringCache = newBuilder().expireAfterWrite(5L, MINUTES).maximumSize(5000L)
                .removalListener(notification -> log.info("++++++++++++ removal")).recordStats().build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        log.info("loaded from cache");
                        return StringService.longRunningTask(key);
                    }
                });

        log.info("key1 : {}", stringCache.get("slawek"));
        log.info("key2 : {}", stringCache.get("slawek"));
        log.info("key3 : {}", stringCache.get("slawek"));
        log.info("key4 : {}", stringCache.get("slawek"));
        log.info("key5 : {}", stringCache.get("slawek"));
        stringCache.put("piotr", "piotr");
        log.info("key6 : {}", stringCache.get("piotr"));
        log.info("key7 : {}", stringCache.get("piotr"));
        stringCache.put("marcin", "spring");
        log.info("key8 : {}", stringCache.get("marcin"));
        log.info("stats : {}", stringCache.stats());
        stringCache.invalidateAll();
    }

    static class StringService {
        @SneakyThrows
        public static String longRunningTask(String key) {
            Thread.sleep(2000);
            log.info("time long ...");
            return "key : " + key;
        }
    }

    @Test
    @SneakyThrows
    public void shouldFunctionWithCacheWork() {
        LoadingCache<String, Integer> loadingCache = newBuilder().build(CacheLoader.from((Function<String, Integer>) input -> input.length()));
        log.info("+++  {}", loadingCache.getIfPresent("Hello Workd")); // null
        // loadingCache.put("World", 67);
        log.info("+++  {}", loadingCache.get("Hello World")); // 5
        log.info("+++  {}", loadingCache.get("Hello World")); // 5
    }

    @Test
    @SneakyThrows
    public void shouldCacheWork2() {
        LoadingCache<String, String> cache = newBuilder().maximumSize(100).build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                log.info("not exists in cache");
                return "value " + Math.abs(new Random().nextInt()) + " key " + key;
            }
        });
        log.info(cache.get("hello"));
        log.info(cache.get("hello"));
        log.info(cache.get("hello"));
        log.info(cache.get("hello1"));
        log.info(cache.get("hello1"));
        log.info(cache.get("hello1"));
    }

}
