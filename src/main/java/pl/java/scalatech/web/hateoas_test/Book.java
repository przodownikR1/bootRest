package pl.java.scalatech.web.hateoas_test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    
    private Long id;
    private String author;
    private String name;
}
