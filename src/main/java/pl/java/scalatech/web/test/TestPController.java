package pl.java.scalatech.web.test;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Scope("prototype")
@Profile("test")
@RequestMapping("/testp")
public class TestPController {

    private static int a;
    private int b;
    
    @RequestMapping
    String showVariable(){        
        log.info("================================ a {} ,  b {}  ", a,b);
        a=+a;
        b=+b;
        log.info("after a {} ,  b {}  ", a,b);
        return "a : " + a + " b " + b;
        
    }
    
}
