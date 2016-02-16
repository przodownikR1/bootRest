package pl.java.scalatech.web.rest.opt;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Gender;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;

@RestController
@RequestMapping("/userOpt")
@Slf4j
public class UserOptController {

    @Autowired
    private UserRepository userRepository;

    Random r = new Random();

    @JsonView(User.AllView.class)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<User> getAll() {
        List<User> result = userRepository.findAll();
        if (result == null || result.isEmpty()) { // TODO some problem with empty collection !!!!
            result = null;
        }
        List<ResponseEntity<User>> res = result.stream().map(u -> ResponseEntity.ok(u)).collect(Collectors.toList());
        log.info("++++  {}", res);
        return result;
        // ResponseEntity.ok().eTag(createETag(user)).body(user);
    }

    @JsonView(User.AllView.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<User> getUserIdEtags(@PathVariable Long id) {
        User result = userRepository.findOne(id);
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "/increment/{id}/etag/{etag}", method = RequestMethod.GET)
    ResponseEntity<User> increment(@PathVariable Long id, @PathVariable Long etag) {
        User result = userRepository.findOne(id);
        /*
         * result.setEnabled(r.nextBoolean());
         * userRepository.save(result);
         */
        return ResponseEntity.ok().eTag(createETag(result) + etag).body(result);
    }

    @RequestMapping(value = "/cached/{id}/etag/{etag}", method = RequestMethod.GET)
    ResponseEntity<User> incrementCached(@PathVariable Long id, @PathVariable Long etag) {
        User result = userRepository.findOne(id);
        result.setEnabled(r.nextBoolean());
        userRepository.save(result);
        return ResponseEntity.ok().eTag(createETag(result) + etag).cacheControl(CacheControl.maxAge(2, TimeUnit.MINUTES)).body(result);
    }

    @RequestMapping("/testMail")
    @JsonView(User.OnlyEmailView.class)
    public User testMail() {
        return User.builder().email("przodownikR1@gmail.com").login("przodownik").firstname("slawek").lastName("borowuec").gender(Gender.MALE).build();
    }

    @RequestMapping("/testLogin")
    @JsonView(User.OnlyLoginView.class)
    public User testLogin() {
        return User.builder().email("przodownikR1@gmail.com").login("przodownik").firstname("slawek").lastName("borowuec").gender(Gender.MALE).build();
    }

    @RequestMapping("/testAll")
    @JsonView(User.AllView.class)
    public User testAllView() {
        return User.builder().email("przodownikR1@gmail.com").login("przodownik").firstname("slawek").lastName("borowuec").gender(Gender.MALE).build();
    }

    private String createETag(User user) {
        return "\"" + user.getVersion() + "\"";
    }
}
