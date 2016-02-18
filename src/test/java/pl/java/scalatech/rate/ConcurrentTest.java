package pl.java.scalatech.rate;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.base.Optional;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.time.TimedTest;

@Slf4j
public class ConcurrentTest {
    @Rule
    public TimedTest time = new TimedTest();
    
    private Concurrency concurrency = new Concurrency();
    
    @Test
    public void oldTest(){
          log.info("+++ {}",concurrency.sequential());        
    }
    @Test
    public void parallelTest(){
      Optional<String> str = concurrency.parallelProcessing();
      log.info("+++ {}",str.get());
    }
    @Test
    public void listenerTest(){
          Optional<String> str = concurrency.listenableFutures();
          log.info("+++ str",str.get());
    }
}
