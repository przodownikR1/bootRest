package pl.java.scalatech.endpoint;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Component
@PropertySource("classpath:git.properties")
@Slf4j
@Setter
@Getter
@ToString
public class GitProperties {

    private String gitId;

    private String userName;

    private Date time;

    private String message;

    @Autowired
    public GitProperties(@Value("${git.commit.id.abbrev}") final String gitId, @Value("${git.commit.user.name}") final String userName,
            @Value("${git.commit.message.full}") final String message, @Value("${git.commit.time}") final String time) {
        this.gitId = gitId;
        this.userName = userName;
        this.message = message;

    }
}