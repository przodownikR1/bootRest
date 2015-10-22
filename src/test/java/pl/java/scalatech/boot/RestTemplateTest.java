package pl.java.scalatech.boot;

import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.web.client.RestTemplate;
@SpringBootApplication
public class RestTemplateTest {
    RestTemplate template = new TestRestTemplate();

    @Test
    public void testRequest() throws Exception {
       // HttpHeaders headers = template.getForEntity("/restSample", String.class).getHeaders();

    }
}
