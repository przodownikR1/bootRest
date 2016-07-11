package pl.java.scalatech.web.hateoas;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.Setter;

public class TeamResource extends ResourceSupport{
    @Getter @Setter
    private String name;
}
