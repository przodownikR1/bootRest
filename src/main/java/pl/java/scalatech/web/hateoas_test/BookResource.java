package pl.java.scalatech.web.hateoas_test;

import org.springframework.hateoas.ResourceSupport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class BookResource extends ResourceSupport {
    
    private String name;
     
    
    private String author;

}