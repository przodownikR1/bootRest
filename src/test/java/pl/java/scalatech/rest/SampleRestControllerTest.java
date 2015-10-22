package pl.java.scalatech.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SampleRestControllerTest extends AbstractControllerRestTest {

    @Test
    public void shouldBootstrap() {

    }

    @Test
    public void shouldSampleWork() throws Exception {

        mockMvc.perform(get("/restSample")).andExpect(status().is(200));

    }

}
