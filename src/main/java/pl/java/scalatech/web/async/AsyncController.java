package pl.java.scalatech.web.async;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.service.user.LongRunningService;

@RestController
@Slf4j
public class AsyncController {

    @Autowired
    private LongRunningService taskService;

    @RequestMapping("/async-task")
    @SneakyThrows
    public String processAsyncTask() {

        long startTime = System.currentTimeMillis();
        Future<String> result = taskService.processAsync("test data...");
        long elapsedTime = System.currentTimeMillis() - startTime;
        return "elapsedTime = " + elapsedTime + "  " + result.get();

    }

    @RequestMapping("/async-callable")
    public Callable<String> processAsyncCallable() {

        Callable<String> callable = () -> taskService.processSync("test data..."+now().format(ofPattern("HH:mm:ss")));

        log.info("Servlet thread released!!!");

        return callable;
    }

    @RequestMapping("/async-deferred")
    public DeferredResult<String> processAsyncDeferred() {

        DeferredResult<String> deferredResult = new DeferredResult<>();

        CompletableFuture.supplyAsync(() -> taskService.processSync("test data..." +now().format(ofPattern("HH:mm:ss")))).whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));

        log.info("Servlet thread released!!!");

        return deferredResult;
    }
    
    @RequestMapping(value = "/block")
    public String executeSlowTask() {
        
        String result = taskService.processSync("test data..." +now().format(ofPattern("HH:mm:ss")));
        log.info("Servlet thread released");

        return result;
    }
}