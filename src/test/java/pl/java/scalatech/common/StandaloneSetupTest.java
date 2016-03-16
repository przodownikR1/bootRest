package pl.java.scalatech.common;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import junitparams.JUnitParamsRunner;
import pl.java.scalatech.config.TestSelectorConfig;

@RunWith(JUnitParamsRunner.class)
@ContextConfiguration(classes = { TestSelectorConfig.class })
@ActiveProfiles("test")
public abstract class StandaloneSetupTest {

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(setControllerToTest()).build();
    }

    protected abstract Object setControllerToTest();

}
