package pl.java.scalatech.web.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AsyncOneController {
    private final AsyncRestTemplate restTemplate = new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());

    @RequestMapping("/async1")
    @ResponseBody
    DeferredResult<String> home() {
        final DeferredResult<String> result = new DeferredResult<>(5000l);
        
        ListenableFuture<ResponseEntity<String>> creditRatingFuture = restTemplate.getForEntity("http://www.google.com", String.class);
        creditRatingFuture.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String>  response) {
                log.info("Success");
                result.setResult(response.getBody());
            }

            @Override
            public void onFailure(Throwable t) {
               result.setErrorResult(t.getMessage());
            }
        });
        return result;
    }
    

    @RequestMapping(value="/sync")
    public String sync() throws InterruptedException {
        Thread.sleep(5000);
        return "Hello!";
    }

    @RequestMapping(value="defered")
    public DeferredResult<String> defered() {
        final DeferredResult<String> result = new DeferredResult<>((long) 9000l);
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result.setResult("Hello from another thread!");
            }
        }.start();

        System.out.println("Started");
        return result;

}
