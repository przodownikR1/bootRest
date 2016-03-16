package pl.java.scalatech.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.I18nConfig;
import pl.java.scalatech.config.RestConfiguration;
import pl.java.scalatech.config.RestHateoasConfig;
import pl.java.scalatech.config.TestSelectorConfig;
import pl.java.scalatech.config.WebConfig;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { TestSelectorConfig.class, WebConfig.class, I18nConfig.class, RestConfiguration.class, RestHateoasConfig.class })
@ActiveProfiles("test")
@WebAppConfiguration
@Slf4j
//END-TO-END integration test
public class UserControllerIntegrationTest {
    private final static String URI_ONE = "/users/{userId}";
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        createSampleData();
    }

    private void createSampleData() {
        Lists.newArrayList("przodownik", "kowalski")
                .forEach(a -> userRepository.save(User.builder().login(a).email(a + "@gmail.com").login(a).enabled(true).build()));
    }

    @Test
    public void shouldUserControllerSimpleResponse() throws Exception {
        mockMvc.perform(get("/users/test")).andExpect(status().isOk()).andExpect(content().string("ok"));
    }

    @Test    
    // TODO webAppContextSetup + mock concrete repository problem ...it really is not mock repository in RestControllerAbstract but controller take real
    // repository object.
    public void shouldFindUserById() throws Exception {
        // @formatter:off
         mockMvc.perform(get(URI_ONE, 1).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.login").value("przodownik"))
        .andExpect(jsonPath("$.email").value("przodownik@gmail.com"));
        // @formatter:on
    }

}