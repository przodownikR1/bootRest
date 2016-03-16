package pl.java.scalatech.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.web.common.RestControllerAbstract;


@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController extends RestControllerAbstract<User>{
    
    @Autowired
    public UserController(UserRepository repository) {        
        super(repository);
        log.info("+++ userController constructor :  {}",repository.getClass());
    }
      
    @RequestMapping(value = "hate/{id}", method = RequestMethod.POST, produces = "application/hal+json")
    public PersistentEntityResource publish(@PathVariable("id") Long id, PersistentEntityResourceAssembler assembler)
            throws Exception {
        return assembler.toFullResource(service.findOne(id));
    } 
    
    @RequestMapping(value = "/{id}/test", method = RequestMethod.GET)
    @ResponseBody User getUserByIdTest(@PathVariable("id") User user) {
       return user;
    }
    @RequestMapping("/test")
    String test(){
        return "ok";        
    }
    @RequestMapping("/link/{id}")
    String getLink(@PathVariable Long id){
        return MvcUriComponentsBuilder.fromMethodName(this.getClass(), " getResourceById", id).build().toUri().toString();
    }
    
    @RequestMapping("/test/{str}")
    String testStr(@PathVariable String str){
        return "ok"+"_"+str;
        
    }
}
