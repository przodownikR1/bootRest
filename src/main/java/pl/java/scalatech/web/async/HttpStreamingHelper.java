package pl.java.scalatech.web.async;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class HttpStreamingHelper {

    @Async
    public void body(long wait, ResponseBodyEmitter emitter) throws IOException, InterruptedException {

        TimeUnit.MILLISECONDS.sleep(100);

        emitter.send("Hello!!\r\n");
        TimeUnit.SECONDS.sleep(1);
        emitter.send("Hello again!!\r\n");

        if(wait == 999){
            emitter.completeWithError(new NullPointerException("error."));
            return;
        }
        TimeUnit.SECONDS.sleep(wait);
        emitter.complete();
    }

    @Async
    public void sse(long wait, SseEmitter emitter) throws IOException, InterruptedException {

        TimeUnit.MILLISECONDS.sleep(100);

        SseEmitter.SseEventBuilder builder = emitter.event();
        emitter.send(emitter.event().id(UUID.randomUUID().toString()).data("Hello!"));
        TimeUnit.SECONDS.sleep(1);
        emitter.send(emitter.event().id(UUID.randomUUID().toString()).data("Hello again!"));
        emitter.send(builder);

        if(wait == 999){
            emitter.completeWithError(new NullPointerException("error."));
            return;
        }
        TimeUnit.SECONDS.sleep(wait);
        emitter.complete();
    }

}