package pl.java.scalatech.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.google.common.collect.Lists;

import pl.java.scalatech.common.WebAppConfigurationAware;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;

// END-TO-END integration test
public class UserControllerIntegrationTest extends WebAppConfigurationAware {
    private final static String URI_ONE = "/users/{userId}";
    @Autowired
    private UserRepository userRepository;

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

    @Override
    protected void createSampleData() {
        Lists.newArrayList("przodownik", "kowalski")
                .forEach(a -> userRepository.save(User.builder().login(a).email(a + "@gmail.com").login(a).enabled(true).build()));

    }

}