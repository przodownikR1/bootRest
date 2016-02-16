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
package pl.java.scalatech.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import pl.java.scalatech.domain.User;
//@RepositoryRestResource(excerptProjection = NoSkills.class)
public interface UserRepository extends JpaRepository<User, Long>{
    @RestResource(rel="bylogin", path="byLogin")
    Optional<User> findByLogin(String login);
    
    
    @RestResource(exported=false)
    Optional<User> findByEmail(String email);
   
   
    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    public Page<User> findByLoginStartsWith(String login, Pageable p);


  

}
