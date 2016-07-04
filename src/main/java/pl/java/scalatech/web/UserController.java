/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.Timed;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired) )
@Slf4j
public class UserController {
    @NonNull
    private final UserRepository userRepository;
    @NonNull
    private final Histogram searchResultHistogram;
    @NonNull
    private final Meter requestUser;

    @NonNull
    private final   MetricRegistry metricRegistry;
    
    @Timed(name="userInfo")    
    @RequestMapping(method = GET, value = "/users", produces = APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        log.info("++++Sdd");
        List<User> result =userRepository.findAll();
        searchResultHistogram.update(result.size());
        requestUser.mark();
        return result; 
    }
    @Timed(name="userId")    
    @RequestMapping(method = GET, value = "/users/{userId}", produces = APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("userId") Long userId) {
        Meter requests = metricRegistry.meter("requestsId");
        requests.mark();
        return userRepository.findOne(userId);
        
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
