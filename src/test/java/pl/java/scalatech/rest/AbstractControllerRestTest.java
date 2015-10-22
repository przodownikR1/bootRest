package pl.java.scalatech.rest;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import pl.java.scalatech.config.JpaWebConfig;


@SpringApplicationConfiguration(classes = JpaWebConfig.class)
@WebAppConfiguration
@IntegrationTest("server.port:9090")
public abstract class AbstractControllerRestTest {
    RestTemplate template = new TestRestTemplate();

    @Autowired
    protected WebApplicationContext webApplicationContext; //without this not mapping controllers

    protected MockMvc mockMvc;



    @Before
    public void getMockMvc() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }


}
