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
package pl.java.scalatech.web.example;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.bull.javamelody.MonitoredWithSpring;
import pl.java.scalatech.domain.Role;
import pl.java.scalatech.repository.RoleRepository;

@RestController
@Slf4j
@CrossOrigin
@MonitoredWithSpring
public class SampleRestController {

     @Autowired
     private RoleRepository roleRepository;

    @RequestMapping(value="/restSample",method=RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
    public Payload exampleEndpoint() {
        log.info("+++ restSample");
        return new Payload("test");
    }

    @RequestMapping(value="/addRole",method=RequestMethod.GET)
    public Role createRole() {
        log.info("+++ createRole");
        roleRepository.save(Role.builder().name("ADMIN").build());
        return roleRepository.findAll().stream().findFirst().get();

    }

    @RequestMapping(value="/updateRole/{id}",method=RequestMethod.GET)
    public Role updateRole(@PathVariable Long id) {
        log.info("+++ createRole");
        Role loaded = roleRepository.findOne(id);
        loaded.setDesc(""+new Date().toString());
        return loaded;


    }

}
