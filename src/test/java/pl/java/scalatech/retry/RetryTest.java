package pl.java.scalatech.retry;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RetryConfig.class)
@Slf4j
public class RetryTest {

    @Autowired
    private OrdinaryBean bean;

    @Autowired
    private RetryTemplate template;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void shouldRetry(){
        thrown.expect(ExhaustedRetryException.class);
        assertThat(bean).isNotNull();
        bean.print();
    }
    @Test
    public void shouldMyExceptionHandle(){
        thrown.expect(ExhaustedRetryException.class);
        assertThat(bean).isNotNull();
        bean.printEx();
    }

    @Test
    public void shouldTemplateWork(){
        thrown.expect(MyShittyException.class);
        template.execute(context -> {
            return bean.printTestTemplate();

        });
    }

}
