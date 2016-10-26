package pl.java.scalatech.config;

import java.util.Random;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import pl.java.scalatech.generator.UserGenerate;

@Configuration
@EnableScheduling
public class SimpleGeneratorConfig {

    
    private final UserGenerate userGenerate;
    private final Random random;
    
    public SimpleGeneratorConfig(UserGenerate userGenerate,Random random) {
     this.userGenerate = userGenerate;
     this.random = random;
    }
    
    
    @Scheduled(fixedDelay=6000)
    public void scheduler(){
        random.ints(1,1000).limit(random.nextInt(1000)).parallel().forEach(value -> userGenerate.generateSingleUser());        
    }
    

}
