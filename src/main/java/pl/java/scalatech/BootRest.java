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
package pl.java.scalatech;

import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Role;
import pl.java.scalatech.domain.Skill;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.RoleRepository;
import pl.java.scalatech.repository.SkillRepository;
import pl.java.scalatech.repository.UserRepository;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@Slf4j
public class BootRest implements CommandLineRunner, ApplicationRunner{
    private final static String ANONYMOUS = "anonymous";

    @Bean
    CommandLineRunner init(UserRepository userRepository, SkillRepository skillRepository, RoleRepository roleRepository) {
        return (evt) ->

        Lists.newArrayList("przodownik", "kowalski", "nowak", "plank", "tyson", "holyfield", "jones", "obama", ANONYMOUS).forEach(a -> {
            User user = userRepository.save(User.builder().firstname(a).login(a).email(a + "@tlen.pl").login(a).enabled(true).build());
            log.info("{}", user);
            Lists.newArrayList("java", "scala", "groovy", "gradle", "maven", "spring", "jpa")
                    .forEach(s -> skillRepository.save(Skill.builder().name(s).build()));
            user.setSkills(Lists.newArrayList(skillRepository.getOne(1l), skillRepository.getOne(2l)));
            roleRepository.save(Role.builder().name(a).build());

        }

        );

    }
    @PostConstruct
    public void init() {
        IntStream.range(0, 60).forEach(index -> { log.info("++++ {}",index); }           );
    }


    @Bean
    String info(){
      return "Simple bean ";
    }

    @Autowired
    String info;


    @Value("${rest.cacheTll}")
    String cache;

    @Autowired
    private MyRestProperties props;


    public static void main(String[] args) {
        new SpringApplicationBuilder()
        .listeners(event -> log.info("#### > " + event.getClass().getCanonicalName()))
        .bannerMode(Banner.Mode.CONSOLE)
        .web(true)
        .sources(BootRest.class)
        .logStartupInfo(false)
        //.profiles("dev")

        .run(args);
        //SpringApplication app = new SpringApplication(BootRest.class);
       // app.setBanner((environment, sourceClass, out) -> out.print("\n\n\tThis is my own banner!\n\n".toUpperCase()));
        //app.run(args);


    }
    @Override
    public void run(String... args) throws Exception {
        log.info("!!!! : " + info);
        log.info("my rest props : {}",props);
        log.info("cachTll : {}",cache);

        for(String arg:args) {
            log.info("arg : {}",arg);
        }

    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("## > ApplicationRunner Implementation...");
        args.getNonOptionArgs().forEach(file -> log.info("+++++++++++++  {} : args :{}",file,args));


    }
}
