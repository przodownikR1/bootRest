package pl.java.scalatech.domain;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static java.time.LocalDateTime.of;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.TestSelectorConfig;
import pl.java.scalatech.domain.poll.Simple;
import pl.java.scalatech.repository.poll.SimpleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSelectorConfig.class)
@ActiveProfiles("test")
@Slf4j
public class RepositoryConverterTest {
    @Autowired
    private SimpleRepository simpleRepository;

    @Test
    public void shouldDateConverterWork() {
        Simple s = Simple.builder().name("przodownik").createDate(LocalDateTime.now()).build();
        Simple loaded = simpleRepository.save(s);
        Assertions.assertThat(loaded.getId()).isNotNull();
        log.info("{}", loaded);
    }

    @Test
    public void convertLocalDateTime() throws Exception {

        final LocalDateTime created = of(2016, Month.FEBRUARY, 1, 6, 46);
        final LocalDateTime modified = of(2016, Month.FEBRUARY, 16, 10, 50);
        Simple entity = new Simple(null, 0l, "przodownik", created, modified);
        Simple loaded = simpleRepository.save(entity);
        log.info("{}", loaded);
    }

    @Test
    public void shouldStreamWork() throws Exception {
        createTestSimple(10);

        try (Stream<Simple> stream = simpleRepository.findAllByQueryWithStream()) {
            stream.map(Simple::getName).forEach(s -> log.info("{}", s));
        }
    }

    @Test
    public void shouldAsyncWork() throws Exception {

        createTestSimple(100000);

        final CompletableFuture<Void> future = simpleRepository.findByVersion(0l).thenAccept(s -> {
            log.info(" Complete!! {}", s.size());
        });

        while (!future.isDone()) {
            log.info(" Waiting...");
            Thread.sleep(1);
        }

        future.get();

        log.info("+++  End!!");
        simpleRepository.deleteAllInBatch();
        
    }

    private void createTestSimple(int i) {
        Stream.generate(new Simple()).limit(i).forEach(s -> simpleRepository.save(s));

    }

}
