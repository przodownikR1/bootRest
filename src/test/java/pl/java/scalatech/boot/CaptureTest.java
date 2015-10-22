package pl.java.scalatech.boot;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.OutputCapture;
public class CaptureTest {

    @Rule
    public OutputCapture capture = new OutputCapture();


    @Test
    public void testName() throws Exception {
        System.out.println("Hello World!");
        assertThat(capture.toString(), containsString("World"));
    }



}
