package pl.java.scalatech.common;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.I18nConfig;
import pl.java.scalatech.config.RestConfiguration;
import pl.java.scalatech.config.RestHateoasConfig;
import pl.java.scalatech.config.TestSelectorConfig;
import pl.java.scalatech.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestSelectorConfig.class, WebConfig.class, I18nConfig.class, RestConfiguration.class, RestHateoasConfig.class })
@ActiveProfiles("test")
@WebAppConfiguration
@Slf4j
public abstract class WebAppConfigurationAware {

    @Autowired
    protected WebApplicationContext webApplicationContext;
    
    protected MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        createSampleData();           
    }
    
    abstract protected void createSampleData();        

  

}