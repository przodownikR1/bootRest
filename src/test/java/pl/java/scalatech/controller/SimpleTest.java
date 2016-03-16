package pl.java.scalatech.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.java.scalatech.RestCommonTest.mockMvc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.java.scalatech.RestCommonTest;
import pl.java.scalatech.config.TestSelectorConfig;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.web.UserController;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSelectorConfig.class})
public class SimpleTest {
    private final static String URI_SIMPLE = "/users/test";
    private MockMvc mockMvc;
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserController userController;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = mockMvc(userController);
        when(userRepository.findOne(1l)).thenReturn(User.builder().login("przodownik").email("przodownikR1@gmail.com").build());
      
    }
    
    @Test
    public void shouldSimpleTestWork() throws Exception{
        this.mockMvc.perform(get(URI_SIMPLE).accept(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(content().string("ok")).andExpect(status().isOk());
        
    }
    
  
}
