package pl.java.scalatech.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.service.user.UserService;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
public class UserController {
    @NonNull
    private final UserService userService;

    @RequestMapping(method = GET, value = "/users", produces = APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return null;
    }

    @RequestMapping(method = GET, value = "/users/{userId}", produces = APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("authorId") Long userId) {
        return userService.findOne(userId);
    }

    @RequestMapping(method = POST, value = "/users", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addAuthor(@RequestBody User user) {
        /*
         * if(null != authorRepository.findByFullName(author.getFullName())) {
         * throw new DuplicateEntryException();
         * }
         * authorRepository.save(new AuthorRecord(authorId, author.getFullName()));
         * headers.add(HttpHeaders.LOCATION, linkTo(methodOn(AuthorRestService.class).getAuthor(authorId)).toUri().toString());
         */
        MultiValueMap<String, String> headers = new HttpHeaders();
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}

/*
 * private Author createAuthor(AuthorRecord authorRecord) {
 * Author author = new Author(authorRecord.getFullName());
 * author.add(linkTo(methodOn(AuthorRestService.class).getAuthor(authorRecord.getAuthorId())).withSelfRel());
 * author.add(linkTo(methodOn(BookRestService.class).getAuthorBooks(authorRecord.getAuthorId())).withRel("books"));
 * return author;
 * }
 */
