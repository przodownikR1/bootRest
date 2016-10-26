package pl.java.scalatech.config.health;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.HealthAggregator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Configuration
@Profile("dbRepoCounter")
public class DbCountAutoConfiguration {

    @Autowired
    private HealthAggregator healthAggregator;

    @Bean
    public HealthIndicator dbCountHealthIndicator(Collection<CrudRepository<?,Long>> repositories) {
        CompositeHealthIndicator compositeHealthIndicator = new CompositeHealthIndicator(healthAggregator);
        for (CrudRepository<?,Long> repository : repositories) {
            String name = DbCountRunner.getRepositoryName(repository.getClass());
            compositeHealthIndicator.addHealthIndicator(name, new DbCountHealthIndicator(repository));
        }
        return compositeHealthIndicator;
    }
}
