package pl.java.scalatech.web.matrix;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;

@RestController
@RequestMapping("api/matrix")
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
@Slf4j
public class MatrixSampleController {

    private @NonNull final UserRepository userRepository;

    @RequestMapping("/{id}")
    HttpEntity<User> getId(@PathVariable Long id) {
        return ResponseEntity.ok(userRepository.findOne(id));

    }

    @RequestMapping("/{name}")
    HttpEntity<User> getUser(@PathVariable String name, @MatrixVariable List<String> keywords) {
        ResponseEntity<?> test = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ResponseEntity<Void> test2 = ResponseEntity.notFound().build();
        ResponseEntity<User> test3 = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        log.info("+++  {}", keywords);
        return userRepository.findByLogin(name).map(user -> ResponseEntity.ok(user)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @RequestMapping("/throw/{name}")
    HttpEntity<User> getUserThrow(@PathVariable String name, @MatrixVariable List<String> keywords) {
        return userRepository.findByLogin(name).map(user -> ResponseEntity.ok(user)).orElseThrow(IllegalArgumentException::new);
    }

    @RequestMapping(method = RequestMethod.POST)
    public HttpEntity<?> create(@RequestBody User user) {
        User loaded = userRepository.save(user);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(loaded.getId()).toUri()).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public HttpEntity<?> delete(@PathVariable Long id) {
        userRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

    // curl dev:8180/api/x/100;name=apple;color=red/y/200;name=orange
    @SuppressWarnings("serial")
    @RequestMapping(value = "/x/{x}/y/{y}")
    public Map<Integer, Map<String, String>> coordinateProfile(@PathVariable int x, @MatrixVariable(pathVar = "x") Map<String, String> xCnf,
            @PathVariable int y, @MatrixVariable(pathVar = "y") Map<String, String> yCnf) {
        return new HashMap<Integer, Map<String, String>>() {
            {
                put(x, xCnf);
                put(y, yCnf);
            }
        };
    }

    @RequestMapping("/full")
    public ResponseEntity<?> matrix(@PathVariable final Integer id,
            @MatrixVariable(pathVar = "filter", required = false) final Map<String, List<String>> filters,
            @MatrixVariable(pathVar = "sort", required = false) final Map<String, List<String>> sorters) {
        log.info("filters : {}", filters);
        log.info("sort : {}", sorters);
        return ResponseEntity.ok(filters);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> notFound(IllegalArgumentException ex) {
        return new ResponseEntity<>("Not Exist user", NOT_FOUND);
    }
}
