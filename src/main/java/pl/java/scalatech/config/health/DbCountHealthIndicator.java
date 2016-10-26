package pl.java.scalatech.config.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Profile("dbRepoCounter")
public class DbCountHealthIndicator implements HealthIndicator {
    
    private static final String COUNT_CONST = "count";
    private final CrudRepository<?,Long> repository;

    public DbCountHealthIndicator(CrudRepository<?,Long> repository) {
        this.repository = repository;
    }

    @SuppressWarnings("boxing")
    @Override
    public Health health() {
        try {
            long count = repository.count();
            if (count >= 0) {
                return Health.up().withDetail(COUNT_CONST, count).build();
            }
            return Health.unknown().withDetail(COUNT_CONST, count).build();
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}