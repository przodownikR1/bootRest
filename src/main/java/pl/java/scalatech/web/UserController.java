package pl.java.scalatech.web;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.java.scalatech.web.hateoas.Version.BOOT_REST_V1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;


@RestController
@RequestMapping(value = "/users", consumes = BOOT_REST_V1, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    
    private final @NonNull UserRepository userRepository;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @RequestMapping(method = GET, value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userRepository.findOne(userId));
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addUser(@RequestBody User user) {         
        //User loaded = ofNullable(userRepository.findOne(checkNotNull(user).getId())).orElseThrow(()->new UserNotFoundException(user.getId()));
        User loaded = userRepository.save(user); 

        return (ResponseEntity<Void>) created(linkTo(methodOn(UserController.class).getUserById(loaded.getId())).toUri());

    }

    @RequestMapping(value = "/{id}/test", method = RequestMethod.GET)
    @ResponseBody User getUserByIdTest(@PathVariable("id") User user) {
       return user;
    }
}
