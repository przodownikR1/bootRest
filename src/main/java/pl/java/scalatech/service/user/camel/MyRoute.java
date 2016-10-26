package pl.java.scalatech.service.user.camel;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.camel.builder.RouteBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

@Component
@Profile("simpleRoute")
public class MyRoute extends RouteBuilder {
 
    
    
    @Override
    public void configure() throws Exception {
        from("timer://foo?fixedRate=true&period=6000").process(exchange ->
        {           
            HttpResponse response = Request.Get("http://localhost:9126/users").execute().returnResponse();
            InputStream is = response.getEntity().getContent();
            String result = CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));            
           // log.info("++++ : {} :  {}",exchange.getIn().getHeaders(), result);
        }).to("log:myCamel?level=INFO");
    }
}