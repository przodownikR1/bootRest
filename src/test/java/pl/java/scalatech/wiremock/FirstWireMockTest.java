package pl.java.scalatech.wiremock;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
public class FirstWireMockTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089); // No-args constructor defaults to port 8080
   
    
    @Test
    public void configure_static_client() {
        WireMock.configureFor(8080);
        assertThat(true).isTrue();
    }

    @Test
    public void configure_standard_client() {
        WireMock wireMock = new WireMock(8089);
        assertThat(wireMock.allStubMappings().getMappings()).isNotNull().isEmpty();
    }
    
    @Test
    public void shouldIdentifyCepAsValid(){
        stubFor(get(urlEqualTo("/user/2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("\"{ "+
                                "\"user\": \"przodownik\"}\"")));
        
       
    }
    
  /*  @Test
    public void exampleTest() {
        stubFor(get(urlEqualTo("/my/resource"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "text/xml")
                    .withBody("<response>Some content</response>")));

        Result result = myHttpServiceCallingObject.doSomething();

        assertTrue(result.wasSuccessFul());

        verify(postRequestedFor(urlMatching("/my/resource/[a-z0-9]+"))
                .withRequestBody(matching(".*<message>1234</message>.*"))
                .withHeader("Content-Type", notMatching("application/json")));
    }*/
}
