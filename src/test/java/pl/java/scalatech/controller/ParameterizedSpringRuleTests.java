package pl.java.scalatech.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collection;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.common.StandaloneSetupTest;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.web.UserController;


@Slf4j
public class ParameterizedSpringRuleTests extends StandaloneSetupTest{
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    public static Collection<Object[]> parametersForTest() {
        return Arrays.asList(new Object[][] { { "John", "ok_John" }, { "Przodownik", "ok_Przodownik" } });
    }

    private final static String URI_ONE = "/users/test/{str}";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    @junitparams.Parameters(method = "parametersForTest")
    public void shouldFindUserById(final String input, final String expectedResult) throws Exception {
        log.info("input :  {} , result : {}", input, expectedResult);
        this.mockMvc.perform(get(URI_ONE, input).accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(expectedResult));
    }

    @Override
    protected Object setControllerToTest() {
        return userController;
    }

}