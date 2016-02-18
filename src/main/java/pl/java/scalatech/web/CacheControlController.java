package pl.java.scalatech.web;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.springframework.http.CacheControl.maxAge;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cache")
public class CacheControlController {
    @RequestMapping("/cache1")
    public ResponseEntity<Void> cacheController() {
        return ResponseEntity.ok().cacheControl(maxAge(20, SECONDS).cachePublic().noTransform()).build();
    }

    @RequestMapping("/cache2")
    public ResponseEntity<Void> cacheController2() {
        return ResponseEntity.ok().cacheControl(maxAge(20, SECONDS).cachePrivate()).build();
    }
}
