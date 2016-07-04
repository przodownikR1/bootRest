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
package pl.java.scalatech.web.consumer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.java.scalatech.domain.Customer;

@RestController
public class ConsumerService {


        @RequestMapping(value="check",method=RequestMethod.GET)
        public String checkAvailability(){
            return "Up and running!";
        }
        
        
        @RequestMapping(method=RequestMethod.GET, value="/customer/{id}")
        public Customer findById(@PathVariable("id") Long id){
            return new Customer(id);
        }
    
}
