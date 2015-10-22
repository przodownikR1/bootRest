package pl.java.scalatech.web.example;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
public class SampleRestController {



    @RequestMapping(value="/restSample",method=RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
    public Payload exampleEndpoint() {
        log.info("+++ restSample");
        return new Payload("test");
    }



}
