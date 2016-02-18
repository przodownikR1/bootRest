package pl.java.scalatech.web.rate;

import java.util.LongSummaryStatistics;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.util.concurrent.RateLimiter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;

@RestController
@RequestMapping("/api/rate")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RateLimiterController {

    private final @NonNull UserRepository userRepository;
    
    RateLimiter rateLimiter = RateLimiter.create(10);
    
    private LongSummaryStatistics summaryStatistics = new LongSummaryStatistics();
    
    @RequestMapping("/rates")
    public String rates() {
        return summaryStatistics.toString();
    }
    @SneakyThrows
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> optUser = Optional.of(userRepository.findOne(id));
        User userLoaded = null;
        if(optUser.isPresent()){
            userLoaded = optUser.get();
            summaryStatistics.accept(userLoaded.getId());
        }
        log.info("++++  acquire {}",rateLimiter.tryAcquire());
        if (!rateLimiter.tryAcquire()) {
            log.error("Too many simultaneous requests per second - rejected request for given userId: " + userLoaded.getId());
            throw new TooManyRequestsException();
        }
        log.info("++++ getRate {}",rateLimiter.getRate());
        return optUser.map(user -> ResponseEntity.ok(user)).orElse(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }
    
}
