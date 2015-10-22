package pl.java.scalatech;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootRest.class)
public class SpringDataTestApplicationTests {
    @Rule
    public OutputCapture capture = new OutputCapture();
	@Test
	public void contextLoads() {
	}

}
