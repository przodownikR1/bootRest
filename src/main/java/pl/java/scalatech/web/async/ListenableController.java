package pl.java.scalatech.web.async;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ListenableController {
    private final AsyncRestTemplate restTemplate = new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());
    @RequestMapping("/listenable")
    @ResponseBody DeferredResult<String> home() {
        final DeferredResult<String> result = new DeferredResult<>();
        
        ListenableFuture<ResponseEntity<String>> future = blockingMethod();
        future.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String> response) {               
                log.info("Success");
                result.setResult(response.getBody());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setErrorResult(t.getMessage());
                log.info("Failure.....");
            }
        });
        return result;
    }
    @SneakyThrows    
    public  ListenableFuture<ResponseEntity<String>> blockingMethod(){        
        Thread.sleep(5000);
        return restTemplate.getForEntity("http://www.google.com", String.class);
    }
    
}
