package pl.java.scalatech.web.hateoas;

import org.springframework.hateoas.ResourceSupport;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class RoleResource  extends ResourceSupport{
    private final String role;
    private final String desc;
   

}
