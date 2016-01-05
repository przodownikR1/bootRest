package pl.java.scalatech;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Project;
import pl.java.scalatech.domain.Skill;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.ProjectRepository;
import pl.java.scalatech.repository.SkillRepository;
import pl.java.scalatech.repository.UserRepository;

@SpringBootApplication
@Slf4j
public class BootRest {

    @Bean
    CommandLineRunner init(UserRepository userRepository, SkillRepository skillRepository, ProjectRepository projectRepository) {
        return (evt) -> Lists.newArrayList("przodownik", "kowalski", "nowak", "plank", "tyson", "holyfield", "jones", "obama").forEach(a -> {
            User user = userRepository.save(User.builder().firstname(a).login("_" + a).email(a + "@tlen.pl").login(a).build());
            log.info("{}", user);
            Lists.newArrayList("java", "scala", "groovy", "gradle", "maven", "spring", "jpa")
                    .forEach(s -> skillRepository.save(Skill.builder().name(s).build()));

            Lists.newArrayList("socialwarehouse", "ogw", "connector", "eo", "cash", "eb")
                    .forEach(p -> projectRepository.save(Project.builder().name(p).build()));
        });

    }

    public static void main(String[] args) {
        SpringApplication.run(BootRest.class, args);
    }
}
