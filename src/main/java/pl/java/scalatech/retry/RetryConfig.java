package pl.java.scalatech.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@ComponentScan(basePackageClasses=OrdinaryBean.class)
@EnableRetry
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
      SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
      retryPolicy.setMaxAttempts(5);

      FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
      backOffPolicy.setBackOffPeriod(500);

      RetryTemplate template = new RetryTemplate();
      template.setRetryPolicy(retryPolicy);
      template.setBackOffPolicy(backOffPolicy);

      return template;
    }



}
