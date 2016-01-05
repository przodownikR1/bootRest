package pl.java.scalatech;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.domain.User;
import pl.java.scalatech.repository.UserRepository;

@SpringBootApplication
@Slf4j
public class BootRest {

    @Bean 
    CommandLineRunner init(UserRepository userRepository) {
        return (evt) ->
                Lists.newArrayList("przodownik","kowalski","nowak","plank","tyson","holyfield","jones","obama")
                        .forEach(a -> {
                            User user = userRepository.save(User.builder().firstname(a).login("_"+a).email(a+"@tlen.pl").login(a).build());
                            log.info("{}",user);
                        });
    }
    
    public static void main(String[] args) {
        SpringApplication.run(BootRest.class, args);
    }
}
