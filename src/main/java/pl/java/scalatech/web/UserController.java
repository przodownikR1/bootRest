package pl.java.scalatech.web;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.java.scalatech.web.hateoas.Version.BOOT_REST_V1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.web.common.RestControllerAbstract;


@RestController
@RequestMapping(value = "/users")

public class UserController extends RestControllerAbstract<User>{
    
    @Autowired
    public UserController(UserRepository repository) {
        super(repository);
    }

    @RequestMapping(value = "/{id}/test", method = RequestMethod.GET)
    @ResponseBody User getUserByIdTest(@PathVariable("id") User user) {
       return user;
    }
}
