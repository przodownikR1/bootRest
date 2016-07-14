/*
 * Copyright 2016 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.java.scalatech.web;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metric;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.web.hateoas.UserResource;
import pl.java.scalatech.web.hateoas.UserResourceAssembler;

@RestController
@Slf4j
public class UserController extends GenericController<User>{
    
    
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository repo,MetricRegistry metricRegistry,Histogram sear) {
        super(repo);
        this.userRepository = repo;
    }
    
    @Autowired
    protected EntityLinks entityLink;

   
    private  Histogram searchResultHistogram;

   
    private  MetricRegistry metricRegistry;

    private Random random = new Random();

    @Autowired
    private UserResourceAssembler userResourceAssembler;

    @Timed(name = "userTimed")
    @com.codahale.metrics.annotation.Counted(name = "userCounted")
    @Metric(name = "userMetric")
    @ExceptionMetered(name = "exceptionUserMetered")
    // @JsonView(UserResource.Projection.class)
    @RequestMapping(method = GET, value = "/users", produces = APPLICATION_JSON_VALUE)
    public List<UserResource> getUsers() throws InterruptedException {
        Thread.sleep(random.nextInt(100));
        log.info("++++Sdd");
        List<UserResource> result = userRepository.findAll().stream().map(t -> new UserResource(t)).collect(toList());
        // TODO functional approach better use
        result.stream()
                .forEach(t -> t.add(new Link(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(t.getUser().getId()).toString())));
        searchResultHistogram.update(result.size());
        return result;
    }

    @Timed(name = "userId")
    @RequestMapping(method = GET, value = "/users/{userId}", produces = APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable("userId") Long userId) {
        Meter requests = metricRegistry.meter("requestsId");
        requests.mark();
        return userRepository.findOne(userId);

    }

    @Timed(name = "userName")
    @RequestMapping(method = GET, value = "/users/name/{name}", produces = APPLICATION_JSON_VALUE)
    public UserResource getUserByName(@PathVariable("name") String name) {
        Meter requests = metricRegistry.meter("requestName");
        requests.mark();
        return userRepository.findByLogin(name).map(t -> new UserResource(t)).orElseThrow(() -> new IllegalArgumentException("resource not found"));

    }

    @RequestMapping(value = "/pagingUser", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public HttpEntity<PagedResources<User>> getRestaurants(Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {
        Page<User> userPage = userRepository.findAll(pageable);

        return new ResponseEntity<>(pagedResourcesAssembler.toResource(userPage, userResourceAssembler), HttpStatus.OK);
    }

    @Timed(name = "userName")
    @RequestMapping(method = GET, value = "/users/nameFilter/{name}", produces = APPLICATION_JSON_VALUE, params = "fields")
    public MappingJacksonValue getUserFilter(@PathVariable("name") String name, @RequestParam String fields) {
        Meter requests = metricRegistry.meter("requestName");
        requests.mark();
        MappingJacksonValue wrapper = new MappingJacksonValue(
                userRepository.findByLogin(name).map(t -> new UserResource(t)).orElseThrow(() -> new IllegalArgumentException("resource not found")));
        final FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
        wrapper.setFilters(filterProvider);
        return wrapper;
    }

    @Override
    Class<?> getDomainClass() {
        return User.class;
    }

}
