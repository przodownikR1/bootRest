package pl.java.scalatech.config.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    public CacheManager productsCacheManager() {
        final GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(CacheBuilder
                .newBuilder()
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .maximumSize(10));
        return cacheManager;
    }

}