package pl.java.scalatech.web.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SampleRestController {

    @RequestMapping(value="/restSample",method=RequestMethod.GET)
    
    public Payload exampleEndpoint() {
        log.info("+++ restSample");  
        return new Payload("test");
    }
    
    
   
}
