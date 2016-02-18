package pl.java.scalatech.rate;

import static java.util.concurrent.Executors.newCachedThreadPool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskTest {
    RateLimiter rateLimiter = RateLimiter.create(20.0);

    @Test
    @SneakyThrows
    public void test() {
        ExecutorService executorService = newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Task(rateLimiter));
        }
        Thread.sleep(5000);
    }

    static class Task implements Runnable {
        private RateLimiter rateLimiter;

        public Task(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
        }

        @Override
        public void run() {
            while (true) {
                rateLimiter.acquire();
                log.info("Thread id : {} ,  get request :{} ", Thread.currentThread(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                log.info("{}" + rateLimiter.tryAcquire());
            }
        }
    }
}