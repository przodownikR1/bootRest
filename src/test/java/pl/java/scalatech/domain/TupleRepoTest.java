package pl.java.scalatech.domain;

import static org.springframework.data.jpa.domain.Specifications.where;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.config.TupleConfig;
import pl.java.scalatech.domain.poll.Simple;
import pl.java.scalatech.domain.repo.SimpleTupleRepository;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TupleConfig.class)
@ActiveProfiles("test")
@Slf4j
public class TupleRepoTest {
    
    @Autowired    
    SimpleTupleRepository simpleTupleRepository;
    @Test
    public void test(){
        final Long id0 = 10l;
        
        final Specifications<Simple> where = where(
                (root, query, cb) ->
                        cb.lessThan(root.get("id"), id0)
        );
       
    }

}
