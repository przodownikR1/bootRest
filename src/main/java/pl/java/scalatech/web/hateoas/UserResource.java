package pl.java.scalatech.web.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResource extends ResourceSupport{
    private final String login;
   

    @JsonCreator
    public UserResource(@JsonProperty("login") String login) {
        this.login = login;
    }

}
