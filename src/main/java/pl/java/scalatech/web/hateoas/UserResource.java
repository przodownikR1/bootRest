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
package pl.java.scalatech.web.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;

import pl.java.scalatech.domain.User;
import pl.java.scalatech.web.UserController;

public class UserResource extends ResourceSupport{
    public interface Projection{        }
    
    @JsonView(UserResource.Projection.class)
    User user;
    
  //  @JsonFilter("userFilter")
    public User getUser() {
        return this.user;
    }

    
    @JsonCreator
    public UserResource(User user) {
        this.user = user;
        this.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
        this.add(linkTo(methodOn(UserController.class).getUserByName(user.getLogin())).withRel("byName"));
        
        
    }

}
