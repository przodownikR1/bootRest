package pl.java.scalatech.domain;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void shouldDateConverterWork(){
        Simple s = Simple.builder().name("przodownik").createDate(LocalDateTime.now()).build();
        Simple loaded = simpleRepository.save(s);
        Assertions.assertThat(loaded.getId()).isNotNull();
        log.info("{}",loaded);  
    }
    
    
}
