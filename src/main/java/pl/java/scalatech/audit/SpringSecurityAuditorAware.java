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
package pl.java.scalatech.audit;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;


@Slf4j
@NoArgsConstructor
public class SpringSecurityAuditorAware implements AuditorAware<User> {
    private final static String ANONYMOUS = "anonymous";
    @Autowired
    private  UserRepository userRepository;

    @Override
    public User getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = ANONYMOUS;
         if(auth != null){
             login = auth.getName();
         }
        log.info("+++++++++++++++++++ +++++++++++++++++++++++++++++ login from security context : {}",login);
        Optional<User> user = userRepository.findByLogin(login);
        User loaded =  user.orElseThrow(()-> new IllegalArgumentException("user not exists"));
        log.info("+++++++++++++++++  +++++++++++++++++++++++++++++  {}",loaded);
        return loaded;
    }

}