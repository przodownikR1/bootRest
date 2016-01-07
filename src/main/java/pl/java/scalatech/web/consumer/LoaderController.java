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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoaderController {
    @RequestMapping("/loaded")
    public String test(){
        System.err.println("test2s");
        return "pl";
    }
    @RequestMapping("/loaded2")
    public String test2 (){
        sdasdasdasdasdf();
        sadasd();
        return "dupa";
    }
    
    @RequestMapping("/loaded3")
    public String test4 (){
        sdasdasdasdasdf();
        sadasd();
        return "plsseseeeeesd";
    }
    
    private void sadasd() {
           System.err.println("+++++++####+++++++");
        
    }
    public void sdasdasdasdasdf(){
        System.err.println("cvcvsdsdssssssssss +++++++++ slsssawekdfdf");
    }
}
