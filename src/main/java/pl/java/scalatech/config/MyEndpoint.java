package pl.java.scalatech.config;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

@Component
public class MyEndpoint implements Endpoint<List<String>> {
     
    public String getId() {
        return "myEndpoint";
    }
 
    public boolean isEnabled() {
        return true;
    }
 
    public boolean isSensitive() {
        return true;
    }
 
    public List<String> invoke() {
        return ImmutableList.copyOf(newArrayList("test1","test2"));
       
    }
}