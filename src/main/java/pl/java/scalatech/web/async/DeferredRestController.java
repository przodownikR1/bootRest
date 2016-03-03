package pl.java.scalatech.web.async;

import static org.springframework.http.HttpStatus.OK;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.base.Stopwatch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DeferredRestController {
    private static final ScheduledExecutorService es = Executors.newScheduledThreadPool(10);
    @Autowired
    private Process process;

    @RequestMapping("/sync-task")
    public String processAsyncTask() {

        Stopwatch sw = Stopwatch.createStarted();
        process.sync("test data...");
        sw.stop();
        return "elapsedTime = " + sw.elapsed(TimeUnit.MILLISECONDS);

    }

    @RequestMapping("/async-callable")
    public Callable<String> processAsyncCallable() {
        Callable<String> callable = () -> process.sync("test data...");
        log.info("Servlet thread released!!!");
        return callable;
    }

    @RequestMapping("/async-deferred")
    public DeferredResult<String> processAsyncDeferred() {

        DeferredResult<String> deferredResult = new DeferredResult<>();

        CompletableFuture.supplyAsync(() -> process.sync("test data...")).whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));

        log.info("Servlet thread released!!!");

        return deferredResult;
    }

    @RequestMapping("/scheduled")
    DeferredResult<String> home() {
        DeferredResult<String> res = new DeferredResult<>();
        es.schedule(() -> res.setResult("Spring"), 2000, TimeUnit.MILLISECONDS);
        return res;
    }

    @RequestMapping(value = "/blocking", method = RequestMethod.GET)
    @SneakyThrows
    public HttpEntity<String> testBlockingResult() {

        Thread.sleep(2_000);

        return new ResponseEntity<>("body string", HttpStatus.OK);
    }

    @RequestMapping(value = "/nonBlocking", method = RequestMethod.GET)
    public DeferredResult<ResponseEntity<String>> testNonBlockingResult() {

        DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();

        new Thread(() -> {

            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            deferredResult.setResult(new ResponseEntity<>("body string", OK));

        }

        ).start();

        return deferredResult;
    }

}
