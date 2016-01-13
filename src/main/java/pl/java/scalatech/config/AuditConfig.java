package pl.java.scalatech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import pl.java.scalatech.audit.SpringSecurityAuditorAware;
import pl.java.scalatech.domain.User;

@Configuration
public class AuditConfig {
    @Bean
    public AuditorAware<User> auditorAware() {
        return new SpringSecurityAuditorAware();
    }

}
