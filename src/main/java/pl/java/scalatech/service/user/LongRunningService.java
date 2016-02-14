package pl.java.scalatech.service.user;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LongRunningService {

    @Async

    public Future<String> processAsync(String data) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        log.info("done ....{}" ,now().format(ofPattern("HH:mm:ss")));
        return new AsyncResult<>(data);
    }

    public String processSync(String data) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        log.info("....done {}",now().format(ofPattern("HH:mm:ss")));
        return " data_processed " + data + " "+now().format(ofPattern("HH:mm:ss"));
    }
}
