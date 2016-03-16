package pl.java.scalatech.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import pl.java.scalatech.common.StandaloneSetupTest;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.web.UserController;

public class SimpleUserControllerTest extends StandaloneSetupTest {
    private final static String URI_SIMPLE = "/users/test";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    public void shouldSimpleTestWork() throws Exception {
        this.mockMvc.perform(get(URI_SIMPLE).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(content().string("ok")).andExpect(status().isOk());
    }

    @Override
    protected Object setControllerToTest() {
        return userController;
    }

}
