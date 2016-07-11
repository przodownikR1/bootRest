package pl.java.scalatech.web.hateoas;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.Setter;

public class SkillResource extends ResourceSupport{
    @Getter @Setter
    private String name;
}
