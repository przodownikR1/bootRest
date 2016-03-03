package pl.java.scalatech.web.async;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProcessImpl implements Process {
    @Async
    @Override
    @SneakyThrows
    public Future<Void> async(String data) {
        Thread.sleep(5000L);
        log.info("processing done");
        return new AsyncResult<>(null);
    }

    @Override
    @SneakyThrows
    public String sync(String data) {

        Thread.sleep(5000L);

        log.info("processing done");
        return "data_processed";
    }

}
