package pl.java.scalatech.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import pl.java.scalatech.common.StandaloneSetupTest;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.web.UserController;

public class UserContollerInjectMockTest extends StandaloneSetupTest {
    private final static String URI_ONE = "/users/{userId}";
   
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    public void shouldFindUserById() throws Exception {
        when(userRepository.findOne(1l)).thenReturn(User.builder().login("przodownik").email("przodownikR1@gmail.com").build());
        this.mockMvc.perform(get(URI_ONE, 1).accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.login").value("przodownik"))
                .andExpect(jsonPath("$.email").value("przodownikR1@gmail.com"));
        verify(userRepository, times(1)).findOne(1l);
        verifyNoMoreInteractions(userRepository);
    }

    @Override
    protected Object setControllerToTest() {
        return userController;
    }

}
