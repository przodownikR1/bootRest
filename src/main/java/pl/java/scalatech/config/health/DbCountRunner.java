package pl.java.scalatech.config.health;

import java.util.Collection;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile("dbRepoCounter")
public class DbCountRunner implements CommandLineRunner {

    private static final String UNKNOWN_REPOSITORY = "UnknownRepository";
    private static final String PL_JAVA_SCALATECH_REPOSITORY = "pl.java.scalatech.repository";
    
    private Collection<CrudRepository<?,Long>> repositories;

    public DbCountRunner(Collection<CrudRepository<?,Long>> repositories) {
        this.repositories = repositories;
    }

    @Override
    public void run(String... args) throws Exception {
        repositories.forEach(crudRepository -> log.info("{}  has  {}	entries", getRepositoryName(crudRepository.getClass()), crudRepository.count()));
    }

   public static String getRepositoryName(Class<?> crudRepositoryClass) {
        for (Class<?> repositoryInterface : crudRepositoryClass.getInterfaces()) {
            if (repositoryInterface.getName().startsWith(PL_JAVA_SCALATECH_REPOSITORY)) {
                return repositoryInterface.getSimpleName();
            }
        }
        return UNKNOWN_REPOSITORY;
    }
}