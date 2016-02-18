package pl.java.scalatech.rate;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static java.util.concurrent.Executors.newCachedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Concurrency {

    @SneakyThrows
    public String longRunning1() {
        Thread.sleep(5000);
        return "hello, ";
    }

    @SneakyThrows
    public String longRunning2() {
        Thread.sleep(2000);
        return "world!";
    }

    public String sequential() {
        String one = longRunning1();
        String two = longRunning2();
        return one + two;
    }

    @SneakyThrows
    public Optional<String> parallelProcessing() {
        Optional<String> result = absent();
        ExecutorService exec = newCachedThreadPool();
        Future<String> one = exec.submit(() -> longRunning1());
        Future<String> two = exec.submit(() -> longRunning2());
        result = Optional.of(one.get() + two.get());
        return result;
    }

    @SneakyThrows
    public Optional<String> listenableFutures() {
        Optional<String> result = absent();

        ListeningExecutorService exec = MoreExecutors.listeningDecorator(newCachedThreadPool());
        ListenableFuture<String> future = exec.submit(() -> longRunning1());
        //future.addListener(() -> log.info("Done...."), exec);

        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                log.info("Done!  {}", result);
            }

            @Override
            public void onFailure(Throwable t) {
                log.info("Failed. Logging and sounding siren");
            }
        });
       
        result = of(future.get());
        return result;
    }

}