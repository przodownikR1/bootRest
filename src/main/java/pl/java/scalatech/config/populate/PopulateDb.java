package pl.java.scalatech.config.populate;

import static com.google.common.collect.Lists.newArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.Project;
import pl.java.scalatech.domain.Skill;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.ProjectRepository;
import pl.java.scalatech.repository.SkillRepository;
import pl.java.scalatech.repository.UserRepository;

@Slf4j
@Configuration
@Order(200)
public class PopulateDb {

    @Bean
    CommandLineRunner init(UserRepository userRepository, SkillRepository skillRepository, ProjectRepository projectRepository) {
        return (evt) -> newArrayList("przodownik", "kowalski", "nowak", "plank", "tyson", "holyfield", "jones", "obama").forEach(a -> {
            User user = userRepository.save(User.builder().firstname(a).login("_" + a).email(a + "@tlen.pl").login(a).build());
            log.info("{}", user);
            newArrayList("java", "scala", "groovy", "gradle", "maven", "spring", "jpa").forEach(s -> skillRepository.save(Skill.builder().name(s).build()));
            newArrayList("socialwarehouse", "ogw", "connector", "eo", "cash", "eb").forEach(p -> projectRepository.save(Project.builder().name(p).build()));
        });

    }
}
