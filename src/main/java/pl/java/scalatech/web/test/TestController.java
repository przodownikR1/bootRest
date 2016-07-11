package pl.java.scalatech.web.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private static int a;
    private int b;
    
    @RequestMapping
    String showVariable(){       
        a=a+1;
        b=b+1;        
        return "a : " + a + " b " + b;
        
    }
    @RequestMapping("/reset")
    void reset(){
        a=0;
        b=0;
    }
    
}
