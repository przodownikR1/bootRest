package pl.java.scalatech.config;

import java.util.Random;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthConfig {
    @Bean
    HealthIndicator myHealthIndicator(){
        return () -> {
            if(new Random().nextBoolean()){
                return Health.up().status("ok").build();
            }
            return Health.down().status("problem").withDetail("my", "przodownik").build();
        };
        
    }
    
    
    
}
