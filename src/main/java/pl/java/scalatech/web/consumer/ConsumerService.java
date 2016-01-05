package pl.java.scalatech.web.consumer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.domain.Customer;

@RestController
public class ConsumerService {


        @RequestMapping(value="check",method=RequestMethod.GET)
        public String checkAvailability(){
            return "Up and running!";
        }
        
        
        @RequestMapping(method=RequestMethod.GET, value="/customer/{id}")
        public Customer findById(@PathVariable("id") Long id){
            return new Customer(id);
        }
    
}
