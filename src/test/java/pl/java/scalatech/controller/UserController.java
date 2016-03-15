package pl.java.scalatech.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.java.scalatech.config.RestConfiguration;
import pl.java.scalatech.config.RestHateoasConfig;
import pl.java.scalatech.config.WebConfig;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class,RestConfiguration.class,RestHateoasConfig.class })
@WebAppConfiguration
public class UserController {
        private final static String URI_ONE = "users/1";
        private MockMvc mockMvc;
        
        @Autowired
        private WebApplicationContext webApplicationContext;
        
        @Autowired
        private UserRepository userRepository;
        
        @Before
        public void setUp() {
            MockitoAnnotations.initMocks(this);
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
            when(userRepository.findOne(1l)).thenReturn(User.builder().login("przodownik").email("przodownikR1@gmail.com").build());
          
        }
        @Test
        public void shouldFindUserById() throws Exception{
            this.mockMvc.perform(get(URI_ONE, 1).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.login").value("przodownik"))
            .andExpect(jsonPath("$.email").value("przodownikR1@gmail.com"));
        }
        
    
}
