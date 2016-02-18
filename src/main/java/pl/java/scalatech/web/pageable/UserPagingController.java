package pl.java.scalatech.web.pageable;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;

@RestController
@RequestMapping("api/paging/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class UserPagingController {

    private final @NonNull UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<Page<User>> listUser(@PageableDefault(page = 0, size = 4) Pageable pageable) {
        return ResponseEntity.ok(userRepository.findAll(pageable));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<User>> listUserSort(@SortDefault Sort sort) {
        return ResponseEntity.ok(userRepository.findAll(sort));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder builder) {
        User loaded = userRepository.save(user);
        URI uri = builder.path("/api/paging/{id}").buildAndExpand(loaded.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
