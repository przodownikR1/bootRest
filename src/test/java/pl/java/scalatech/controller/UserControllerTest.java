package pl.java.scalatech.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.java.scalatech.config.TestSelectorConfig;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.web.UserController;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSelectorConfig.class})
@ActiveProfiles("test")
public class UserControllerTest {
        private final static String URI_ONE = "/users/1";
        private MockMvc mockMvc;
        
        @Mock
        private UserRepository userRepository;
        
        /*@InjectMocks // or like this in constuctor 
        private UserController userController;*/
        
        private UserController userController ;
        
        @Before
        public void setUp() {
            MockitoAnnotations.initMocks(this);
            userController =new UserController(userRepository);
            mockMvc = standaloneSetup(userController).build();
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
