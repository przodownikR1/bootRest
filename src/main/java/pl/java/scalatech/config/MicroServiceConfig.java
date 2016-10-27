package pl.java.scalatech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import pl.java.scalatech.collaboration.filter.CorrelationIdFilter;


@Configuration
public class MicroServiceConfig {

    @Bean
    @Profile("micro")
    CorrelationIdFilter collerationIdFilter() {
        return new CorrelationIdFilter();
    }
    
}
