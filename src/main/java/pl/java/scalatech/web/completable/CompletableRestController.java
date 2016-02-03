package pl.java.scalatech.web.completable;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
public class CompletableRestController {
    final BlockingQueue<CompletableFuture<String>> q = new ArrayBlockingQueue<>(100);
    

    @RequestMapping("/completable")
    CompletableFuture<String> hello() {
        CompletableFuture<String> future = new CompletableFuture<>();
        q.add(future);
        return future;
    }

    @Scheduled(initialDelay = 1_000, fixedDelay = 3_000)
    void emit() throws IOException {
        for (int i = 0; i < 5; i++) {
            CompletableFuture<String> future = q.poll();
            if (future == null) return;
            log.info("___!!!!____"+now().format(ofPattern("HH:mm:ss")));
            future.complete("Hello! "+now().format(ofPattern("HH:mm:ss")));
        }
    }
  }
